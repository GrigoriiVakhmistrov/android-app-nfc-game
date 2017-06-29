package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Group extends BaseModel {

    public Group () {
        id = -1;
        name = "";
        description = "";
        totemname = "";
        totemimage = "";
        color = "";
        colorhex = "";
//        isdeleted = "-1";
        price = 0;
        vip = 0;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String name;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "")
    public String totemname;
    @MAnnotation(DefaultValue = "")
    public String totemimage;
    @MAnnotation(DefaultValue = "")
    public String color;
    @MAnnotation(DefaultValue = "")
    public String colorhex;
//    @MAnnotation
//    public String isdeleted;
    @MAnnotation(DefaultValue = "0")
    public Integer price;
    @MAnnotation(DefaultValue = "0")
    public Integer vip;



    @Override
    public String GetTableName() {
        return "tbGroups";
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.groupid = id;

        return (Collection<User>)u.selectAllByParams();
    }
}
