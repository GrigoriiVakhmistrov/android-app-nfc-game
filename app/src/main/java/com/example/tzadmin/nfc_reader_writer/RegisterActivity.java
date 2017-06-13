package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Adapters.AutoCompleteAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.DatabaseHelper;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    EditText et_patronymic, et_search, et_name, et_lastName;
    Button btn_register;
    AutoCompleteTextView autoCompleted;
    final int _requestCode = 200;

    ArrayList<User> users;
    DatabaseHelper dbHelper;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.btn_readRegister);
        et_search = (EditText) findViewById(R.id.autoCompleteTextView);
        et_name = (EditText)findViewById(R.id.et_name_register);
        et_lastName = (EditText)findViewById(R.id.et_lastname_register);
        et_patronymic = (EditText)findViewById(R.id.et_patronymic_register);

        btn_register.setOnClickListener(this);

        dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getReadableDatabase());

        users = Database.selectUsers();

        //autoCompleted = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        //autoCompleted.setAdapter(new AutoCompleteAdapter(this, users));
        //autoCompleted.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_readRegister:
                if(!et_name.getText().toString().equals("") &&
                        !et_lastName.getText().toString().equals("") &&
                        !et_patronymic.getText().toString().equals("")) {
                    Intent intent = new Intent(this, ScanNfcActivity.class);
                    intent.putExtra("name",
                            et_name.getText().toString() + " " +
                                    et_lastName.getText().toString() + " " +
                                    et_patronymic.getText().toString()
                    );
                    startActivityForResult(intent, _requestCode);
                } else {
                    Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode) {
           case _requestCode:
               if(resultCode == RESULT_OK) {
                   String RfcId = data.getStringExtra("NfcId");
                   //TODO FIXME
                   User user = new User(true);
                   user.cFirstName = et_name.getText().toString();
                   user.cLastName = et_lastName.getText().toString();
                   user.cSurname = et_patronymic.getText().toString();
                   user.cRfcId = RfcId;

                   Database.insert("tbUsers", user);
                   Database.insert("tbUsers_cache", user);

                   Toast.makeText(this, "Пользователь " + et_name.getText().toString() +
                           et_lastName.getText().toString() +
                           et_patronymic.getText().toString() + " успешно зарегистрирован", Toast.LENGTH_SHORT).show();
               }
               break;
       }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        intent.putExtra("name",
                et_search.getText().toString()
        );
        startActivityForResult(intent, _requestCode);
    }
}
