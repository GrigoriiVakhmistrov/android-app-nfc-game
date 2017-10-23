package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Adapters.ActivitysAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.Event;
import com.example.tzadmin.nfc_reader_writer.Database.User;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;

import java.util.ArrayList;

public class ActivitysesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ActivitysAdapter adapter;
    ArrayList<Event> activitys;
    GridView lv_main;
    Event thisActivity = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityses);
        lv_main = (GridView) findViewById(R.id.lv_activitys_main);

        activitys = (ArrayList) new Event().selectAll();
        adapter = new ActivitysAdapter(this, activitys);

        if(activitys != null && activitys.size() > 0) {
            lv_main.setAdapter(adapter);
            lv_main.setOnItemClickListener(this);
        } else {
            Toast.makeText(this,
                    Message.ACTIVITYS_NOT_FOUND, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        thisActivity = activitys.get(position);
        if(thisActivity != null)
            startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                user.AddMoney(thisActivity.getPrice(),
                        "Активность - " + thisActivity.getName());
                Toast.makeText(this,
                        Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this,
                        Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
        }
    }
}
