package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Collection;

/**
 * Created by yurii on 19.10.2017.
 */

public class Group extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public String name;
    public String description;
    public String totemname;
    public String totemimage;
    public String color;
    public String colorhex;
    public Integer price;
    public Integer vip;

    public Collection<User> getUsers() {
        //return DataSupport.where("groupid like ?", )
    }
}
