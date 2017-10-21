package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
        return (ArrayList<User>) DataSupport.findAll(User.class);
    }

    public boolean isNfcIdAlreadyExist (String RfcId) {
       return DataSupport.where("rfcid like ?", RfcId).findFirst(User.class) != null;
    }

    public String getFIO() {
        return lastname + " " + firstname + " " +patronymic;
    }

    public User selectUserByRfcId  (String RfcId) {
       return DataSupport.where("rfcid like ?", RfcId).findFirst(User.class);
    }

    public Integer getBallance() {
        Collection<MoneyLogs> moneys =
                DataSupport.where("userid like ?", String.valueOf(id)).find(MoneyLogs.class);

        Integer retdata = 0;
        if(moneys == null)
            return retdata;

        for (MoneyLogs l : moneys) {
            if (l.type.equals(MoneyLogs.Type.ADD_MONEY.toString())) retdata += l.money;
            if (l.type.equals(MoneyLogs.Type.REMOVE_MONEY.toString())) retdata -= l.money;
        }

        return retdata;
    }

    public Boolean AddMoney(Integer money, String description) {
        if(money < 0) return false;
        if(description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.userid = id;
        ml.money = money;
        ml.description = description;
        ml.type = MoneyLogs.Type.ADD_MONEY.toString();
        ml.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return ml.save();
    }

    public Boolean RemoveMoney(Integer money, String description) {
        if (money < 1) return false;
        if (money > getBallance()) return false;
        if (description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.userid = id;
        ml.money = money;
        ml.description = description;
        ml.type = MoneyLogs.Type.REMOVE_MONEY.toString();
        ml.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return ml.save();
    }

    public boolean subscribeToRoute(Integer route) {
        routeid = route;
        this.update(id);
        return true;
    }

    public Collection<Morda> getSubscribed () {
        Collection<Morda> retdata = new ArrayList<>();

        Collection<UserMorda> mordas =
                DataSupport.where("userid like ?", String.valueOf(id)).find(UserMorda.class);

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
        Morda m =
                DataSupport.where("id like ?", String.valueOf(mordaId)).findFirst(Morda.class);

        if (m == null) return false;

        UserMorda um =
                DataSupport.where("userid like ? and mordaid like ?",
                        String.valueOf(id),
                        String.valueOf(mordaId)).findFirst(UserMorda.class);

        if (um == null) {
            um = new UserMorda();
            um.mordaid = mordaId;
            um.userid = id;
            return um.save();
        }

        return true;
    }

    public Route getRoute(){
        return DataSupport.where("id like ?", String.valueOf(routeid)).findFirst(Route.class);
    }

    public Group getGroup(){
        return DataSupport.where("id like ?", String.valueOf(groupid)).findFirst(Group.class);
    }

    public Collection<MoneyLogs> getMoneyLog(){
        return DataSupport.where("userid like ?", String.valueOf(id)).find(MoneyLogs.class);
    }

    public Collection<UserMorda> getUserMordas() {
        return DataSupport.where("userid like ?", String.valueOf(id)).find(UserMorda.class);
    }

    public Integer getRating(){
        Collection<MoneyLogs> moneyLogsCollection =
                DataSupport.where("userid like ?", String.valueOf(id)).find(MoneyLogs.class);

        Integer sum = 0;
        for (MoneyLogs tempmoneyLog : moneyLogsCollection){
            if(tempmoneyLog.type.equals(MoneyLogs.Type.ADD_MONEY.toString())){
                sum += tempmoneyLog.money;
            }
        }
        return sum;
    }

    public boolean updateAllId(Integer id) {
        Collection<UserMorda> mordas =
                DataSupport.where("userid like ?", String.valueOf(this.id)).find(UserMorda.class);

        for (UserMorda item : mordas) {
            item.userid = id;
            item.save();
        }

        this.id = id;
        save();

        return true;
    }
}
