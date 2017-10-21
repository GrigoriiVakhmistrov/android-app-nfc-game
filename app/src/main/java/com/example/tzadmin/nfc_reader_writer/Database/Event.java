package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;

/**
 * Created by yurii on 19.10.2017.
 */

public class Event extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public String name;
    public String description;
    public Integer price;

    public ArrayList selectAll () {
        return (ArrayList) DataSupport.findAll(Event.class);
    }
}
