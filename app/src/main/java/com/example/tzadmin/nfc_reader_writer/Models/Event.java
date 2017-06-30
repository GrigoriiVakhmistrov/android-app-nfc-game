package com.example.tzadmin.nfc_reader_writer.Models;

/**
 * Created by velor on 6/27/17.
 */

public class Event extends BaseModel {

    @MAnnotation(PrimaryKey = true)
    public Integer id;
    @MAnnotation(DefaultValue = "")
    public String name;
    @MAnnotation(DefaultValue = "")
    public String description;
    @MAnnotation(DefaultValue = "0")
    public Integer price;

    public Event() {
        id = -1;
        name = "";
        price = 0;
        description = "";
    }

    @Override
    public String GetTableName() {
        return "Event";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Event)) return false;

        Event event = (Event) o;

        if(id != null ? !id.equals(event.id) : event.id != null) return false;
        if(name != null ? !name.equals(event.name) : event.name != null) return false;
        if(description != null ? !description.equals(event.description) : event.description != null) return false;
        return price != null ? price.equals(event.price) : event.price == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        return result;
    }
}
