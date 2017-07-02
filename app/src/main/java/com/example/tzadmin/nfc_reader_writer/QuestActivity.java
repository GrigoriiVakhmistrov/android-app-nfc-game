package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.media.audiofx.AudioEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.EventLogTags;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.User;

public class QuestActivity extends AppCompatActivity implements View.OnClickListener {

    User user;
    String RfcId;
    EditText value, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        value = (EditText) findViewById(R.id.moneyOperation_value);
        description = (EditText) findViewById(R.id.moneyOperation_desc);
        findViewById(R.id.moneyOperation_write).setOnClickListener(this);

        startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            RfcId = data.getStringExtra("RfcId");
            user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                fillTextViews();
            } else {
                Toast.makeText(this,
                        Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if(resultCode == RESULT_CANCELED)
            finish();
    }

    private void fillTextViews () {
        user = new User().selectUserByRfcId(RfcId);
        if(user != null) {
            TextView moneyOperationBalance = ((TextView) findViewById(R.id.moneyOperation_balance));
            moneyOperationBalance.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
            moneyOperationBalance.setText(Message.concatFio(user) + " ваш баланс = " + user.getBallance());
        }
    }

    private void clearEditBoxs () {
        value.getText().clear();
        description.getText().clear();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moneyOperation_write:
                if(value.getText().length() != 0) {
                    user.AddMoney(Integer.valueOf(
                           value.getText().toString()), description.getText().toString());
                    user.update();
                    clearEditBoxs();
                    Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                }
                break;
        }
        fillTextViews();
    }
}
