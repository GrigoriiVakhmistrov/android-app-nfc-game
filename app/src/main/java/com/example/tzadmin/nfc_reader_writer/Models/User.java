package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by forz on 11.06.17.
 */

public class User {

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

    public int id;
    public String cFirstName;
    public String cLastName;
    public String cSurname;
    public String cRfcId;
    public String cGroupId;
    public String cBatchId;
    public String cRouteId;
    public String cIsCap;
    public String cIsDeleted;
}
