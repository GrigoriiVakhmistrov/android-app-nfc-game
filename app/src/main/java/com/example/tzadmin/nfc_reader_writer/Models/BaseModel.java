package com.example.tzadmin.nfc_reader_writer.Models;

import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.ModelInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Created by velor on 6/20/17.
 */

public abstract class BaseModel implements ModelInterface {

    public String getInsertQuery() {
        StringBuilder queryBuilder = new StringBuilder();

        return queryBuilder.toString();
    }

    public String GetLogTableName() {
        return "log" + GetTableName();
    }


    public boolean InsertIt() {
        return false;
    }

    public Collection<? extends BaseModel> selectAll() {
        //Collection<Map<String, String>> data = Database.get().select(GetTable())
    }

    public Collection<? extends BaseModel> select (Type model, Map<String, String> andWhare) {

        BaseModel currentModel;
        try {
            currentModel = (BaseModel) model.getClass().getConstructors()[0].newInstance();
        } catch (Exception e) {
            return null;
        }

        String whereCause = null;
        String[] whereParams = null;

        if (andWhare != null) {

            StringBuilder sbuilder = new StringBuilder();
            whereParams = new String[andWhare.size()];

            int i = 0;
            for (Map.Entry<String, String> entry : andWhare.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                if (i == 0) {
                    sbuilder.append(" ").append(key).append(" = ?");
                } else {
                    sbuilder.append(" AND ").append(key).append(" = ?");
                }

                whereParams[i] = value;
                i++;
            }
        }

        Collection<Map<String, String>> data = Database.get().select(currentModel.GetTableName(), null, )

        currentModel.GetTableName();

        //TODO Replace with proper code
        return null;
    }
}
