package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.tzadmin.nfc_reader_writer.Adapters.MainGridViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.DatabaseHelper;
import com.example.tzadmin.nfc_reader_writer.Enums.MainMenu;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] values = {
            "Регистрация",
            "Валидатор",
            /*
            "Регистрация",
            "Test2",
            "Test3",
            "Test4",
            "Test5",
            "Test6",
            */
    };
    
    int[] imageId = {
            /*
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            */
            R.drawable.mainIconReg,
            R.drawable.mainIconReg,


    };

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView_main);

        MainGridViewAdapter adapter = new MainGridViewAdapter(MainActivity.this, values, imageId);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        switch (position) {
            case MainMenu.REGISTER:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}
