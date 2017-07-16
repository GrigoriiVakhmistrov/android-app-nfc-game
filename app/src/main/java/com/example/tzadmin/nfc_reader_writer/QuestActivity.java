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
import com.example.tzadmin.nfc_reader_writer.Utilites.Utilites;

public class QuestActivity extends AppCompatActivity implements View.OnClickListener {

    User user;
    String RfcId;
    EditText value, desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        value = (EditText) findViewById(R.id.moneyOperation_value);
        desc = (EditText) findViewById(R.id.moneyOperation_desc);
        findViewById(R.id.moneyOperation_write).setOnClickListener(this);

        Utilites.setFilterEditBox(value, 9);

        TextView moneyOperationBalance = ((TextView) findViewById(R.id.moneyOperation_balance));
        moneyOperationBalance.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        moneyOperationBalance.setText("Введите название квеста и его сумму");
        moneyOperationBalance.setTextColor(getResources().getColor(R.color.colorBtn));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            RfcId = data.getStringExtra("RfcId");
            user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                if(value.getText().length() != 0) {
                    user.AddMoney(Integer.valueOf(
                           value.getText().toString()), desc.getText().toString());
                    user.update();
                    Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this,
                        Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            }
            Intent i = new Intent(this, ScanNfcActivity.class);
            i.putExtra("name", "Добавление квеста - "
                    + desc.getText().toString() + " со стоимостью - " + value.getText());
            startActivityForResult(i, 200);
        }

        if(resultCode == RESULT_CANCELED)
            finish();
    }

    /*private void fillTextViews () {
        user = new User().selectUserByRfcId(RfcId);
        if(user != null) {
            TextView moneyOperationBalance = ((TextView) findViewById(R.id.moneyOperation_balance));
            moneyOperationBalance.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
            moneyOperationBalance.setText(Message.concatFio(user) +
                    " - текущий баланс = " + user.getBallance() + " баллов");
            moneyOperationBalance.setTextColor(getResources().getColor(R.color.colorBtn));
        }
    }

    private void clearEditBoxs () {
        value.getText().clear();
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moneyOperation_write:

                if (value.getText().equals("") || desc.getText().equals("")) {
                    Toast.makeText(this,
                            "Все поля обязательны для заполнения", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(this, ScanNfcActivity.class);
                i.putExtra("name", "Добавление квеста со стоимостью - " + value.getText());
                startActivityForResult(i, 200);
                break;
        }
    }
}
