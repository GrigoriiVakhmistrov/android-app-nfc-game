package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Collection;

/**
 * Created by yurii on 19.10.2017.
 */

public class Shop extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public String name;
    public String description;
    public String pic;
    public Integer price;

    public Collection<Shop> GetAllItems() {
        return DataSupport.findAll(Shop.class);
    }
}
