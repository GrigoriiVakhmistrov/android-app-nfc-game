package com.example.tzadmin.nfc_reader_writer.Models;

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

    @MAnotation(PrimaryKey = true)
    public Integer id;
    @MAnotation
    public String cFirstName;
    @MAnotation
    public String cLastName;
    @MAnotation
    public String cSurname;
    @MAnotation
    public String cRfcId;
    @MAnotation
    public Integer cGroupId;
//    @MAnotation
//    public String cBatchId;
    @MAnotation
    public Integer cRouteId;
    @MAnotation
    public Integer cIsCap;
    @MAnotation
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

        User u = (User) selectOneByParams();

        return u;
    }

    public Integer getBallance() {
        MoneyLogs mLog = new MoneyLogs();
        mLog.userid = id;

        Collection<MoneyLogs> moneys = (Collection<MoneyLogs>) mLog.selectAllByParams();

        Integer retdata = 0;

        for (MoneyLogs l : moneys) {
            if (l.equals(MoneyLogs.AddMoney)) retdata += l.money;
            if (l.equals(MoneyLogs.RemoveMoney)) retdata -= l.money;
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
        ml.type = MoneyLogs.AddMoney;

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
        ml.type = MoneyLogs.RemoveMoney;

        return  ml.insert();
    }

    public boolean subscribeToRoute(Integer route) {
        cRouteId = route;

        return update();
    }

}
