package com.example.tzadmin.nfc_reader_writer.network;

import android.support.test.runner.AndroidJUnit4;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpResponse;
import com.koushikdutta.async.http.callback.HttpConnectCallback;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RequestManagerTest {
    @Test
    public void postTest() throws Exception {
        AsyncHttpResponse response = RequestManager.post("http://httpbin.org/post").get();
        assertEquals(200, response.code());
    }

    @Test
    public void getRequestTest() throws Exception {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AsyncHttpResponse response = RequestManager.get("http://httpbin.org/get", new HttpConnectCallback() {
            @Override
            public void onConnectCompleted(Exception ex, AsyncHttpResponse response) {
                atomicBoolean.set(response.code() == 200);
            }
        }).get();

        assertEquals(200, response.code());
        assertNotNull(response.message());
        assertTrue(atomicBoolean.get());
    }

    @Test
    public void getStringTest() throws Exception {
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        String response = RequestManager.get("http://httpbin.org/get", new AsyncHttpClient.StringCallback() {
            @Override
            public void onCompleted(Exception e, AsyncHttpResponse source, String result) {
                atomicBoolean.set(source.code() == 200 && !result.isEmpty());
            }
        }).get();

        assertFalse(response.isEmpty());
        assertTrue(atomicBoolean.get());
    }
}