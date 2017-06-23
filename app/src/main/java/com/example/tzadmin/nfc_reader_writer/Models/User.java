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
        cGroupId = "-1";
        cBatchId = "-1";
        cRouteId = "-1";
        cIsCap = "-1";
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
    public String cGroupId;
    @MAnotation
    public String cBatchId;
    @MAnotation
    public String cRouteId;
    @MAnotation
    public String cIsCap;
    @MAnotation
    public String cIsDeleted;


    //### MODEL INTERFACE ###//
    public String GetTableName() {
        return "tbUsers";
    }

    public boolean isNfcIdAlreadyExist (String RfcId) {
        this.cRfcId = RfcId;

        Collection<User> u = (Collection<User>) selectByParams();

        return u.size() > 0;
    }

    public User selectUserByRfcId  (String RfcId) {
        this.cRfcId = RfcId;

        Collection<User> u = (Collection<User>) selectByParams();

        if (u.size() > 0)
            for (User usr : u) return usr;

        return null;
    }

}
