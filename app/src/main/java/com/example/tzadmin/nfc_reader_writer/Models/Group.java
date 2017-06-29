package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Group extends BaseModel {

    public Group () {
        id = -1;
        name = "-1";
        description = "-1";
        totemname = "-1";
        totemimage = "-1";
        color = "-1";
        colorhex = "-1";
//        isdeleted = "-1";
        price = 0;
        vip = 0;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public String name;
    @MAnnotation
    public String description;
    @MAnnotation
    public String totemname;
    @MAnnotation
    public String totemimage;
    @MAnnotation
    public String color;//TODO wtf is this?
    @MAnnotation
    public String colorhex;//TODO wtf is this?
//    @MAnnotation
//    public String isdeleted;
    @MAnnotation
    public Integer price;
    @MAnnotation
    public Integer vip;



    @Override
    public String GetTableName() {
        return "tbGroups";
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.groupid = id;

        return (Collection<User>)u.selectAllByParams();
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Group)) return false;

        Group group = (Group) o;

        if(id != null ? !id.equals(group.id) : group.id != null) return false;
        if(name != null ? !name.equals(group.name) : group.name != null) return false;
        if(description != null ? !description.equals(group.description) : group.description != null) return false;
        if(totemname != null ? !totemname.equals(group.totemname) : group.totemname != null) return false;
        if(totemimage != null ? !totemimage.equals(group.totemimage) : group.totemimage != null) return false;
        if(color != null ? !color.equals(group.color) : group.color != null) return false;
        if(colorhex != null ? !colorhex.equals(group.colorhex) : group.colorhex != null) return false;
        if(price != null ? !price.equals(group.price) : group.price != null) return false;
        return vip != null ? vip.equals(group.vip) : group.vip == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (totemname != null ? totemname.hashCode() : 0);
        result = 31 * result + (totemimage != null ? totemimage.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (colorhex != null ? colorhex.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (vip != null ? vip.hashCode() : 0);
        return result;
    }
}
