package com.example.tzadmin.nfc_reader_writer.Database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by yurii on 19.10.2017.
 */

public class UserMorda extends DataSupport {
    @Column(unique = true)
    public Integer id;
    public Integer userid;
    public Integer syncFlag;
    public Integer mordaid;

    public Morda getMorda() {
        return DataSupport.where("id like ?", String.valueOf(mordaid)).findFirst(Morda.class);
    }
}
