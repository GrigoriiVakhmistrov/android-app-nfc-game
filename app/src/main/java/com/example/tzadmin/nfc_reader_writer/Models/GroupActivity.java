package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class GroupActivity extends BaseModel {

    public GroupActivity() {
        id = -1;
        groupid = -1;
        p1 = 0;
        p2 = 0;
        p3 = 0;
        p4 = 0;
        syncFlag = 0;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public Integer groupid;
    @MAnnotation(DefaultValue = "0")
    public Integer p1;
    @MAnnotation(DefaultValue = "0")
    public Integer p2;
    @MAnnotation(DefaultValue = "0")
    public Integer p3;
    @MAnnotation(DefaultValue = "0")
    public Integer p4;
    @MAnnotation(SyncField = true)
    public Integer syncFlag;

    @Override
    public String GetTableName() {
        return "tbGroupPriority";
    }

    public GroupActivity GetGroupScoreModel(Integer groupId) {
        Group g = new Group();
        g.id = groupId;

        g = (Group) g.selectAllByParams();
        if (g == null) return  null;

        GroupActivity ga = new GroupActivity();
        ga.groupid = groupId;

        ga = (GroupActivity) ga.selectOneByParams();

        if (ga == null) {
            ga = new GroupActivity();

            ga.groupid = groupId;
            ga.p1 = 0;
            ga.p2 = 0;
            ga.p3 = 0;
            ga.p4 = 0;

            ga.insert();
        }

        return ga;
    }

}
