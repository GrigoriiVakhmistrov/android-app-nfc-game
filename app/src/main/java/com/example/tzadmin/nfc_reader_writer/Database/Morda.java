package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class Morda extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public String fio;
    public Integer capacity;
    public String description;
    public String pic;

    public Integer getLeft() {
        /*UserMorda u = new UserMorda();
        u.mordaid = id;
        Collection<UserMorda> users = (Collection<UserMorda>) u.selectAllByParams();
        if(users == null)
            return capacity;

        return capacity - users.size();*/
        return -1;
    }
}
