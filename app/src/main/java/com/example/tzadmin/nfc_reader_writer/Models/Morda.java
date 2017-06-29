package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class Morda extends BaseModel {

    public Morda () {
        id = -1;
        fio = "";
        description = "";
        pic = "";
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String fio;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "")
    public String pic;

    @Override
    public String GetTableName() {
        return "tbMorda";
    }
}
