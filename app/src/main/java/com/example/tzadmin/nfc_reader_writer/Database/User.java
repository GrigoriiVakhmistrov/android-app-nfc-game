package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurii on 19.10.2017.
 */

public class User extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public String firstname;
    public String lastname;
    public String patronymic;
    public String rfcid;
    public Integer groupid;
    public Integer routeid;
    public Integer iscap;
    public Integer syncFlag;

    public ArrayList<User> selectAll () {
        return null;
    }

    public boolean isNfcIdAlreadyExist (String RfcId) {
       /* this.rfcid = RfcId;

        User u = (User) selectOneByParams();
        return u != null;*/
       return false;
    }

    public String getFIO() {
        return lastname + " " + firstname + " " +patronymic;
    }

    public User selectUserByRfcId  (String RfcId) {
       /* this.rfcid = RfcId;

        return (User) selectOneByParams();*/
       return null;
    }

    public Integer getBallance() {
       /* MoneyLogs mLog = new MoneyLogs();
        mLog.userid = id;

        Collection<MoneyLogs> moneys = (Collection<MoneyLogs>) mLog.selectAllByParams();

        Integer retdata = 0;
        if(moneys == null)
            return retdata;

        for (MoneyLogs l : moneys) {
            if (l.type.equals(MoneyLogs.Type.ADD_MONEY.toString())) retdata += l.money;
            if (l.type.equals(MoneyLogs.Type.REMOVE_MONEY.toString())) retdata -= l.money;
        }

        return retdata;*/
        return null;
    }

    public Boolean AddMoney(Integer money, String description) {
        /*if (money < 0) return false;
        if (description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.userid = id;
        ml.money = money;
        ml.description = description;
        ml.type = MoneyLogs.Type.ADD_MONEY.toString();
        ml.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return  ml.insert();*/
        return false;
    }

    public Boolean RemoveMoney(Integer money, String description) {
        /*if (money < 1) return false;
        if (money > getBallance()) return false;
        if (description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.userid = id;
        ml.money = money;
        ml.description = description;
        ml.type = MoneyLogs.Type.REMOVE_MONEY.toString();
        ml.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return  ml.insert();*/
        return null;
    }

    public boolean subscribeToRoute(Integer route) {
       /* routeid = route;

        return update();*/
        return false;
    }

    public Collection<Morda> getSubscribed () {
        /*Collection<Morda> retdata = new ArrayList<>();

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

        return retdata;*/
        return null;
    }

    public boolean subscribe(Integer mordaId) {
       /* Morda m = new Morda();
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

        return true;*/
        return false;
    }

    public Route getRoute(){
       /* Route route = new Route();
        route.id = routeid;

        route = (Route)route.selectOneByParams();
        return route;*/
        return null;
    }

    public Group getGroup(){
       /* Group group = new Group();
        group.id = groupid;

        group = (Group)group.selectOneByParams();
        return  group;*/
        return null;
    }

    public Collection<MoneyLogs> getMoneyLog(){
       /* MoneyLogs moneyLogs = new MoneyLogs();
        moneyLogs.userid = id;

        Collection<MoneyLogs> moneyLogsList = (Collection<MoneyLogs>)moneyLogs.selectAllByParams();
        return moneyLogsList;*/
        return null;
    }

    public Collection<UserMorda> getUserMordas() {
       /* UserMorda morda = new UserMorda();
        morda.userid = id;

        return (Collection<UserMorda>)morda.selectAllByParams();*/
        return null;
    }

    public Integer getRating(){
       /* MoneyLogs moneyLogs = new MoneyLogs();
        moneyLogs.userid = id;

        Collection<MoneyLogs> moneyLogsCollection = (Collection<MoneyLogs>)moneyLogs.selectAllByParams();
        Integer sum = 0;

        for (MoneyLogs tempmoneyLog : moneyLogsCollection){
            if(tempmoneyLog.type.equals(MoneyLogs.Type.ADD_MONEY.toString())){
                sum += tempmoneyLog.money;
            }
        }
        return sum;*/
        return null;
    }

    public boolean updateAllId(Integer id) {
        /*UserMorda m = new UserMorda();
        m.userid = this.id;

        Collection<UserMorda> mordas = (Collection<UserMorda>) m.selectAllByParams();

        for (UserMorda item : mordas) {
            item.userid = id;
            item.update();
        }

        this.id = id;
        update();

        return true;*/
        return false;
    }
}
