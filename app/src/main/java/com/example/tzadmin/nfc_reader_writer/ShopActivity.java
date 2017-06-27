package com.example.tzadmin.nfc_reader_writer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.example.tzadmin.nfc_reader_writer.Adapters.ShopAdapter;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    ArrayList<Shop> shops;
    ShopAdapter shopAdapter;
    GridView lv_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        lv_shop = (GridView) findViewById(R.id.gridView_shop_main);
        //shops = new Shop().getAllItems();
        if(shops != null) {
            shopAdapter = new ShopAdapter(this, shops);
            lv_shop.setAdapter(shopAdapter);
        }
    }

    public void subscrubeListener (ArrayList<Shop> items) {
        for (Shop item : items) {
            //item.btn =
        }
    }
}
