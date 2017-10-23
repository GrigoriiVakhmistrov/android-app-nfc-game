package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by yurii on 19.10.2017.
 */

public class Event extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private String name;
    private String description;
    private Integer price;

    public ArrayList selectAll () {
        return (ArrayList) DataSupport.findAll(Event.class);
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
