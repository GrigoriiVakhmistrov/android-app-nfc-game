package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    EditText id;
    ImageView avatar;
    AutoCompleteTextView autoCompleted;
    final int _requestCode = 200;
    final String[] items = { "Ivanov Anton1", "Ivanova Karina2", "Ivanov Anton3", "Ivanova Karina4", "Ivanov Anton5", "Ivanova Karina6", "Kotlov Andrey", "Indykov Danil",
            "Stecenko andrey", "Ponfilova yulia" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        avatar = (ImageView) findViewById(R.id.image_register);
        //TODO FixMe
        avatar.setImageResource(R.mipmap.ic_launcher_round);
        //
        id = (EditText) findViewById(R.id.et_id_register);

        helper.setFilterEditBox(id, 9);

        autoCompleted = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleted.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, items));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_readRegister:
                startActivityForResult(new Intent(this, ScanNfcActivity.class), _requestCode);
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode) {
           case _requestCode:
               if(resultCode == _requestCode) {
                   byte[] bytes = data.getByteArrayExtra("name");
               }
               break;
       }
    }
}
