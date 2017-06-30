package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/24/17.
 */

public class Morda extends BaseModel {

    public Morda () {
        id = -1;
        fio = "";
        description = "";
        pic = "";
    }

    public Morda(Integer id, String fio, String description, String pic) {
        this.id = id;
        this.fio = fio;
        this.description = description;
        this.pic = pic;
    }

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String fio;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "")
    public String pic;

    @Override
    public String GetTableName() {
        return "tbMorda";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Morda)) return false;

        Morda morda = (Morda) o;

        if(id != null ? !id.equals(morda.id) : morda.id != null) return false;
        if(fio != null ? !fio.equals(morda.fio) : morda.fio != null) return false;
        if(description != null ? !description.equals(morda.description) : morda.description != null) return false;
        return pic != null ? pic.equals(morda.pic) : morda.pic == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (pic != null ? pic.hashCode() : 0);
        return result;
    }
}
