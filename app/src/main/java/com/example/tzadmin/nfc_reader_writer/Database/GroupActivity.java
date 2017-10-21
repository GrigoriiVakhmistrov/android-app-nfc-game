package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class GroupActivity extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public Integer groupid;
    public Integer p1;
    public Integer p2;
    public Integer p3;
    public Integer syncFlag;
    public Integer p4;

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
}
