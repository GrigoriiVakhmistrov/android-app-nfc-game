package com.example.tzadmin.nfc_reader_writer;

import android.content.Context;
import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by tzadmin on 09.06.17.
 */

public class Nfc {

    Tag tag;
    NfcAdapter adapter;
    Context context;

    Nfc(Context context) {
        this.context = context;
        adapter = NfcAdapter.getDefaultAdapter(this.context);
    }

    public byte[] read (Intent intent) {
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Parcelable[] msgs =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefRecord firstRecord = ((NdefMessage) msgs[0]).getRecords()[0];
            byte[] payload = firstRecord.getPayload();
            int payloadLength = payload.length;
            int langLength = payload[0];
            int textLength = payloadLength - langLength - 1;
            byte[] text = new byte[textLength];
            System.arraycopy(payload, 1 + langLength, text, 0, textLength);
            //Toast.makeText(this.context, new String(text), Toast.LENGTH_LONG).show();
            return text;
        } else
            return null;
    }

    private void write(String text, Tag tag) throws IOException, FormatException {
        //NdefRecord[] records = { createRecord(text) };
        NdefMessage message = new NdefMessage(text.getBytes());
        Ndef ndef = Ndef.get(tag);
        ndef.connect();
        ndef.writeNdefMessage(message);
        ndef.close();
    }
}
