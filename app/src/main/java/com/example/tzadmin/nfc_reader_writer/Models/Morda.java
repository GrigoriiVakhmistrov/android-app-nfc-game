package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class Morda extends BaseModel {

    public Morda () {
        id = -1;
        fio = "-1";
        description = "-1";
        pic = "-1";
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String fio;
    @MAnnotation
    public String description;
    @MAnnotation
    public String pic;

    @Override
    public String GetTableName() {
        return "tbMorda";
    }
}
