package com.example.tzadmin.nfc_reader_writer.Models;

import android.support.annotation.Nullable;
import com.example.tzadmin.nfc_reader_writer.Utils;

import java.util.Collection;

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

    public MoneyLogs(){
        id = -1;
        userid = -1;
        type = "-1";
        description = "-1";
        money = 0;
        syncFlag = 0;
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof MoneyLogs)) return false;

        MoneyLogs logs = (MoneyLogs) o;

        if(id != null ? !id.equals(logs.id) : logs.id != null) return false;
        if(userid != null ? !userid.equals(logs.userid) : logs.userid != null) return false;
        if(money != null ? !money.equals(logs.money) : logs.money != null) return false;
        if(type != null ? !type.equals(logs.type) : logs.type != null) return false;
        if(description != null ? !description.equals(logs.description) : logs.description != null) return false;
        return syncFlag != null ? syncFlag.equals(logs.syncFlag) : logs.syncFlag == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (money != null ? money.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (syncFlag != null ? syncFlag.hashCode() : 0);
        return result;
    }

    public enum Type {
        ADD_MONEY,
        REMOVE_MONEY
    }
}
