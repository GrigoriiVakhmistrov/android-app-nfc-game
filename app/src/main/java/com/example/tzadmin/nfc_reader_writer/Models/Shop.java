package com.example.tzadmin.nfc_reader_writer.Models;

import android.widget.Button;
import java.util.Collection;

/**
 * Created by forz on 22.06.17.
 */

public class Shop extends BaseModel{
    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String name;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "")
    public String pic;
    @MAnnotation(DefaultValue = "0")
    public Integer price;



    //public Button btn;

    public Shop() {
        id = -1;
        pic = "";
        name = "";
        description = "";
        price = 0;
    }

    public Collection<Shop> GetAllItems(){
        return (Collection<Shop>) new Shop().selectAll();
    }

    @Override
    public String GetTableName() {
        return "tbShop";
    }

}
