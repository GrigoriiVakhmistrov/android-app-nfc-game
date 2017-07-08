package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Route extends BaseModel {

    public Route () {
        this.id = -1;
        this.name = "";
        this.description = "";
        this.capacity = 0;
        this.price = 0;
        this.isvip = -1;
//        this.isdeleted = "-1";
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String name;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "0")
    public Integer capacity;
    @MAnnotation(DefaultValue = "0")
    public Integer price;
    @MAnnotation(DefaultValue = "-1")
    public Integer isvip;
//    @MAnnotation
//    public String isdeleted;


    @Override
    public String GetTableName() {
        return "tbRoutes";
    }

    public Integer getLeft() {
        User u = new User();
        u.routeid = id;
        Collection<User> users = (Collection<User>) u.selectAllByParams();
        if(users == null)
            return capacity;

        return capacity - users.size();
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.routeid = id;

        return (Collection<User>)u.selectAllByParams();

    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Route)) return false;

        Route route = (Route) o;

        if(id != null ? !id.equals(route.id) : route.id != null) return false;
        if(name != null ? !name.equals(route.name) : route.name != null) return false;
        if(description != null ? !description.equals(route.description) : route.description != null) return false;
        if(capacity != null ? !capacity.equals(route.capacity) : route.capacity != null) return false;
        return price != null ? price.equals(route.price) : route.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (capacity != null ? capacity.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
