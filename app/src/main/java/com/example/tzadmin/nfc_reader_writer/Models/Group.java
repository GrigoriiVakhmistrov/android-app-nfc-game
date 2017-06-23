package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Group extends BaseModel {

    @MAnotation(PrimaryKey = true)
    public Integer id;
    @MAnotation
    public String name;
    @MAnotation
    public String description;
    @MAnotation
    public String totemname;
    @MAnotation
    public Integer totemimage;
    @MAnotation
    public String color;
    @MAnotation
    public String colorhex;
    @MAnotation
    public String isdeleted;

    @Override
    public String GetTableName() {
        return "tbGroups";
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.cGroupId = id;

        return (Collection<User>)u.selectByParams();
    }
}
