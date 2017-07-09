package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tzadmin.nfc_reader_writer.NET.Sync;

public class MainValidationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_validation);
        findViewById(R.id.btnRefresh1).setOnClickListener(this);
        findViewById(R.id.btnItsOnlyValidation).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRefresh1:
                new Sync();
                break;
            case R.id.btnItsOnlyValidation:
                startActivity(new Intent(this, ValidationActivity.class));
                break;
        }
    }
}
