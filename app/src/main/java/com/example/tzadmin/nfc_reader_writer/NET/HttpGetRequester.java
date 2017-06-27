package com.example.tzadmin.nfc_reader_writer.NET;

import android.os.AsyncTask;

import com.github.kevinsawicki.http.HttpRequest;

/**
 * Created by velor on 6/27/17.
 */

public class HttpGetRequester extends AsyncTask<String, Long, String> {
    @Override
    protected String doInBackground(String... urls) {
        try {
            return HttpRequest.get(urls[0]).body();
        } catch (HttpRequest.HttpRequestException exception) {
            return null;
        }
    }
}
