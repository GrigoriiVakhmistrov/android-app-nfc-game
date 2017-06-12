package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.os.Build;
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
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.DatabaseHelper;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener{

    EditText et_patronymic, et_search, et_name, et_lastName;
    Button btn_register;
    AutoCompleteTextView autoCompleted;
    final int _requestCode = 200;

    String[] usersBlinds = new String[] { "Иванов Антон Эдуардович",
            "Котлов Андрей Сергеевич",
            "Иванова Карина Юрьевна",
            "Стеценко Андрей Викторович",
            "Свистун Антон Антонович",
            "Стивцков Денис Олегович",
            "Дираков Сергей Юрьевич",
            "Орел Андрей Андреевич",
            "Стеценко Юлия Юрьевна",
            "Козлов Артем Олегович",
            "Иванов Сергей Юрьевич",
            "Попандос Артем Валерьевич",
            "Дзюба Константин Сергеевич",
            "Письков Валерий Юрьевич",
            "Удиткин Михаил Богданович",
            "Мухин Николая Андреевич",
            "Комаров Михаил Эдуардович",
    };

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
        et_name.setOnClickListener(this);
        btn_register.setOnClickListener(this);

        dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getReadableDatabase());


        autoCompleted = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        autoCompleted.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, usersBlinds));
        autoCompleted.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_readRegister:
               /* User user = new User();
                user.firstname = et_name.getText().toString();
                user.lastname = et_name.getText().toString();
                user.patronymic = et_name.getText().toString();
                user.rfcid = et_name.getText().toString();
                user.isdeleted = et_name.getText().toString();
                user.routeid = et_name.getText().toString();
                user.groupid = et_name.getText().toString();
                user.batchild = et_name.getText().toString();
                user.iscap = et_name.getText().toString();
                ArrayList<User> users = new ArrayList<>();
                users.add(user);
                try {
                    Database.insert("tbUsers", (ArrayList)users);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                String test = "tbUsers";
                ArrayList<Object> objects = Database.select(test.toString());
                users = new ArrayList<>(objects.size());
                for (Object object : objects) {
                    users.add((User) object);
                }
                int b = 5;*/
                if(!et_name.getText().toString().equals("") &&
                        !et_lastName.getText().toString().equals("") &&
                        !et_patronymic.getText().toString().equals("")) {
                    Intent intent = new Intent(this, ScanNfcActivity.class);
                    intent.putExtra("nameBlinds",
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
                   String NfcId = data.getStringExtra("NfcId");
                   String nameBlinds = data.getStringExtra("nameBlinds");
                   Toast.makeText(this, "Пользователь " + nameBlinds + " успешно зарегистрирован", Toast.LENGTH_SHORT).show();
               }
               et_search.setText("");
               break;
       }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ScanNfcActivity.class);
        intent.putExtra("nameBlinds",
                et_search.getText().toString()
        );
        startActivityForResult(intent, _requestCode);
    }
}
