package com.example.tzadmin.nfc_reader_writer.network;

public interface Callback<T> {
    void receive(T t);
}
