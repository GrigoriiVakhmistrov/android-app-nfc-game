package com.example.tzadmin.nfc_reader_writer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.tzadmin.nfc_reader_writer.Adapters.MainGridViewAdapter;

public class Route extends AppCompatActivity {





    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        gridView = (GridView)findViewById(R.id.gridView_route);
        MainGridViewAdapter mainGridViewAdapter = new MainGridViewAdapter()

    }
}
