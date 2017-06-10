package com.example.tzadmin.nfc_reader_writer;

import android.text.InputFilter;
import android.widget.EditText;

/**
 * Created by tzadmin on 10.06.17.
 */

public class helper {

    public static void setFilterEditBox (EditText box, int value) {
        int maxLength = value;
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(maxLength);
        box.setFilters(FilterArray);
    }
}
