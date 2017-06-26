package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by forz on 11.06.17.
 */

public class User extends BaseModel {

    public User () {
        id = -1;
        cFirstName = "-1";
        cLastName = "-1";
        cSurname = "-1";
        cRfcId = "-1";
        cGroupId = -1;
//        cBatchId = "-1";
        cRouteId = -1;
        cIsCap = 0;
        cIsDeleted = "-1";
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String cFirstName;
    @MAnnotation
    public String cLastName;
    @MAnnotation
    public String cSurname;
    @MAnnotation
    public String cRfcId;
    @MAnnotation
    public Integer cGroupId;
//    @MAnotation
//    public String cBatchId;
    @MAnnotation
    public Integer cRouteId;
    @MAnnotation
    public Integer cIsCap;
    @MAnnotation
    public String cIsDeleted;


    //### MODEL INTERFACE ###//
    public String GetTableName() {
        return "tbUsers";
    }

    public boolean isNfcIdAlreadyExist (String RfcId) {
        this.cRfcId = RfcId;

        User u = (User) selectOneByParams();

        return u != null;
    }

    public User selectUserByRfcId  (String RfcId) {
        this.cRfcId = RfcId;

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
        cRouteId = route;

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

    public Route getRoute(Integer userId){
        Route r = new Route();
        r.id = cRouteId;

        r = (Route)r.selectOneByParams();
        return r;
    }

}
