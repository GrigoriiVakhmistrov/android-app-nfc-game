package com.example.tzadmin.nfc_reader_writer.Database;

import android.support.annotation.Nullable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class MoneyLogs extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public Integer userid;
    public Integer money;
    public String type;
    public String description;
    public Integer syncFlag;
    public String date;

    @Nullable
    public User getUser() {
        return DataSupport.where("id like ?", String.valueOf(userid)).findFirst(User.class);
    }

    public enum Type {
        ADD_MONEY,
        REMOVE_MONEY
    }
}
