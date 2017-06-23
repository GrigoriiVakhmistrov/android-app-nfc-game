package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class MoneyLogs extends BaseModel {

    public final static String AddMoney = "Add";
    public final static String RemoveMoney = "Remove";

    @MAnotation(PrimaryKey = true)
    public Integer id;
    @MAnotation
    public Integer userid;
    @MAnotation
    public Integer money;
    @MAnotation
    public String type;
    @MAnotation
    public String description;

    @Override
    public String GetTableName() {
        return "tbMoneyLogs";
    }

    public User getUser () {
        User u = new User();
        u.id = userid;

        Collection<User> users = (Collection<User>) new User().selectByParams();

        if (users.size() == 0) return null;

        for (User usr : users) return  usr;

        return null;
    }
}
