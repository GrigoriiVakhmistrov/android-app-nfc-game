package com.example.tzadmin.nfc_reader_writer.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * This class holds {@link Gson} instance for app. Instance contains all required serializers/deserializers for classes.
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

    public static <T> T fromString(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
