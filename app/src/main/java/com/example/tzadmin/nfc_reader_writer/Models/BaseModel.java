package com.example.tzadmin.nfc_reader_writer.Models;

import android.content.ContentValues;
import android.support.annotation.Nullable;
import com.example.tzadmin.nfc_reader_writer.Database.Database;
import com.example.tzadmin.nfc_reader_writer.Database.ModelInterface;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by velor on 6/20/17.
 */

public abstract class BaseModel implements ModelInterface {

    public String GetLogTableName() {
        return "log_" + GetTableName();
    }

    @Nullable
    public Collection<? extends BaseModel> selectAll() {
        return select(getClass(), null, null, null, null);
    }

    @Nullable
    public Collection<? extends BaseModel> selectAllByParams() {
        Map<String, String> findParams = new HashMap<>();

        Field[] fields = getClass().getFields();
        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnnotation.class)) {
                MAnnotation a = item.getAnnotation(MAnnotation.class);

                String key = item.getName();
                if (!a.FieldName().equals(""))
                    key = a.FieldName();
                String val = null;

                try {
                    val = item.get(this).toString();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (val != null && !val.equals(a.DefaultValue()))
                    findParams.put(key, val);
            }
        }

        if (findParams.size() == 0)
            findParams = null;
        return select(getClass(), findParams, null, null, null);
    }

    @Nullable
    public BaseModel selectOne() {
        Collection<? extends BaseModel> all = selectAll();

        return all.size() == 0 ? null : (BaseModel) all.toArray()[0];
    }

    @Nullable
    public BaseModel selectOneByParams() {
        Collection<? extends BaseModel> all = selectAllByParams();

        return all.size() == 0 ? null : (BaseModel) all.toArray()[0];
    }

    public boolean insert() {
        ContentValues values = new ContentValues();

        Field[] fields = getClass().getFields();
        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnnotation.class)) {
                MAnnotation annotation = item.getAnnotation(MAnnotation.class);

                item.setAccessible(true);

                String key = item.getName();
                if (!"".equals(annotation.FieldName()))
                    key = annotation.FieldName();

                String val = null;
                try {
                    val = item.get(this).toString();
                } catch (IllegalAccessException e) {
                    //Ignore
                }

                if (!annotation.PrimaryKey())
                    values.put(key, val);
            }
        }

        long newId = Database.get().insert(GetTableName(), null, values);
        if (newId == -1) return false;

        for (Field item : fields) {
            if (item.isAnnotationPresent(MAnnotation.class)) {
                MAnnotation a = item.getAnnotation(MAnnotation.class);

                if (a.PrimaryKey()) {
                    try {
                        item.set(this, (int) newId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            if (item.isAnnotationPresent(MAnnotation.class)) {
                MAnnotation a = item.getAnnotation(MAnnotation.class);

                item.setAccessible(true);

                String key = item.getName();
                if (!"".equals(a.FieldName()))
                    key = a.FieldName();
                String val = null;

                try {
                    val = item.get(this).toString();
                } catch (IllegalAccessException e) {
                    //Ignore
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

        return newId != 0;
    }

    @Nullable
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

        _groupBy = concatStrings(groupBy, _groupBy);
        _orderBy = concatStrings(orderBy, _orderBy);


        Collection<Map<String, String>> data = Database.get().select(currentModel.GetTableName(), null, whereCause, whereParams, _groupBy, null, _orderBy, limit);

        Collection<BaseModel> retData = new ArrayList<>();

        Field[] fields = model.getFields();

        for (Map<String, String> item : data) {
            BaseModel m;
            try {
                m = (BaseModel)model.getConstructors()[0].newInstance();

                for (Field f : fields) {
                    if (f.isAnnotationPresent(MAnnotation.class)) {
                        MAnnotation a = f.getAnnotation(MAnnotation.class);

                        String key = f.getName();
                        if (!a.FieldName().equals(""))
                            key = a.FieldName();

                        if (item.containsKey(key)) {
                            setItemValue(item, m, f, key);
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

    private void setItemValue(Map<String, String> item, BaseModel m, Field f, String key) {
        try {
            String value = item.get(key);
            if (f.getType() == String.class) {
                f.set(m, value);
            } else if (f.getType() == Integer.class) {
                f.set(m, Integer.valueOf(value));
            }
        } catch (IllegalAccessException e) {
            //Ignore
        }
    }

    /**
     * Used for create orderBy and groupBy
     */
    private String concatStrings(Collection<String> orderBy, String _orderBy) {
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
        return _orderBy;
    }
}
