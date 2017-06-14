package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.User;

public class ValidationActivity extends AppCompatActivity {

    TextView firstName, lastName, SurName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        firstName = (TextView) findViewById(R.id.tv_valid_firstName);
        lastName = (TextView) findViewById(R.id.tv_valid_lastName);
        SurName = (TextView) findViewById(R.id.tv_valid_surName);

        startActivityForResult(new Intent(this, ScanNfcActivity.class), RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESULT_OK:
                if(resultCode == RESULT_OK) {
                    String RfcId = data.getStringExtra("RfcId");
                    User user = Database.selectUserByRfcId(RfcId);
                    if(user != null) {
                        firstName.setText(user.cFirstName);
                        lastName.setText(user.cLastName);
                        SurName.setText(user.cSurname);
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
}
