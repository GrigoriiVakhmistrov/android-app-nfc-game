package com.example.tzadmin.nfc_reader_writer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ScanNfcNonstopActivity extends AppCompatActivity {

    TextView info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_nfc_nonstop);
        info = (TextView) findViewById(R.id.scan_nonstop_info);

        //info.getIntent().getStringExtra("info");
    }
}
