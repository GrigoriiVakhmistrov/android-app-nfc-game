package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Route extends BaseModel {

    public Route () {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.capacity = 0;
        this.price = 0;
//        this.isdeleted = "-1";
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String name;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "0")
    public Integer capacity;
    @MAnnotation(DefaultValue = "0")
    public Integer price;
//    @MAnnotation
//    public String isdeleted;


    @Override
    public String GetTableName() {
        return "tbRoutes";
    }

    public Integer getLeft() {
        Collection<User> users = (Collection<User>) new User().selectAll();
        if(users == null)
            return capacity;

        int cap = capacity;

        for (User item : users) {
            if (item.groupid.equals(id))
                cap--;
        }

        return cap;
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.routeid = id;

        return (Collection<User>)u.selectAllByParams();

    }
}
