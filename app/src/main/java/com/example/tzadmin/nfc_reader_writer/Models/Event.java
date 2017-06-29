package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/27/17.
 */

public class Event extends BaseModel {

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String name;
    @MAnnotation
    public String description;
    @MAnnotation
    public String price;

    public Event() {
        id = -1;
        name = "-1";
        price = "-1";
        description = "";
    }

    @Override
    public String GetTableName() {
        return "Event";
    }

}
