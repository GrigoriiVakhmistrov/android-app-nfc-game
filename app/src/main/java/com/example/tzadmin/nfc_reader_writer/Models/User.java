package com.example.tzadmin.nfc_reader_writer.Models;

import com.example.tzadmin.nfc_reader_writer.Database.ModelInterface;

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

}
