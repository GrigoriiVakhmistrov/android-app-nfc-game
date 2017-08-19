package com.example.tzadmin.nfc_reader_writer.network;

import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by velor on 6/28/17.
 */

@Deprecated
public class RequestNode {
    public String url;
    public RequestMethod method;
    public Map<String, String> params = new HashMap<>();
    public Object backParam;

    void initIt() {
        url = "";
        method = RequestMethod.GET;
        params = new HashMap<>();
        backParam = null;
    }

    public RequestNode(String url) {
        this.url = url;
    }

    public RequestNode(String url, RequestMethod method) {
        this.url = url;
        this.method = method;
    }

    public RequestNode(String url, RequestMethod method, Object backParam) {
        this.url = url;
        this.method = method;
        this.backParam = backParam;
    }

    public RequestNode(String url, RequestMethod method, Object backParam, Pair<String, String>... params) {
        this.url = url;
        this.method = method;
        this.backParam = backParam;

        for (Pair<String, String> item : params) {
            this.params.put(item.first, item.second);
        }
    }

    public RequestNode(String url, RequestMethod method, Pair<String, String>... params) {
        this.url = url;
        this.method = method;

        for (Pair<String, String> item : params) {
            this.params.put(item.first, item.second);
        }
    }

    public RequestNode(String url, RequestMethod method, Object backParam, Map<String, String> params) {
        this.url = url;
        this.method = method;
        this.backParam = backParam;

        for (Map.Entry<String, String> item : params.entrySet()) {
            this.params.put(item.getKey(), item.getValue());
        }
    }

    public RequestNode(String url, RequestMethod method, Map<String, String> params) {
        this.url = url;
        this.method = method;

        for (Map.Entry<String, String> item : params.entrySet()) {
            this.params.put(item.getKey(), item.getValue());
        }
    }

}
