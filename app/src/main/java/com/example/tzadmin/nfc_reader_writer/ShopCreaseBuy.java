package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.annotation.RequiresApi;
import com.example.tzadmin.nfc_reader_writer.Adapters.CreaseBuyAdapter;
import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.MoneyLogs;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.Utilites.Utilites;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShopCreaseBuy extends AppCompatActivity implements View.OnClickListener {

    int currentShopId = -1;
    Shop currentShop = null;

    ImageView shopImage;
    TextView  name;
    TextView price;
    TextView description;

    EditText money;
    Button addButton;
    Button saveButton;

    GridView GView;

    ArrayList<MoneyLogs> logs;

    int currentMoney = -1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_crease_buy);

        shopImage = (ImageView) findViewById(R.id.image_shop_crease);

        name = (TextView) findViewById(R.id.name);
        name.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        name.setTextColor(getResources().getColor(R.color.colorBtn));

        price = (TextView) findViewById(R.id.price);
        price.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        price.setTextColor(getResources().getColor(R.color.colorBtn));

        description = (TextView) findViewById(R.id.description);
        description.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        price.setTextColor(getResources().getColor(R.color.colorBtn));

        money = (EditText) findViewById(R.id.money);

        addButton = (Button) findViewById(R.id.buttonAdd);
        saveButton = (Button) findViewById(R.id.buttonSave);

        GView = (GridView) findViewById(R.id.gridView_shop_main);


        Intent i = getIntent();
        currentShopId = i.getIntExtra("id", -1);

        if (currentShopId == -1) {
            Toast.makeText(this, "Товар не был передан", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentShop = new Shop();
        currentShop.id = currentShopId;

        currentShop = (Shop) currentShop.selectOneByParams();

        if (currentShop == null) {
            Toast.makeText(this, "Товар не найден", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        addButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        if(!currentShop.pic.equals("")) {
            Picasso.with(this)
                    .load(currentShop.pic)
                    .placeholder(R.drawable.shop_circle)
                    .into(shopImage);
        }

        name.setText(currentShop.name);
        description.setText(currentShop.description);
        price.setText(currentShop.price.toString());

        logs = new ArrayList<>();

        GView.setAdapter(new CreaseBuyAdapter(this, logs));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonAdd) {
            if (money.getText().equals("")) {
                Toast.makeText(this, "Поле Деньги не может быть пустым", Toast.LENGTH_SHORT).show();
                return;
            }

            currentMoney = Utilites.tryParseInt(money.getText().toString(), -1);

            if (currentMoney == -1) {
                Toast.makeText(this, "Поле Деньги должно содержить только цифры", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, ScanNfcActivity.class);
            intent.putExtra("name", currentShop.name);
            startActivityForResult(intent, 200);
        } else if (v.getId() == R.id.buttonSave) {
            if (!price.getText().equals("0")) {
                Toast.makeText(this, "Вы недобрали баллов!", Toast.LENGTH_SHORT).show();
                return;
            }

            for (MoneyLogs item : logs) {
                item.insert();
            }

            Toast.makeText(this, "Покупка успешна!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(currentMoney != -1) {
                String RfcId = data.getStringExtra("RfcId");
                User user = new User().selectUserByRfcId(RfcId);
                if(user != null) {
                    if(user.getBallance() >= currentMoney){
                        MoneyLogs m = new MoneyLogs();
                        m.userid = user.id;
                        m.description = "Покупка - " + currentShop.name;
                        m.money = currentMoney;
                        m.type = MoneyLogs.Type.REMOVE_MONEY.toString();
                        m.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                        logs.add(m);
                        GView.invalidateViews();

                        int cp = Utilites.tryParseInt(price.getText().toString(), -1);

                        price.setText(String.valueOf(cp - currentMoney));
                    } else
                        Toast.makeText(this, Message.MONEY_LOW, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
