package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.User;

public class ValidationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null) {

                if(user.getMoneyLog().size() > 0)
                    ((ListView) findViewById(R.id.lv_history_valid)).setAdapter((ListAdapter) user.getMoneyLog());

                ((TextView) findViewById(R.id.tv_valid_fio)).setText(Message.concatFio(user));

                ((TextView) findViewById(R.id.tv_valid_points)).setText(String.valueOf(user.getBallance()));

                ((TextView) findViewById(R.id.tv_valid_rating)).setText(String.valueOf(user.getRating()));

                if(user.getRoute() != null)
                    ((TextView) findViewById(R.id.tv_valid_routes)).setText(user.getRoute().name);

                //TODO FIX ME : THIS EXEPTION ->
                try {
                    ((ImageView) findViewById(R.id.image_valid)).setImageResource(R.drawable.class.getField(user.getGroup().name).getInt(getResources()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

                ((TextView) findViewById(R.id.nameClan_valid)).setText(Message.concatFio(user));
                ((TextView) findViewById(R.id.route_valid)).setText(Message.concatFio(user));
            } else {
                Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
