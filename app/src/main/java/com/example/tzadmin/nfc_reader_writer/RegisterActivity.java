package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Adapters.AutoCompleteAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    EditText surName, et_search, firstName, lastName;
    Button btn_register;
    AutoCompleteTextView autoCompleted;
    static final int _registerCode = 1;
    static final int _bindCode = 2;
    ArrayList<User> users;
    User selectedUser = null;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.btn_readRegister);
        et_search = (EditText) findViewById(R.id.autoCompleteTextView);
        firstName = (EditText)findViewById(R.id.et_name_register);
        lastName = (EditText)findViewById(R.id.et_lastname_register);
        surName = (EditText)findViewById(R.id.et_patronymic_register);

        btn_register.setOnClickListener(this);

        users = Database.selectUsers();

        autoCompleted = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleted.setAdapter(new AutoCompleteAdapter(this, users));
        autoCompleted.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_readRegister:
                if(!firstName.getText().toString().equals("") &&
                        !lastName.getText().toString().equals("") &&
                        !surName.getText().toString().equals("")) {
                    Intent intent = new Intent(this, ScanNfcActivity.class);
                    intent.putExtra("name",
                            firstName.getText().toString() + " " +
                                    lastName.getText().toString() + " " +
                                    surName.getText().toString());
                    startActivityForResult(intent, _registerCode);
                } else {
                    Toast.makeText(this, Message.FIELDS_NOT_FILLED, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode) {
           case _registerCode:
               if(resultCode == RESULT_OK) {
                   String RfcId = data.getStringExtra("RfcId");

                   if(Database.isNfcIdAlreadyExist(RfcId)) {
                       Toast.makeText(this, Message.BRACER_ALREADY_EXIST, Toast.LENGTH_LONG).show();
                       return;
                   }

                   //TODO FIXME
                   User user = new User();
                   user.cFirstName = firstName.getText().toString();
                   user.cLastName = lastName.getText().toString();
                   user.cSurname = surName.getText().toString();
                   user.cRfcId = RfcId;

                   Database.insert("tbUsers", user);
                   Database.insert("tbUsers_cache", user);

                   Toast.makeText(this,
                           Message.USER_SUCCESSFULLY_REGISTERED(user),
                           Toast.LENGTH_SHORT).show();
               }
               break;
           case _bindCode:
               if(resultCode == RESULT_OK) {
                   String RfcId = data.getStringExtra("RfcId");
                   selectedUser.cRfcId = RfcId;
                   Database.update("tbUsers", selectedUser);
               }
               break;
       }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        users = Database.selectUsers(); // TODO WARNING synchronize (this.users -> adapter.users)
        selectedUser = users.get(position);

        if(Database.isNfcIdAlreadyExist(selectedUser.cRfcId)) {
            Toast.makeText(this,
                    Message.USER_IS_HAVE_BRACER(selectedUser),
                    Toast.LENGTH_LONG).show();
            return;
        }

        intent.putExtra("name", Message.concatFio(selectedUser));
        startActivityForResult(intent, _bindCode);
    }
}
