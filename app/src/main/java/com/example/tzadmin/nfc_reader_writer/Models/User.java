package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by forz on 11.06.17.
 */

public class User {

    public User (boolean isRegister) {
        if(isRegister) {
            cRfcId = "0";
            cGroupId = "0";
            cBatchId = "0";
            cRouteId = "0";
            cIsCap = "0";
            cIsDeleted = "0";
        }
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
