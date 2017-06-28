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

public class SpickersActivity extends AppCompatActivity implements View.OnClickListener {

    //TODO use image lib picaso
    ArrayList<Morda> spickers;
    Morda spicker = null;
    boolean isSubscrube;
    String RfcId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spickers);
        findViewById(R.id.image_chekin_spicker1).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker2).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker3).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker4).setOnClickListener(this);
        isSubscrube = getIntent().getBooleanExtra("isSubscrube", true);
    }

    @Override
    public void onClick(View v) {
        spickers = (ArrayList<Morda>) new Morda().selectAll();
        if(spicker != null) {
            switch (v.getId()) {
                case R.id.image_chekin_spicker1:
                    spicker = spickers.get(0) != null ? spickers.get(0) : null;
                    break;
                case R.id.image_chekin_spicker2:
                    spicker = spickers.get(1) != null ? spickers.get(1) : null;
                    break;
                case R.id.image_chekin_spicker3:
                    spicker = spickers.get(2) != null ? spickers.get(2) : null;
                    break;
                case R.id.image_chekin_spicker4:
                    spicker = spickers.get(3) != null ? spickers.get(3) : null;
                    break;
            }
            if (spicker != null)
                startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            this.RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                if(isSubscrube) {
                    user.subscribe(spicker.id);
                    user.update();
                    Toast.makeText(this,
                            Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                } else {
                    //TODO its chekin check spiker
                    finish();
                }
            } else {
                Toast.makeText(this,
                        Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
