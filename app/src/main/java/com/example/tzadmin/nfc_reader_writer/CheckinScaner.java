package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.tzadmin.nfc_reader_writer.Models.Activitys;

public class CheckinScaner extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin_scaner);
        findViewById(R.id.btn_chekin_spikers).setOnClickListener(this);
        findViewById(R.id.btn_chekin_activity).setOnClickListener(this);
        findViewById(R.id.btn_chekin_quest).setOnClickListener(this);
        findViewById(R.id.btn_chekin_route).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chekin_spikers:
                Intent intentSpikers = new Intent(this, SpickersActivity.class);
                intentSpikers.putExtra("isSubscrube", false);
                startActivity(intentSpikers);
                break;
            case R.id.btn_chekin_activity:
                startActivity(new Intent(this, ActivitysesActivity.class));
                break;
            case R.id.btn_chekin_quest:
                startActivity(new Intent(this, QuestActivity.class));
                break;
            case R.id.btn_chekin_route:
                Intent intentRoute = new Intent(this, RouteActivity.class);
                intentRoute.putExtra("isSubscrube", false);
                startActivity(intentRoute);
                break;
        }
    }
}
