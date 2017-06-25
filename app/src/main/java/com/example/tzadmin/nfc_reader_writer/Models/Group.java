package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Group extends BaseModel {

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
    @MAnnotation
    public String isdeleted;

    @Override
    public String GetTableName() {
        return "tbGroups";
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.cGroupId = id;

        return (Collection<User>)u.selectAllByParams();
    }
}
