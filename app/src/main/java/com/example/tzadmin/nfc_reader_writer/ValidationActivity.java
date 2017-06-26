package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.User;

public class ValidationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        startActivityForResult(new Intent(this, ScanNfcActivity.class), RESULT_OK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                //TODO set adapter history
                //((ListView) findViewById(R.id.lv_history_valid)).setAdapter();

                ((TextView) findViewById(R.id.tv_valid_fio)).setText(Message.concatFio(user));
                ((TextView) findViewById(R.id.tv_valid_points)).setText(user.getBallance());

                //TODO set rating
                //((TextView) findViewById(R.id.tv_valid_rating)).setText();

                //TODO set routes
                //((TextView) findViewById(R.id.tv_valid_routes)).setText();

                //TODO set image clan
                //((ImageView) findViewById(R.id.image_valid)).setImageResource();

                ((TextView) findViewById(R.id.nameClan_valid)).setText(Message.concatFio(user));
                ((TextView) findViewById(R.id.route_valid)).setText(Message.concatFio(user));
            }
        }
    }
}
