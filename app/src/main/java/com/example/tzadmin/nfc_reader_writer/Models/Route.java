package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Route extends BaseModel {

    public Route () {
        this.id = -1;
        this.name = "-1";
        this.description = "-1";
        this.capacity = -1;
        this.isdeleted = "-1";
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String name;
    @MAnnotation
    public String description;
    @MAnnotation
    public Integer capacity;
    @MAnnotation
    public String isdeleted;


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
            if (item.cGroupId.equals(id))
                cap--;
        }

        return cap;
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.cRouteId = id;

        return (Collection<User>)u.selectAllByParams();

    }
}
