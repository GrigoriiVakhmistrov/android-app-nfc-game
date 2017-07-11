package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collection;

public class SpickersActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Morda> spickers;
    Morda spicker = null;
    boolean isSubscrube;
    String RfcId;

    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spickers);

        text1 = (TextView)findViewById(R.id.tv_checkin_spiker1);
        text2 = (TextView)findViewById(R.id.tv_checkin_spiker2);
        text3 = (TextView)findViewById(R.id.tv_checkin_spiker3);
        text4 = (TextView)findViewById(R.id.tv_checkin_spiker4);

        text1.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        text2.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        text3.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
        text4.setTypeface(SingletonFonts.getInstanse(this).getKarlson());

        text1.setTextColor(getResources().getColor(R.color.colorBtn));
        text2.setTextColor(getResources().getColor(R.color.colorBtn));
        text3.setTextColor(getResources().getColor(R.color.colorBtn));
        text4.setTextColor(getResources().getColor(R.color.colorBtn));

        findViewById(R.id.image_chekin_spicker1).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker2).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker3).setOnClickListener(this);
        findViewById(R.id.image_chekin_spicker4).setOnClickListener(this);
        isSubscrube = getIntent().getBooleanExtra("isSubscrube", true);
        if(!isSubscrube)
            findViewById(R.id.spikers_layout).setBackgroundResource(R.drawable.checkin_out_spiker);

        spickers = (ArrayList<Morda>) new Morda().selectAll();

        if(spickers.size() == 0 || spickers == null) {
            findViewById(R.id.image_chekin_spicker1).setBackgroundResource(R.drawable.ic_spiker_not_found);
            findViewById(R.id.image_chekin_spicker2).setBackgroundResource(R.drawable.ic_spiker_not_found);
            findViewById(R.id.image_chekin_spicker3).setBackgroundResource(R.drawable.ic_spiker_not_found);
            findViewById(R.id.image_chekin_spicker4).setBackgroundResource(R.drawable.ic_spiker_not_found);
        } else {
            try {
                if(spickers.get(0) == null)
                    findViewById(R.id.image_chekin_spicker1).setBackgroundResource(R.drawable.ic_spiker_not_found);
                else {
                    Picasso.with(this).load(spickers.get(0).pic).placeholder(R.drawable.ic_spiker_not_found).into((ImageView) findViewById(R.id.image_chekin_spicker1));
                    text1.setText(spickers.get(0).fio);
                    ((TextView)findViewById(R.id.tv_checkin_capacity1)).setText("Свободно мест - " + spickers.get(0).getLeft());
                    ((TextView)findViewById(R.id.tv_checkin_capacity1)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView)findViewById(R.id.tv_checkin_capacity1)).setTextColor(getResources().getColor(R.color.colorBtn));
                }

                if(spickers.get(1) == null)
                    findViewById(R.id.image_chekin_spicker2).setBackgroundResource(R.drawable.ic_spiker_not_found);
                else {
                    Picasso.with(this).load(spickers.get(1).pic).placeholder(R.drawable.ic_spiker_not_found).into((ImageView) findViewById(R.id.image_chekin_spicker2));
                    text2.setText(spickers.get(1).fio);
                    ((TextView)findViewById(R.id.tv_checkin_capacity2)).setText("Свободно мест - " + spickers.get(1).getLeft());
                    ((TextView)findViewById(R.id.tv_checkin_capacity2)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView)findViewById(R.id.tv_checkin_capacity2)).setTextColor(getResources().getColor(R.color.colorBtn));
                }


                if(spickers.get(2) == null)
                    findViewById(R.id.image_chekin_spicker3).setBackgroundResource(R.drawable.ic_spiker_not_found);
                else {
                    Picasso.with(this).load(spickers.get(2).pic).placeholder(R.drawable.ic_spiker_not_found).into((ImageView) findViewById(R.id.image_chekin_spicker3));
                    text3.setText(spickers.get(2).fio);
                    ((TextView)findViewById(R.id.tv_checkin_capacity3)).setText("Свободно мест - " + spickers.get(2).getLeft());
                    ((TextView)findViewById(R.id.tv_checkin_capacity3)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView)findViewById(R.id.tv_checkin_capacity3)).setTextColor(getResources().getColor(R.color.colorBtn));
                }


                if(spickers.get(3) == null)
                    findViewById(R.id.image_chekin_spicker4).setBackgroundResource(R.drawable.ic_spiker_not_found);
                else {
                    Picasso.with(this).load(spickers.get(3).pic).placeholder(R.drawable.ic_spiker_not_found).into((ImageView) findViewById(R.id.image_chekin_spicker4));
                    text4.setText(spickers.get(3).fio);
                    ((TextView)findViewById(R.id.tv_checkin_capacity4)).setText("Свободно мест - " + spickers.get(3).getLeft());
                    ((TextView)findViewById(R.id.tv_checkin_capacity4)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView)findViewById(R.id.tv_checkin_capacity4)).setTextColor(getResources().getColor(R.color.colorBtn));
                }
            } catch (Exception ex) {
                Toast.makeText(this,
                        Message.ERROR_URL_IMAGE,
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(spickers != null && spickers.size() > 0) {
            switch (v.getId()) {
                case R.id.image_chekin_spicker1:
                    spicker = spickers.get(0) != null ? spickers.get(0) : null;
                    break;
                case R.id.image_chekin_spicker2:
                    spicker = spickers.get(1) != null ? spickers.get(1) : null;
                    break;
                case R.id.image_chekin_spicker3:
                    spicker = spickers.get(2) != null ? spickers.get(2) : null;
                    break;
                case R.id.image_chekin_spicker4:
                    spicker = spickers.get(3) != null ? spickers.get(3) : null;
                    break;
            }
            if (spicker != null)
                startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            this.RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if(user != null) {
                if(isSubscrube) {
                    if(user.getSubscribed() == null || user.getSubscribed().size() == 0) {
                        user.subscribe(spicker.id);
                        user.update();
                        Toast.makeText(this,
                                Message.SUCCESSFULLY, Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<Morda> mordas = (ArrayList)user.getSubscribed();
                        Toast.makeText(this,
                                Message.getUserAlreadySubscrubeSpiker(
                                        mordas.get(0)), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Collection<Morda> subscribes = user.getSubscribed();

                    for (Morda m : subscribes) {
                        if (m.id.equals(spicker.id)) {
                            user.AddMoney(800, Message.userVisitSpiker(spicker.fio));
                            Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_LONG).show();
                            finish();
                            return;
                        }
                    }
                    user.AddMoney(400, Message.userVisitSpikerNotOwn(spicker.fio));
                    Toast.makeText(this, Message.SUCCESSFULLY, Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
            } else {
                Toast.makeText(this,
                        Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_SHORT).show();
            }
            //startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
        }
    }
}
