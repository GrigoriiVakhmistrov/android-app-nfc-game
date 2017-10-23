package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.Collection;

/**
 * Created by yurii on 19.10.2017.
 */

public class Morda extends DataSupport {
    @Column(unique = true)
    private Integer id;
    private String fio;
    private Integer capacity;
    private String description;
    private String pic;

    public Integer getLeft() {
        Collection<UserMorda> users = DataSupport.where("mordaid like ?", String.valueOf(id)).find(UserMorda.class);
        if(users == null)
            return capacity;

        return capacity - users.size();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
