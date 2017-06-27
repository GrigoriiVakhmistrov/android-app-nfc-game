package com.example.tzadmin.nfc_reader_writer.Models;

import android.media.Image;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by forz on 22.06.17.
 */

public class Shop {
    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation
    public Integer image;
    @MAnnotation
    public String name;
    @MAnnotation
    public String info;

    public Button btn;

    public Shop(){
        id = -1;
        image = -1;
        name = "-1";
        info = "-1";

    }
}
