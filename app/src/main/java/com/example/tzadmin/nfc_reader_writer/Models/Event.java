package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/27/17.
 */

public class Event extends BaseModel {

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String name;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "0")
    public Integer price;

    public Event() {
        id = -1;
        name = "";
        price = 0;
        description = "";
    }

    @Override
    public String GetTableName() {
        return "Event";
    }

}
