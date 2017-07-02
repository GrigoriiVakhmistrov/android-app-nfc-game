package com.example.tzadmin.nfc_reader_writer;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Fonts.SingletonFonts;
import com.example.tzadmin.nfc_reader_writer.Messages.Message;
import com.skyfishjy.library.RippleBackground;

public class ScanNfcActivity extends AppCompatActivity {

    NfcAdapter adapter;
    PendingIntent pendingIntent;
    String name, imageTeam = null;
    TextView infoScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_nfc);
        infoScan = (TextView)findViewById(R.id.infoScan);
        infoScan.setTypeface(SingletonFonts.getInstanse(this).getKarlson());

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        imageTeam = intent.getStringExtra("imageTeam");
        if(imageTeam != null )
            findViewById(R.id.scan_image_team).setBackground(getDrawable(imageTeam));

        infoScan.setText(name);

        adapter = NfcAdapter.getDefaultAdapter(this);
        if(adapter == null || !adapter.isEnabled()) {
            setResult(RESULT_CANCELED, new Intent());
            Toast.makeText(this,
                    Message.DEVICE_NOT_FOUND_NFC_ADAPTHER, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        final RippleBackground rippleBackground =
                (RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        pendingIntent = PendingIntent.getActivity(
            this, 0, new Intent(this,
                        getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    public void onPause() {
        super.onPause();
        adapter.disableForegroundDispatch(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Tag tagFromIntent =
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String RfcId = toHex(tagFromIntent.getId());

        Intent resultInt = new Intent();
        resultInt.putExtra("RfcId", RfcId);
        resultInt.putExtra("name", name);
        setResult(RESULT_OK, resultInt);
        finish();
    }

    private String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = bytes.length - 1; i >= 0; --i) {
            int b = bytes[i] & 0xff;
            if (b < 0x10)
                sb.append('0');
            sb.append(Integer.toHexString(b));
            if (i > 0) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private Drawable getDrawable (String drawableName) {
        drawableName += "_v2";
        Resources res = getResources();
        int resID = res.getIdentifier(drawableName , "drawable", getPackageName());
        return res.getDrawable(resID );
    }

}
