package com.example.tzadmin.nfc_reader_writer.Models;

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
    @MAnnotation
    public Integer pic;
    @MAnnotation(DefaultValue = "0")
    public Integer price;



    //public Button btn;

    public Shop() {
        id = -1;
        pic = -1;
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Shop)) return false;

        Shop shop = (Shop) o;

        if(id != null ? !id.equals(shop.id) : shop.id != null) return false;
        if(name != null ? !name.equals(shop.name) : shop.name != null) return false;
        if(description != null ? !description.equals(shop.description) : shop.description != null) return false;
        if(pic != null ? !pic.equals(shop.pic) : shop.pic != null) return false;
        return price != null ? price.equals(shop.price) : shop.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
