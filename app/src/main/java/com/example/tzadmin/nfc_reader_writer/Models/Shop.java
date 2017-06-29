package com.example.tzadmin.nfc_reader_writer.Models;

import android.widget.Button;
import java.util.Collection;

/**
 * Created by forz on 22.06.17.
 */

public class Shop extends BaseModel{
    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(PrimaryKey = true)
    public String name;
    @MAnnotation(PrimaryKey = true)
    public String description;
    @MAnnotation(PrimaryKey = true)
    public Integer pic;
    @MAnnotation(PrimaryKey = true)
    public Integer price;



    //public Button btn;

    public Shop() {
        id = -1;
        pic = -1;
        name = "-1";
        description = "-1";
        price = -1;
    }

    public Collection<Shop> GetAllItems(){
        return (Collection<Shop>) new Shop().selectAll();
    }

    @Override
    public String GetTableName() {
        return "tbShop";
    }

}
