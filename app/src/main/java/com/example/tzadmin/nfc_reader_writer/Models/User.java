package com.example.tzadmin.nfc_reader_writer.Models;

import android.support.annotation.IntegerRes;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by forz on 11.06.17.
 */

public class User extends BaseModel {

    public User () {
        id = -1;
        firstname = "-1";
        lastname = "-1";
        patronymic = "-1";
        rfcid = "-1";
        groupid = -1;
//        cBatchId = "-1";
        routeid = -1;
        iscap = 0;
        syncFlag = 0;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String firstname;
    @MAnnotation
    public String lastname;
    @MAnnotation
    public String patronymic;
    @MAnnotation
    public String rfcid;
    @MAnnotation
    public Integer groupid;
//    @MAnotation
//    public String cBatchId;
    @MAnnotation
    public Integer routeid;
    @MAnnotation
    public Integer iscap;
    @MAnnotation(SyncField = true)
    public Integer syncFlag;


    //### MODEL INTERFACE ###//
    public String GetTableName() {
        return "tbUsers";
    }

    public boolean isNfcIdAlreadyExist (String RfcId) {
        this.rfcid = RfcId;

        User u = (User) selectOneByParams();

        return u != null;
    }

    public User selectUserByRfcId  (String RfcId) {
        this.rfcid = RfcId;

        return (User) selectOneByParams();
    }

    public Integer getBallance() {
        MoneyLogs mLog = new MoneyLogs();
        mLog.userid = id;

        Collection<MoneyLogs> moneys = (Collection<MoneyLogs>) mLog.selectAllByParams();

        Integer retdata = 0;
        if(moneys == null)
            return retdata;

        for (MoneyLogs l : moneys) {
            if (l.type == MoneyLogs.Type.ADD_MONEY.toString()) retdata += l.money;
            if (l.type == MoneyLogs.Type.REMOVE_MONEY.toString()) retdata -= l.money;
        }

        return retdata;
    }

    public Boolean AddMoney(Integer money, String description) {
        if (money < 0) return false;
        if (description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.userid = id;
        ml.money = money;
        ml.description = description;
        ml.type = MoneyLogs.Type.ADD_MONEY.toString();

        return  ml.insert();
    }

    public Boolean RemoveMoney(Integer money, String description) {
        if (money < 1) return false;
        if (money < getBallance()) return false;
        if (description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.userid = id;
        ml.money = money;
        ml.description = description;
        ml.type = MoneyLogs.Type.REMOVE_MONEY.toString();

        return  ml.insert();
    }

    public boolean subscribeToRoute(Integer route) {
        routeid = route;

        return update();
    }

    public Collection<Morda> getSubscribed () {
        Collection<Morda> retdata = new ArrayList<>();

        UserMorda m = new UserMorda();
        m.userid = id;

        Collection<UserMorda> mordas = (Collection<UserMorda>) m.selectAllByParams();
        if(mordas == null)
            return retdata;

        for (UserMorda item : mordas) {
            Morda tmp = item.getMorda();
            if (tmp != null)
                retdata.add(tmp);
        }

        return retdata;
    }

    public boolean subscribe(Integer mordaId) {
        Morda m = new Morda();
        m.id = mordaId;

        m = (Morda) m.selectOneByParams();

        if (m == null) return false;

        UserMorda um = new UserMorda();
        um.userid = id;
        um.mordaid = mordaId;

        um = (UserMorda) um.selectOneByParams();

        if (um == null) {
            um = new UserMorda();
            um.mordaid = mordaId;
            um.userid = id;
            return um.insert();
        }

        return true;
    }

    public Route getRoute(){
        Route route = new Route();
        route.id = routeid;

        route = (Route)route.selectOneByParams();
        return route;
    }

    public Group getGroup(){
        Group group = new Group();
        group.id = groupid;

        group = (Group)group.selectOneByParams();
        return  group;
    }

    public Collection<MoneyLogs> getMoneyLog(){
        MoneyLogs moneyLogs = new MoneyLogs();
        moneyLogs.userid = id;

        Collection<MoneyLogs> moneyLogsList = (Collection<MoneyLogs>)moneyLogs.selectAllByParams();
        return moneyLogsList;

    }

    public Integer getRating(){
        MoneyLogs moneyLogs = new MoneyLogs();
        moneyLogs.userid = id;

        Collection<MoneyLogs> moneyLogsCollection = (Collection<MoneyLogs>)moneyLogs.selectAllByParams();
        Integer sum = 0;

        for (MoneyLogs tempmoneyLog : moneyLogsCollection){
            if(tempmoneyLog.type == MoneyLogs.Type.ADD_MONEY.toString()){
                sum += tempmoneyLog.money;
            }
        }
        return sum;
    }

    public boolean updateAllId(Integer id) {
        UserMorda m = new UserMorda();
        m.userid = this.id;

        Collection<UserMorda> mordas = (Collection<UserMorda>) m.selectAllByParams();

        for (UserMorda item : mordas) {
            item.userid = id;
            item.update();
        }

        this.id = id;
        update();

        return true;
    }

}
