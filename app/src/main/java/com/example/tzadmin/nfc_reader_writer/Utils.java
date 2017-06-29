package com.example.tzadmin.nfc_reader_writer;

import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;

public final class Utils {
    private Utils() {}

    @Nullable
    public static <T> T getFirst(Collection<T> collection) {
        Iterator<T> iterator = collection.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    public static int parseIntDefault(String s, int radix, int def) {
        if (s == null) {
            throw new NumberFormatException("null");
        }

        if (radix < Character.MIN_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " less than Character.MIN_RADIX");
        }

        if (radix > Character.MAX_RADIX) {
            throw new NumberFormatException("radix " + radix +
                    " greater than Character.MAX_RADIX");
        }

        int result = 0;
        boolean negative = false;
        int i = 0, len = s.length();
        int limit = -Integer.MAX_VALUE;
        int multmin;
        int digit;

        if (len > 0) {
            char firstChar = s.charAt(0);
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Integer.MIN_VALUE;
                } else if (firstChar != '+')
                    return def;

                if (len == 1) // Cannot have lone "+" or "-"
                    return def;
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(s.charAt(i++),radix);
                if (digit < 0) {
                    return def;
                }
                if (result < multmin) {
                    return def;
                }
                result *= radix;
                if (result < limit + digit) {
                    return def;
                }
                result -= digit;
            }
        } else {
            return def;
        }
        return negative ? result : -result;
    }
}
