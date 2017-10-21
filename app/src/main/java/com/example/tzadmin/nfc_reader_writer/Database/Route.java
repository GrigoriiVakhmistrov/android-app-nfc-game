package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Collection;

/**
 * Created by yurii on 19.10.2017.
 */

public class Route extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public String name;
    public String description;
    public Integer capacity;
    public Integer price;
    public Integer isvip;

    public Integer getLeft() {
        /*User u = new User();
        u.routeid = id;
        Collection<User> users = (Collection<User>) u.selectAllByParams();
        if(users == null)
            return capacity;

        return capacity - users.size();*/
        return -1;
    }

    public Collection<User> getUsers() {
        /*User u = new User();
        u.routeid = id;

        return (Collection<User>)u.selectAllByParams();*/
        return null;
    }
}
