package com.example.tzadmin.nfc_reader_writer.Database;

import android.support.annotation.Nullable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class MoneyLogs extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private Integer userid;
    private Integer money;
    private String type;
    private String description;
    private Integer syncFlag;
    private String date;

    @Nullable
    public User getUser() {
        return DataSupport.where("id like ?", String.valueOf(userid)).findFirst(User.class);
    }

    public Integer getId() {
        return id;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getUserid () {
        return this.userid;
    }

    public enum Type {
        ADD_MONEY,
        REMOVE_MONEY
    }
}
