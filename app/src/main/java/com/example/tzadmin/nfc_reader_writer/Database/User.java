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
    private Integer id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private String rfcid;
    private Integer groupid;
    private Integer routeid;
    private Integer iscap;
    private Integer syncFlag;

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
            if (l.getType().equals(MoneyLogs.Type.ADD_MONEY.toString())) retdata += l.getMoney();
            if (l.getType().equals(MoneyLogs.Type.REMOVE_MONEY.toString())) retdata -= l.getMoney();
        }

        return retdata;
    }

    public Boolean AddMoney(Integer money, String description) {
        if(money < 0) return false;
        if(description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.setUserid(id);
        ml.setMoney(money);
        ml.setDescription(description);
        ml.setType(MoneyLogs.Type.ADD_MONEY.toString());
        ml.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return ml.save();
    }

    public Boolean RemoveMoney(Integer money, String description) {
        if (money < 1) return false;
        if (money > getBallance()) return false;
        if (description.equals("")) description = "Без описания";

        MoneyLogs ml = new MoneyLogs();
        ml.setUserid(id);
        ml.setMoney(money);
        ml.setDescription(description);
        ml.setType(MoneyLogs.Type.REMOVE_MONEY.toString());
        ml.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

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
            um.setMordaid(mordaId);
            um.setUserid(id);
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
            if(tempmoneyLog.getType().equals(MoneyLogs.Type.ADD_MONEY.toString())){
                sum += tempmoneyLog.getMoney();
            }
        }
        return sum;
    }

    public boolean updateAllId(Integer id) {
        Collection<UserMorda> mordas =
                DataSupport.where("userid like ?", String.valueOf(this.id)).find(UserMorda.class);

        for (UserMorda item : mordas) {
            item.setUserid(id);
            item.save();
        }

        this.id = id;
        save();

        return true;
    }

    public Integer getId() {
        return this.id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public void setRfcid(String rfcid) {
        this.rfcid = rfcid;
    }

    public String getRfcid() {
        return rfcid;
    }

    public void setRouteid (Integer routeid) {
        this.routeid = routeid;
    }

    public Integer getRouteid() {
        return this.routeid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupid() {
        return this.groupid;
    }

    public void setIscap(Integer iscap) {
        this.iscap = iscap;
    }

    public Integer getIscap() {
        return iscap;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }
}
