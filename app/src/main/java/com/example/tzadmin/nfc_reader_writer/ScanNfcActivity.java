package com.example.tzadmin.nfc_reader_writer;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ScanNfcActivity extends AppCompatActivity {

    NfcAdapter adapter;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_nfc);

        adapter = NfcAdapter.getDefaultAdapter(this);
        if(adapter == null || !adapter.isEnabled()) {
            setResult(RESULT_CANCELED, new Intent());
            finish();
            return;
        }

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
        String NfcId = toHex(tagFromIntent.getId());
        Intent resultInt = new Intent();
        resultInt.putExtra("NfcId", NfcId);
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
