package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class UserMorda extends BaseModel {

    public UserMorda () {
        id = -1;
        userid = -1;
        mordaid = -1;
    }

    @MAnnotation(PrimaryKey=true)
    public Integer id;
    @MAnnotation
    public Integer userid;
    @MAnnotation
    public Integer mordaid;

    @Override
    public String GetTableName() {
        return "tbUserMorda";
    }

    public Morda getMorda() {
        Morda m = new Morda();
        m.id = mordaid;

        return (Morda) m.selectOneByParams();
    }

}
