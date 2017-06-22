package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by kim on 22.06.2017.
 */

public class Route_State {
    private String routeName;
    private Integer routeCount;

    public Route_State(String routeName, Integer routeCount){
        this.routeCount = routeCount;
        this.routeName = routeName;
    }

    public String GetRouteName(){
        return routeName;
    }

    public Integer GetRouteCount(){
        return routeCount;
    }

}


