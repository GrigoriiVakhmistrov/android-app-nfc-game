package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Collection;

/**
 * Created by yurii on 19.10.2017.
 */

public class Group extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private String name;
    private String description;
    private String totemname;
    private String totemimage;
    private String color;
    private String colorhex;
    private Integer price;
    private Integer vip;

    public Collection<User> getUsers() {
        return DataSupport.where("groupid like ?", String.valueOf(id)).find(User.class);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTotemname() {
        return totemname;
    }

    public void setTotemname(String totemname) {
        this.totemname = totemname;
    }

    public String getTotemimage() {
        return totemimage;
    }

    public void setTotemimage(String totemimage) {
        this.totemimage = totemimage;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getColorhex() {
        return colorhex;
    }

    public void setColorhex(String colorhex) {
        this.colorhex = colorhex;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getVip() {
        return vip;
    }

    public void setVip(Integer vip) {
        this.vip = vip;
    }
}
