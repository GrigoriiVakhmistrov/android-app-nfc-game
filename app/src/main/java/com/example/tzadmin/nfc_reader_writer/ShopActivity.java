package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Switch;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Adapters.ShopAdapterV2;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ArrayList<Shop> shops = new ArrayList<>();
    Shop shop = null;
    GridView lv_shop;
    Switch buySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        buySwitch = (Switch) findViewById(R.id.buySwitch);

        shops = new ArrayList(new Shop().selectAll());



        lv_shop = (GridView)findViewById(R.id.gridView_shop_main);
        //lv_shop = (GridView) findViewById(R.id.route_grid);
        ShopAdapterV2 shopAdapter = new ShopAdapterV2(this, shops);

        if(shops != null && shops.size() > 0) {
            lv_shop.setAdapter(shopAdapter);
            lv_shop.setOnItemClickListener(this);
        } else {
            Toast.makeText(this,
                    Message.ITEMS_SHOP_NOT_FOUND, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        shop = shops.get(position);

        if (!buySwitch.isChecked()) {
            Intent intent = new Intent(this, ScanNfcActivity.class);
            intent.putExtra("name", shop.name);
            startActivityForResult(intent, 200);
        } else {
            Intent intent = new Intent(this, ShopCreaseBuy.class);
            intent.putExtra("id", shop.id);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(shop != null) {
                String RfcId = data.getStringExtra("RfcId");
                User user = new User().selectUserByRfcId(RfcId);
                if(user != null) {
                    if(user.getBallance() >= shop.price){
                        user.RemoveMoney(shop.price, "покупка " + shop.name);
                        user.update();

                        Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, Message.MONEY_LOW, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
