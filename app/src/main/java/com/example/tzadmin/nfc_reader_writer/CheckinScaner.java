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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chekin_spikers:
                //startActivityForResult();
                break;
            case R.id.btn_chekin_activity:
                startActivity(new Intent(this, Activitys.class));
                break;
            case R.id.btn_chekin_quest:
                startActivity(new Intent(this, QuestActivity.class));
                break;
            case R.id.btn_chekin_route:
                //startActivity(new Intent(this, .class));
                break;
        }
    }
}
