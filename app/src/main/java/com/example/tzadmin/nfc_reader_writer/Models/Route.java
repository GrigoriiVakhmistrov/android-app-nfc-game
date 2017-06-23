package com.example.tzadmin.nfc_reader_writer.Models;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by velor on 6/23/17.
 */

public class Route extends BaseModel {

    public Route () {
        this.id = -1;
        this.name = "-1";
        this.description = "-1";
        this.capacity = -1;
        this.isdeleted = "-1";
    }

    @MAnotation(PrimaryKey = true)
    public Integer id;
    @MAnotation
    public String name;
    @MAnotation
    public String description;
    @MAnotation
    public Integer capacity;
    @MAnotation
    public String isdeleted;


    @Override
    public String GetTableName() {
        return "tbRoutes";
    }

    public Integer getLeft() {
        Collection<User> users = (Collection<User>) new User().selectAll();

        int cap = capacity;

        for (User item : users) {
            if (item.cGroupId.equals(id))
                cap --;
        }

        return cap;
    }

    public Collection<User> getUsers() {
        User u = new User();
        u.cRouteId = id;

        return (Collection<User>)u.selectByParams();

    }


    public Collection<Route_State> getDrawModels() {
        Collection<Route_State> states = new ArrayList<>();

        Collection<Route> tmp = (Collection<Route>) selectAll();

        for (Route r : tmp) {
            Route_State state = new Route_State(r.name, r.capacity);
            states.add(state);
        }

        return states;
    }
}
