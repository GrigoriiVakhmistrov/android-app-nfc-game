package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Group extends BaseModel {

    public Group () {
        id = -1;
        name = "-1";
        description = "-1";
        totemname = "-1";
        totemimage = "-1";
        color = "-1";
        colorhex = "-1";
//        isdeleted = "-1";
        price = 0;
        vip = 0;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String name;
    @MAnnotation
    public String description;
    @MAnnotation
    public String totemname;
    @MAnnotation
    public String totemimage;
    @MAnnotation
    public String color;
    @MAnnotation
    public String colorhex;
//    @MAnnotation
//    public String isdeleted;
    @MAnnotation
    public Integer price;
    @MAnnotation
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
