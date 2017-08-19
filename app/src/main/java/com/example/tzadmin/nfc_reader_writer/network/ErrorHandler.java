package com.example.tzadmin.nfc_reader_writer.network;

import com.koushikdutta.async.http.AsyncHttpResponse;

public interface ErrorHandler {
    void handleHttpError(int code, AsyncHttpResponse response);
}
