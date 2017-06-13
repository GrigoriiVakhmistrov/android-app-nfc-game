package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Models.User;

public class Validation extends AppCompatActivity {

    TextView fname, lname, sname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        fname = (TextView) findViewById(R.id.tv_fname);
        lname = (TextView) findViewById(R.id.tv_lname);
        sname = (TextView) findViewById(R.id.tv_sname);

        startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 200:
                if(resultCode == RESULT_OK) {
                    String RfcId = data.getStringExtra("NfcId");
                    User user = Database.selectUsersByNfcId(RfcId);
                    fname.setText(user.cFirstName);
                    lname.setText(user.cLastName);
                    sname.setText(user.cSurname);
                }
                break;
        }
    }
}
