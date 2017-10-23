package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Adapters.RouteViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.Route;
import com.example.tzadmin.nfc_reader_writer.Database.User;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;

import org.litepal.crud.DataSupport;

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

        states = new ArrayList((Collection) DataSupport.where("isvip like ?", "0").findFirst(Route.class));

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
        startActivityForResult(states.get(position).getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(targetRoute != null) {
                String RfcId = data.getStringExtra("RfcId");
                User user = new User().selectUserByRfcId(RfcId);
                if (user != null) {
                    if(isSubscrube) {
                        if (user.getRouteid().equals(-1)) {
                            if(targetRoute.getLeft() > 0) {
                                user.setRouteid(targetRoute.getId());
                                user.update(user.getId());
                                routeGridVie.invalidateViews();
                                Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(this, Message.COUNT_FULL, Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(this, Message.REGISTER_ERROR_SUB_ALREADY, Toast.LENGTH_SHORT).show();
                    } else {
                        if(user.getRouteid() == targetRoute.getId()) {
                            user.AddMoney(targetRoute.getPrice(), Message.userVisitRoute(targetRoute.getName()));
                            Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(this, Message.USER_NOT_SUBSCRUBE_TO_ROUTE, Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
                startActivityForResult(targetRoute.getName());
            }
        }
    }

    public void startActivityForResult(String name) {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        intent.putExtra("name", name);
        startActivityForResult(intent, 200);
    }
}
