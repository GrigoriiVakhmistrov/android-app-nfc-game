package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tzadmin.nfc_reader_writer.Adapters.MoneyAdapter;
import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.example.tzadmin.nfc_reader_writer.Models.Group;
import com.example.tzadmin.nfc_reader_writer.Models.Morda;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.NET.Sync;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ValidationActivity extends AppCompatActivity {

    Timer timerSync;
    MyTimerTask timerSyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation);
        startActivityForResult(new Intent(this, ScanNfcActivity.class), 200);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if(MainActivity.itsOnlyValidationApp) {
                timerSync = new Timer();
                timerSyncTask = new MyTimerTask();
                timerSync.schedule(timerSyncTask, 20000);
            }
            String RfcId = data.getStringExtra("RfcId");
            User user = new User().selectUserByRfcId(RfcId);
            if (user != null) {

                ((ListView) findViewById(R.id.lv_history_valid)).
                        setAdapter(new MoneyAdapter(this, user.getMoneyLog()));

                TextView validFio = ((TextView) findViewById(R.id.tv_valid_fio));
                validFio.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                validFio.setText(Message.concatFio(user));
                validFio.setTextColor(getResources().getColor(R.color.colorBtn));

                TextView validPoints = ((TextView) findViewById(R.id.tv_valid_points));
                validPoints.setText("Баллы: " + String.valueOf(user.getBallance()));
                validPoints.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                validPoints.setTextColor(getResources().getColor(R.color.colorBtn));

                TextView validRating = ((TextView) findViewById(R.id.tv_valid_rating));
                validRating.setText("Рейтинг: " + String.valueOf(user.getRating()));
                validRating.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                validRating.setTextColor(getResources().getColor(R.color.colorBtn));


                if (user.getRoute() != null && !user.routeid.equals(-1)) {
                    TextView validRoutes = ((TextView) findViewById(R.id.tv_valid_routes));
                    validRoutes.setText("Маршрут: " + user.getRoute().name);
                    validRoutes.setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    validRoutes.setTextColor(getResources().getColor(R.color.colorBtn));
                }else {
                    ((TextView) findViewById(R.id.tv_valid_routes)).
                            setText(Message.NO_ROUTE);
                    ((TextView) findViewById(R.id.tv_valid_routes)).
                            setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView) findViewById(R.id.tv_valid_routes)).
                            setTextColor(getResources().getColor(R.color.colorBtn));
                }

                if(user.getSubscribed().size() > 0) {
                    ArrayList<Morda> spiker = (ArrayList)user.getSubscribed();
                    ((TextView)findViewById(R.id.spiker_valid)).setText("Спикер: " + spiker.get(0).fio);
                    ((TextView)findViewById(R.id.spiker_valid)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView)findViewById(R.id.spiker_valid)).setTextColor(getResources().getColor(R.color.colorBtn));
                } else {
                    ((TextView) findViewById(R.id.spiker_valid)).setText(Message.NO_SPIKER);
                    ((TextView) findViewById(R.id.spiker_valid)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                    ((TextView)findViewById(R.id.spiker_valid)).setTextColor(getResources().getColor(R.color.colorBtn));
                }

                try {
                    Group userGroup = user.getGroup();
                    if (userGroup != null && !user.groupid.equals(-1)) {
                        Resources res = getResources();
                        String mDrawableName = userGroup.totemimage + "_v2";
                        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
                        Drawable drawable = res.getDrawable(resID );

                        findViewById(R.id.image_valid).
                                setBackground(drawable);

                    } else {
                        findViewById(R.id.image_valid).
                                setBackgroundResource(R.drawable.ic_spiker_not_found);

                        ((TextView)findViewById(R.id.text_team_valid)).setText(Message.NO_CLAN);
                        ((TextView)findViewById(R.id.text_team_valid)).setTypeface(SingletonFonts.getInstanse(this).getKarlson());
                        ((TextView)findViewById(R.id.text_team_valid)).setTextColor(getResources().getColor(R.color.colorBtn));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, Message.USER_THIS_BRACER_NOT_FOUND, Toast.LENGTH_LONG).show();
                finish();
            }
        } else
            finish();
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    finish();
                }
            });
        }
    }
}
