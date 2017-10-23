package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class GroupActivity extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private Integer groupid;
    private Integer p1;
    private Integer p2;
    private Integer p3;
    private Integer syncFlag;
    private Integer p4;

    public GroupActivity GetGroupScoreModel(Integer groupId) {
        Group g = DataSupport.where("id like ?", String.valueOf(groupId)).findFirst(Group.class);
        if(g == null) return null;
        GroupActivity ga = DataSupport.where("groupid like ?", String.valueOf(groupId)).findFirst(GroupActivity.class);
        if(ga == null) {
            ga = new GroupActivity();

            ga.groupid = groupId;
            ga.p1 = 0;
            ga.p2 = 0;
            ga.p3 = 0;
            ga.p4 = 0;

            ga.save();
        }
        return ga;
    }

    public Integer getId() {
        return id;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid (Integer groupid) {
        this.groupid = groupid;
    }

    public Integer getP1() {
        return p1;
    }

    public void setP1 (Integer p1) {
        this.p1 = p1;
    }

    public Integer getP2() {
        return p2;
    }

    public void setP2 (Integer p2) {
        this.p2 = p2;
    }

    public Integer getP3() {
        return p3;
    }

    public void setP3 (Integer p3) {
        this.p3 = p3;
    }

    public Integer getSyncFlag() {
        return syncFlag;
    }

    public void setSyncFlag (Integer syncFlag) {
        this.syncFlag = syncFlag;
    }

    public Integer getP4() {
        return p4;
    }

    public void setP4 (Integer p4) {
        this.p4 = p4;
    }
}
