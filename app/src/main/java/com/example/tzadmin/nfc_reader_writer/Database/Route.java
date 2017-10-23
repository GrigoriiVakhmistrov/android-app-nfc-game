package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Collection;

/**
 * Created by yurii on 19.10.2017.
 */

public class Route extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private String name;
    private String description;
    private Integer capacity;
    private Integer price;
    private Integer isvip;

    public Integer getLeft() {
        Collection<User> users = DataSupport.where("routeid like ?", String.valueOf(id)).find(User.class);
        if(users == null)
            return capacity;

        return capacity - users.size();
    }

    public Collection<User> getUsers() {
        return DataSupport.where("routeid like ?", String.valueOf(id)).find(User.class);
    }

    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public Integer getPrice() {
        return this.price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getIsvip() {
        return this.isvip;
    }

    public void setIsvip(Integer isvip) {
        this.isvip = isvip;
    }
}
