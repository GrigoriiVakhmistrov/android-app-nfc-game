package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/24/17.
 */

public class Morda extends BaseModel {

    public Morda () {
        id = -1;
        fio = "";
        description = "";
        pic = "";
        capacity = -1;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String fio;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "")
    public String pic;
    @MAnnotation(DefaultValue = "-1")
    public Integer capacity;

    @Override
    public String GetTableName() {
        return "tbMorda";
    }

    public Integer getLeft() {
        UserMorda u = new UserMorda();
        u.mordaid = id;
        Collection<UserMorda> users = (Collection<UserMorda>) u.selectAllByParams();
        if(users == null)
            return capacity;

        return capacity - users.size();
    }
}
