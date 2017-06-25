package com.example.tzadmin.nfc_reader_writer.NET;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by forz on 16.06.17.
 */

public final class JSON {
    private static final Gson GSON = new GsonBuilder()
            .create();

    public static String toString(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromString(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
