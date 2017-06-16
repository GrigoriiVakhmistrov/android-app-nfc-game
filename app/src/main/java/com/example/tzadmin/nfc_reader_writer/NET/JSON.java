package com.example.tzadmin.nfc_reader_writer.NET;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by forz on 16.06.17.
 */

public class JSON {
    public static String toString (Object object) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(object);
    }

    public static Object fromString (String json, Class _class) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Object object = gson.fromJson(json, _class);
        return object;
    }
}
