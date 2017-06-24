package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.tzadmin.nfc_reader_writer.Adapters.RouteViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.User;

import java.util.ArrayList;

public class RouteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Route> states = new ArrayList<>();
    Route targetRoute = null;
    GridView routeGridVie;

    private void initStates(){
        /*
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        states.add(new Route_State("test", 15));
        */
        states = new ArrayList(new Route().selectAll());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        initStates();

        routeGridVie = (GridView)findViewById(R.id.route_grid);
        RouteViewAdapter routeViewAdapter = new RouteViewAdapter(this, states);
        routeGridVie.setAdapter(routeViewAdapter);
        routeGridVie.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        targetRoute = states.get(position);
        Intent intent = new Intent(this, ScanNfcActivity.class);
        intent.putExtra("name", states.get(position).name);
        startActivityForResult( intent, RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(targetRoute != null) {
                String RfcId = data.getStringExtra("RfcId");
                User user = new User().selectUserByRfcId(RfcId);
                if(user != null) {
                    user.cRouteId = targetRoute.id;
                    user.update();
                    //TODO сомнения по слдеющей функции, переотрисует ли она адаптер
                    routeGridVie.invalidateViews();
                }
            }
        }
    }
}
