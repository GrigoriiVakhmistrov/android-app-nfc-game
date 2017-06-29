package com.example.tzadmin.nfc_reader_writer.network;

import android.util.Log;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpGet;
import com.koushikdutta.async.http.AsyncHttpPost;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.callback.HttpConnectCallback;

/**
 * This class contains {@link AsyncHttpClient} for app and provide GET/POST functions
 */
public final class RequestManager {
    private static final AsyncHttpClient client = AsyncHttpClient.getDefaultInstance();

    private static final HttpConnectCallback EMPTY_CALLBACK = new HttpConnectCallback() {
        @Override
        public void onConnectCompleted(Exception ex, AsyncHttpResponse response) {

        }
    };

    private RequestManager() {
        throw new UnsupportedOperationException();
    }

    public static Future<AsyncHttpResponse> post(String uri) {
        Log.d("[RequestManager][POST]", uri);
        AsyncHttpPost post = new AsyncHttpPost(uri);
        return client.execute(post, EMPTY_CALLBACK);
    }

    public static Future<AsyncHttpResponse> get(String uri, HttpConnectCallback callback) {
        Log.d("[RequestManager][GET]", uri);
        AsyncHttpGet get = new AsyncHttpGet(uri);
        return client.execute(get, callback);
    }

    public static Future<String> get(String uri, AsyncHttpClient.StringCallback callback) {
        Log.d("[RequestManager][GET]", uri);
        AsyncHttpGet get = new AsyncHttpGet(uri);
        return client.executeString(get, callback);
    }
}
