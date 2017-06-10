package com.example.tzadmin.nfc_reader_writer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity {

    EditText id;
    ImageView avatar;
    AutoCompleteTextView autoCompleted;
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
}
