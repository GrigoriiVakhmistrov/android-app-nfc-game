package com.example.tzadmin.nfc_reader_writer;

import android.app.Application;

/**
 * Created by velor on 6/20/17.
 */

public class SharedApplication extends Application {
    private static SharedApplication instance;
    public static SharedApplication get() { return instance; }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
