package com.example.tzadmin.nfc_reader_writer.NET;

import android.os.AsyncTask;
import android.speech.tts.Voice;
import android.text.BoringLayout;
import android.view.ViewDebug;

import com.github.kevinsawicki.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PolicySpi;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Created by velor on 6/27/17.
 */

public class HttpRequester extends AsyncTask<RequestNode, ResponceNode, Integer> {

    private RequestDelegate delegate;
    private Integer stage;

    public HttpRequester (RequestDelegate delegate, Integer stage) {
        this.delegate = delegate;
        this.stage = stage;
    }

    private void progressUpdate (ResponceNode... values) {
        if (delegate != null) {
            for (ResponceNode item : values)
                delegate.RequestDone(item.url, item.responce, item.passData, stage);
        }
    }

    @Override
    protected void onPostExecute(Integer success) {
        if (delegate != null)
            delegate.TaskDone(success);

        super.onPostExecute(success);
    }

    @Override
    protected Integer doInBackground(RequestNode... urls) {
        try {
            for (RequestNode item : urls) {
                if (item.method == RequestMethod.GET) {
                    String body = HttpRequest.get(item.url, item.params, true).body();
                    progressUpdate( new ResponceNode(item.url, body, item.backParam) );
                } else if (item.method == RequestMethod.POST) {
                    String body = HttpRequest.post(item.url).form(item.params).body();
                    progressUpdate( new ResponceNode(item.url, body, item.backParam) );
                }
            }
        } catch (HttpRequest.HttpRequestException exception) {
            return stage;
        }
        return stage;
    }
}