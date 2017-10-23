package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class UserMorda extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private Integer userid;
    private Integer syncFlag;
    private Integer mordaid;

    public Morda getMorda() {
        return DataSupport.where("id like ?", String.valueOf(mordaid)).findFirst(Morda.class);
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag(Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    public void setMordaid(Integer mordaid) {
        this.mordaid = mordaid;
    }

    public Integer getMordaid() {
        return mordaid;
    }
}
