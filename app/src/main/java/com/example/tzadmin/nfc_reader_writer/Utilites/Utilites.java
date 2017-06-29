package com.example.tzadmin.nfc_reader_writer.Utilites;

/**
 * Created by velor on 6/29/17.
 */

public class Utilites {
    public static int tryParseInt(String value, int def) {
        try {
            return Integer.parseInt(value);
        } catch(NumberFormatException nfe) {
            // Log exception.
            return def;
        }
    }
}
