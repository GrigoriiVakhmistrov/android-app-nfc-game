package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.example.tzadmin.nfc_reader_writer.Adapters.MainGridViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] values = {
            "Test1",
            "Test2",
            "Test3",
            "Test4",
            "Test5",
            "Test6",
            "Test7",
            "Test8",
            "Test9",
            "Test10",
    };
    
    int[] imageId = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
    };

    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.gridView_main);

        MainGridViewAdapter adapter = new MainGridViewAdapter(MainActivity.this, values, imageId);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Database.SetUp(dbHelper.getWritableDatabase());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        /* TODO
        1 - Определить какой пункт меню был нажат (switch)
        2 - Запустить соответствующее активити
        */
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
