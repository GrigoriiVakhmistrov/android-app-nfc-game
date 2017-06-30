package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.example.tzadmin.nfc_reader_writer.Adapters.MainGridViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.DatabaseHelper;
import com.example.tzadmin.nfc_reader_writer.Enums.MainMenu;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.NET.Sync;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String[] values = {
            "Регистрация",
            "В команду",
            "Chekin",
            "Кубики",
            "Маршруты",
            "Спикеры",
            "Магазин",
            "Валидация"
    };
    
    int[] imageId = {
            R.drawable.mainiconreg,
            R.drawable.register_teamxxxhdpi,
            R.drawable.main_chekin,
            R.drawable.main_cubes_xxxhdpi,
            R.drawable.mainiconreg,
            R.drawable.mainiconreg,
            R.drawable.mainiconreg,
            R.drawable.mainiconreg
    };

    GridView gridView;

    Button refreshButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        gridView = (GridView) findViewById(R.id.gridView_main);
        MainGridViewAdapter adapter = new MainGridViewAdapter(MainActivity.this, values, imageId);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        refreshButton = (Button) findViewById(R.id.btnRefresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Sync();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        switch (position) {
            case MainMenu.REGISTER:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case MainMenu.REGISTER_TEAM:
                startActivity(new Intent(this, TeamsActivity.class));
                break;
            case MainMenu.CHEKIN_SCANER:
                startActivity(new Intent(this, CheckinScaner.class));
                break;
            case MainMenu.CUBES:
                startActivity(new Intent(this, ThrowCubes.class));
                break;
            case MainMenu.REGISTER_ROUTES:
                startActivity(new Intent(this, RouteActivity.class));
                break;
            case MainMenu.REGISTER_SPICKERS:
                startActivity(new Intent(this, SpickersActivity.class));
                break;
            case MainMenu.SHOP:
                startActivity(new Intent(this, ShopActivity.class));
                break;
            case MainMenu.VALIDATION:
                startActivity(new Intent(this, ValidationActivity.class));
                break;
        }
    }
}
