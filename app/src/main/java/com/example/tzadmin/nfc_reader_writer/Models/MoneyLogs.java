package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class MoneyLogs extends BaseModel {

    public final static String AddMoney = "Add";
    public final static String RemoveMoney = "Remove";

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public Integer userid;
    @MAnnotation
    public Integer money;
    @MAnnotation
    public String type;
    @MAnnotation
    public String description;

    @Override
    public String GetTableName() {
        return "tbMoneyLogs";
    }

    public User getUser () {
        User u = new User();
        u.id = userid;

        Collection<User> users = (Collection<User>) new User().selectAllByParams();

        if (users.size() == 0) return null;

        for (User usr : users) return  usr;

        return null;
    }
}
