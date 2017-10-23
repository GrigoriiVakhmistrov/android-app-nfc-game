package com.example.tzadmin.nfc_reader_writer;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by velor on 6/20/17.
 */

public class SharedApplication extends Application {
    private static SharedApplication instance;
    public static SharedApplication get() { return instance; }

    public boolean syncActive;

    @Override
    public void onCreate() {
        LitePal.initialize(this);
        syncActive = false;
        super.onCreate();
        instance = this;
    }
}
