package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.User;

import java.util.ArrayList;
import java.util.Collection;

public class SpickersActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Morda> morda;
    Morda spicker = null;
    String RfcId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spickers);
        findViewById(R.id.image_chekin_spicker1).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker2).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker3).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker4).setOnClickListener(this);
        morda = (ArrayList<Morda>) new Morda().selectAll();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_chekin_spicker1:
                spicker = morda.get(0);
                break;
            case R.id.image_chekin_spicker2:
                spicker = morda.get(1);
                break;
            case R.id.image_chekin_spicker3:
                spicker = morda.get(2);
                break;
            case R.id.image_chekin_spicker4:
                spicker = morda.get(3);
                break;
        }
        startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            this.RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null && spicker != null) {
                //TODO
                //Известны Юзер - Спикер
                //Подписать юзера на спикера
            }
        }
    }
}
