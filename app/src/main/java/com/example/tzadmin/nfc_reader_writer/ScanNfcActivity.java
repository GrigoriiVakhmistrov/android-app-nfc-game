package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ScanNfcActivity extends AppCompatActivity {

    NfcAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_nfc);

        adapter = NfcAdapter.getDefaultAdapter(this);
        if(adapter == null || ) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
            return;
        }
    }
}
