package com.example.tzadmin.nfc_reader_writer.NET;

import android.util.Pair;

import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by velor on 6/28/17.
 */

public class RequestNode {
    public String url;
    public RequestMethod method;
    public Map<String, String> params;

    void initIt() {
        url = "";
        method = RequestMethod.GET;
        params = new HashMap<>();
    }

    public RequestNode(String url) {
        this.url = url;
    }

    public RequestNode(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public RequestNode(String url, RequestMethod method, Pair<String, String>... params) {
        this.url = url;
        this.method = method;

        for (Pair<String, String> item : params) {
            this.params.put(item.first, item.second);
        }
    }
}
