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
}
