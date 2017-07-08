package com.example.tzadmin.nfc_reader_writer.Models;

import android.support.annotation.Nullable;
import com.example.tzadmin.nfc_reader_writer.Utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

/**
 * Created by velor on 6/23/17.
 */

public class MoneyLogs extends BaseModel {
    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public Integer userid;
    @MAnnotation(DefaultValue = "0")
    public Integer money;
    @MAnnotation
    public String type;
    @MAnnotation
    public String description;
    @MAnnotation(SyncField = true)
    public Integer syncFlag;
    @MAnnotation(DefaultValue = "")
    public String date;

    public MoneyLogs(){
        id = -1;
        userid = -1;
        type = "-1";
        description = "-1";
        money = 0;
        syncFlag = 0;
        date = "";
    }

    @Override
    public String GetTableName() {
        return "tbMoneyLogs";
    }

    @Nullable
    public User getUser() {
        User u = new User(); //TODO this object is useless. Maybe it created for selectAllByParams() ?
        u.id = userid;

        return (User) u.selectOneByParams();
    }

    public enum Type {
        ADD_MONEY,
        REMOVE_MONEY
    }
}
