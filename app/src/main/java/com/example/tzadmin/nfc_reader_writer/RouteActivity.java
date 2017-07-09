package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Adapters.RouteViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.User;

import java.util.ArrayList;
import java.util.Collection;

public class RouteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Route> states = new ArrayList<>();
    Route targetRoute = null;
    GridView routeGridVie;
    Boolean isSubscrube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        Route tmp = new Route();
        tmp.isvip = 0;

        states = new ArrayList(tmp.selectAllByParams());

        isSubscrube = getIntent().getBooleanExtra("isSubscrube", true);

        routeGridVie = (GridView)findViewById(R.id.route_grid);
        RouteViewAdapter routeViewAdapter = new RouteViewAdapter(this, states);
        routeGridVie.setAdapter(routeViewAdapter);
        routeGridVie.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        targetRoute = states.get(position);
        startActivityForResult(states.get(position).name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(targetRoute != null) {
                String RfcId = data.getStringExtra("RfcId");
                User user = new User().selectUserByRfcId(RfcId);
                if (user != null) {
                    if(isSubscrube) {
                        if (user.routeid.equals(-1)) {
                            user.routeid = targetRoute.id;
                            user.update();
                            routeGridVie.invalidateViews();
                            Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(this, Message.REGISTER_ERROR_SUB_ALREADY, Toast.LENGTH_SHORT).show();
                    } else {
                        if(user.routeid == targetRoute.id) {
                            user.AddMoney(targetRoute.price, Message.userVisitRoute(targetRoute.name));
                            Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(this, Message.USER_NOT_SUBSCRUBE_TO_ROUTE, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } else
                    Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
                startActivityForResult(targetRoute.name);
            }
        }
    }

    public void startActivityForResult(String name) {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        intent.putExtra("name", name);
        startActivityForResult(intent, 200);
    }
}
