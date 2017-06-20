package com.example.tzadmin.nfc_reader_writer.Models;

import android.support.annotation.VisibleForTesting;

import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.ModelInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.CollationElementIterator;
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

    public Collection<? extends BaseModel> select (Type model, Map<String, String> andWhare, Collection<String> groupBy, Collection<String> orderBy, String limit) {

        BaseModel currentModel;
        try {
            currentModel = (BaseModel) model.getClass().getConstructors()[0].newInstance();
        } catch (Exception e) {
            return null;
        }

        String whereCause = null;
        String[] whereParams = null;
        String _groupBy = null;
        String _orderBy = null;

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

            whereCause = sbuilder.toString();
        }

        if (groupBy != null && groupBy.size() > 0) {
            StringBuilder sBuilder = new StringBuilder();

            int i = 0;
            for (String item : groupBy) {
                if (i == 0)
                    sBuilder.append(item);
                else
                    sBuilder.append(", ").append(item);
                i++;
            }

            _groupBy = sBuilder.toString();
        }

        //TODO Add order by if nessary
        /*
        if (orderBy != null && orderBy.size() > 0) {
            StringBuilder sBuilder = new StringBuilder();

            int i = 0;
            for (String item : groupBy) {
                if (i == 0)
                    sBuilder.append(item);
                else
                    sBuilder.append(", ").append(item);
                i++;
            }

            _groupBy = sBuilder.toString();
        }*/


        Collection<Map<String, String>> data = Database.get().select(currentModel.GetTableName(), null, whereCause, whereParams, _groupBy, null, _orderBy, limit);

        Collection<? extends BaseModel> retData = new ArrayList<>();

        for (Map<String, String> item : data) {
            BaseModel m = null;
            try {
                m = (BaseModel) model.getClass().getConstructors()[0].newInstance();
            } catch (Exception e) {
            } finally {
                continue;
            }

            retData.add(m);
        }

        //TODO Replace with proper code
        return retData;
    }
}
