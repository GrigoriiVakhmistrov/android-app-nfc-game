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

        g = (Group) g.selectOneByParams();
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof GroupActivity)) return false;

        GroupActivity activity = (GroupActivity) o;

        if(id != null ? !id.equals(activity.id) : activity.id != null) return false;
        if(groupid != null ? !groupid.equals(activity.groupid) : activity.groupid != null) return false;
        if(p1 != null ? !p1.equals(activity.p1) : activity.p1 != null) return false;
        if(p2 != null ? !p2.equals(activity.p2) : activity.p2 != null) return false;
        if(p3 != null ? !p3.equals(activity.p3) : activity.p3 != null) return false;
        if(p4 != null ? !p4.equals(activity.p4) : activity.p4 != null) return false;
        return syncFlag != null ? syncFlag.equals(activity.syncFlag) : activity.syncFlag == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (groupid != null ? groupid.hashCode() : 0);
        result = 31 * result + (p1 != null ? p1.hashCode() : 0);
        result = 31 * result + (p2 != null ? p2.hashCode() : 0);
        result = 31 * result + (p3 != null ? p3.hashCode() : 0);
        result = 31 * result + (p4 != null ? p4.hashCode() : 0);
        result = 31 * result + (syncFlag != null ? syncFlag.hashCode() : 0);
        return result;
    }
}
