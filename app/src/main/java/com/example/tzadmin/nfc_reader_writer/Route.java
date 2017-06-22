package com.example.tzadmin.nfc_reader_writer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.tzadmin.nfc_reader_writer.Adapters.RouteViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Models.Route_State;

import java.util.ArrayList;

public class Route extends AppCompatActivity {

    ArrayList<Route_State> states = new ArrayList<>();

    GridView routeGridVie;

    private void initStates(){
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        initStates();

        routeGridVie = (GridView)findViewById(R.id.route_grid);

        RouteViewAdapter routeViewAdapter = new RouteViewAdapter(this, states);

        routeGridVie.setAdapter(routeViewAdapter);



    }


}
