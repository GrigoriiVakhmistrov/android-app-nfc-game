package com.example.tzadmin.nfc_reader_writer;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.skyfishjy.library.RippleBackground;

public class ScanNfcActivity extends AppCompatActivity {

    NfcAdapter adapter;
    PendingIntent pendingIntent;
    String nameBlinds;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_nfc);
        textView = (TextView)findViewById(R.id.test);
        Intent intent = getIntent();
        nameBlinds = intent.getStringExtra("name");
        textView.setText(nameBlinds);
        adapter = NfcAdapter.getDefaultAdapter(this);
        if(adapter == null || !adapter.isEnabled()) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
            return;
        }

        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();

        pendingIntent = PendingIntent.getActivity(
            this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
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
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String RfcId = toHex(tagFromIntent.getId());

        /*if(Database.isNfcIdAlreadyExist(RfcId)) {
            Toast.makeText(this, "Браслет уже зарегистрирован", Toast.LENGTH_LONG).show();
            return;
        }*/

        Intent resultInt = new Intent();
        resultInt.putExtra("NfcId", RfcId);
        resultInt.putExtra("name", nameBlinds);
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
}
