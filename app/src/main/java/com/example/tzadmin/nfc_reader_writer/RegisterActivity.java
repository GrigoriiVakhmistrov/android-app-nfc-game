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
import com.example.tzadmin.nfc_reader_writer.Database.User;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;

import org.litepal.crud.DataSupport;

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

        users = (ArrayList<User>) new User().selectAll();

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

                    User u = DataSupport.where("firstname like ? and lastname like ? and patronymic like ?",
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            surName.getText().toString()).findFirst(User.class);

                    if (u != null) {
                        Toast.makeText(this, Message.DUBLICATE_USER, Toast.LENGTH_SHORT).show();
                        break;
                    }

                    Intent intent = new Intent(this, ScanNfcActivity.class);
                    intent.putExtra("name",
                            lastName.getText().toString() + " " +
                            firstName.getText().toString() + " " +
                                    surName.getText().toString());
                    startActivityForResult(intent, _registerCode);
                } else {
                    Toast.makeText(this, Message.FIELDS_NOT_FILLED, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void clearFields() {
        firstName.setText("");
        lastName.setText("");
        surName.setText("");
        et_search.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String RfcId = data.getStringExtra("RfcId");
            switch (requestCode) {
                case _registerCode:
                    if (new User().isNfcIdAlreadyExist(RfcId)) {
                        Toast.makeText(this, Message.BRACER_ALREADY_EXIST, Toast.LENGTH_LONG).show();
                        return;
                    }

                    Toast.makeText(this,
                            Message.userSuccessfullyRegistered(register(RfcId)),
                            Toast.LENGTH_SHORT).show();
                    clearFields();
                    break;
                case _bindCode:
                    if (new User().isNfcIdAlreadyExist(RfcId)) {
                        Toast.makeText(this, Message.BRACER_ALREADY_EXIST, Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(this,
                            Message.userSuccessfullyRegistered(register(RfcId)),
                            Toast.LENGTH_SHORT).show();
                    bind(RfcId);
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        selectedUser = (User) parent.getItemAtPosition(position);
        if(!selectedUser.rfcid.equals("")) {
            Toast.makeText(this,
                    Message.isUserHaveBraced(selectedUser),
                    Toast.LENGTH_LONG).show();
            return;
        }

        intent.putExtra("name", Message.concatFio(selectedUser));
        startActivityForResult(intent, _bindCode);
    }

    private User register (String RfcId) {
        User user = new User();
        user.firstname = firstName.getText().toString();
        user.lastname = lastName.getText().toString();
        user.patronymic = surName.getText().toString();
        user.rfcid = RfcId;
        user.save();

        return user;
    }

    private void bind (String RfcId) {
        selectedUser.rfcid = RfcId;
        selectedUser.update(selectedUser.id);
        clearFields();
    }
}
