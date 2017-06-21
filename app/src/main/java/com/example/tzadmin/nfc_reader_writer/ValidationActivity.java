package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.User;

public class ValidationActivity extends AppCompatActivity implements View.OnClickListener{

    TextView firstName, lastName, surName;
    Button btn_clear;
    User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        firstName = (TextView) findViewById(R.id.tv_valid_firstName);
        lastName = (TextView) findViewById(R.id.tv_valid_lastName);
        surName = (TextView) findViewById(R.id.tv_valid_surName);
        btn_clear = (Button)findViewById(R.id.btn_validClear);
        btn_clear.setOnClickListener(this);
        startActivityForResult(new Intent(this, ScanNfcActivity.class), 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if(resultCode == RESULT_OK) {
                    String RfcId = data.getStringExtra("RfcId");
                    user = Database.selectUserByRfcId(RfcId);
                    if(user != null) {
                        firstName.setText(user.cFirstName);
                        lastName.setText(user.cLastName);
                        surName.setText(user.cSurname);
                    } else {
                        Toast.makeText(this,
                                Message.USER_THIS_BRACER_NOT_FOUND,
                                Toast.LENGTH_LONG).show();
                        //TODO что делать дальше ?
                    }
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_validClear:
                if(this.user != null) {
                    this.user.cRfcId = "-1";
                    Database.updateBracer(user.id, user.cRfcId);
                    //Database.update("tbUsers_cache", user);
                    firstName.setText("");
                    lastName.setText("");
                    surName.setText("");
                    user = null;
                    Toast.makeText(this, Message.CLEAR_RFCID_SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                    //TODO что делать дальше ?
                }
                break;
        }
    }
}
