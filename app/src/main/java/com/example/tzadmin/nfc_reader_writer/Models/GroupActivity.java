package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class GroupActivity extends BaseModel {

    public GroupActivity() {
        id = -1;
        groupid = -1;
        p1 = -1;
        p2 = -1;
        p3 = -1;
        p4 = -1;
    }

    @MAnotation(PrimaryKey = true)
    public Integer id;
    @MAnotation
    public Integer groupid;
    @MAnotation
    public Integer p1;
    @MAnotation
    public Integer p2;
    @MAnotation
    public Integer p3;
    @MAnotation
    public Integer p4;

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
