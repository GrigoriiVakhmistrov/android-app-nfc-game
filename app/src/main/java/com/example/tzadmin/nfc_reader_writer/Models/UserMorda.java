package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class UserMorda extends BaseModel {

    public UserMorda () {
        id = -1;
        userid = -1;
        mordaid = -1;
        syncFlag = 0;
    }

    @MAnnotation(PrimaryKey=true)
    public Integer id;
    @MAnnotation
    public Integer userid;
    @MAnnotation
    public Integer mordaid;
    @MAnnotation(SyncField = true)
    public Integer syncFlag;

    @Override
    public String GetTableName() {
        return "tbUserMorda";
    }

    public Morda getMorda() {
        Morda m = new Morda();
        m.id = mordaid;

        return (Morda) m.selectOneByParams();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof UserMorda)) return false;

        UserMorda morda = (UserMorda) o;

        if(id != null ? !id.equals(morda.id) : morda.id != null) return false;
        if(userid != null ? !userid.equals(morda.userid) : morda.userid != null) return false;
        if(mordaid != null ? !mordaid.equals(morda.mordaid) : morda.mordaid != null) return false;
        return syncFlag != null ? syncFlag.equals(morda.syncFlag) : morda.syncFlag == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userid != null ? userid.hashCode() : 0);
        result = 31 * result + (mordaid != null ? mordaid.hashCode() : 0);
        result = 31 * result + (syncFlag != null ? syncFlag.hashCode() : 0);
        return result;
    }
}
