package com.example.tzadmin.nfc_reader_writer.Models;

import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.support.annotation.VisibleForTesting;

import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.ModelInterface;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by velor on 6/20/17.
 */

public abstract class BaseModel implements ModelInterface {

    public String GetLogTableName() {
        return "log_" + GetTableName();
    }

    public Collection<? extends BaseModel> selectAll() {
        return select(getClass(), null, null, null, null);
    }

    public Collection<? extends BaseModel> selectAllByParams() {
        Map<String, String> findParams = new HashMap<>();

        Field[] fields = getClass().getFields();
        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnotation.class)) {
                MAnotation a = item.getAnnotation(MAnotation.class);

                String key = item.getName();
                if (!a.FieldName().equals(""))
                    key = a.FieldName();
                String val = null;

                if (item.getType() == String.class) {
                    try {
                        val = (String) item.get(this);
                    } catch (Exception e) { }
                } else if (item.getType() == Integer.class) {
                    try {
                        val = ((Integer) item.get(this)).toString();
                    } catch (Exception e) { }
                }

                if (val != null && !val.equals(a.DefaultValue()))
                    findParams.put(key, val);
            }
        }

        if (findParams.size() == 0)
            findParams = null;
        return select(getClass(), findParams, null, null, null);
    }

    public BaseModel selectOne() {
        Collection<? extends BaseModel> all = selectAll();

        if (all.size() == 0) return  null;

        for (BaseModel obj : all) return obj;

        return null;
    }

    public BaseModel selectOneByParams() {
        Collection<? extends BaseModel> all = selectAllByParams();

        if (all.size() == 0) return  null;

        for (BaseModel obj : all) return obj;

        return null;
    }


    public boolean insert() {
        ContentValues values = new ContentValues();

        Field[] fields = getClass().getFields();
        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnotation.class)) {
                MAnotation a = item.getAnnotation(MAnotation.class);

                String key = item.getName();
                if (!a.FieldName().equals(""))
                    key = a.FieldName();
                String val = null;

                if (item.getType() == String.class) {
                    try {
                        val = (String) item.get(this);
                    } catch (Exception e) { }
                } else if (item.getType() == Integer.class) {
                    try {
                        val = ((Integer) item.get(this)).toString();
                    } catch (Exception e) { }
                }

                if (!a.PrimaryKey())
                    values.put(key, val);
            }
        }

        long newId = Database.get().insert(GetTableName(), null, values);
        if (newId == -1) return false;

        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnotation.class)) {
                MAnotation a = item.getAnnotation(MAnotation.class);

                if (a.PrimaryKey()) {
                    try {
                        item.set(this, Integer.valueOf((int)newId));
                    } catch (Exception e) { }
                }
            }
        }

        return true;
    }

    public boolean update() {
        ContentValues values = new ContentValues();
        String where = "";
        String[] whereARGS = new String[1];


        Field[] fields = getClass().getFields();
        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnotation.class)) {
                MAnotation a = item.getAnnotation(MAnotation.class);

                String key = item.getName();
                if (!a.FieldName().equals(""))
                    key = a.FieldName();
                String val = null;

                if (item.getType() == String.class) {
                    try {
                        val = (String) item.get(this);
                    } catch (Exception e) { }
                } else if (item.getType() == Integer.class) {
                    try {
                        val = ((Integer) item.get(this)).toString();
                    } catch (Exception e) { }
                }

                if (!a.PrimaryKey()) {
                    values.put(key, val);
                } else {
                    where = key + " = ? ";
                    whereARGS[0] = val;
                }

            }
        }

        long newId = Database.get().update(GetTableName(), values, where, whereARGS);

        if (newId == 0) return false;
        else return true;
    }

    public Collection<? extends BaseModel> select (Class<?> model, Map<String, String> andWhare, Collection<String> groupBy, Collection<String> orderBy, String limit) {

        BaseModel currentModel;
        try {
            currentModel = (BaseModel) model.getConstructors()[0].newInstance();
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


        if (orderBy != null && orderBy.size() > 0) {
            StringBuilder sBuilder = new StringBuilder();

            int i = 0;
            for (String item : orderBy) {
                if (i == 0)
                    sBuilder.append(item);
                else
                    sBuilder.append(", ").append(item);
                i++;
            }

            _orderBy = sBuilder.toString();
        }


        Collection<Map<String, String>> data = Database.get().select(currentModel.GetTableName(), null, whereCause, whereParams, _groupBy, null, _orderBy, limit);

        Collection<BaseModel> retData = new ArrayList<>();

        Field[] fields = model.getFields();

        for (Map<String, String> item : data) {
            BaseModel m = null;
            try {
                m = (BaseModel)model.getConstructors()[0].newInstance();


                for (Field f : fields) {
                    if (f.isAnnotationPresent(MAnotation.class)) {
                        MAnotation a = f.getAnnotation(MAnotation.class);

                        String key = f.getName();
                        if (!a.FieldName().equals(""))
                            key = a.FieldName();

                        if (item.containsKey(key)) {
                            if (f.getType() == String.class) {
                                try {
                                    f.set(m, item.get(key));
                                } catch (Exception e) { }
                            } else if (f.getType() == Integer.class) {
                                try {
                                    f.set(m, Integer.valueOf(item.get(key)));
                                } catch (Exception e) { }
                            }
                        }

                    }
                }

            } catch (Exception e) {
                continue;
            }

            retData.add(m);
        }

        return retData;
    }
}
