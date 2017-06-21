package com.example.tzadmin.nfc_reader_writer.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by forz on 11.06.17.
 */

public class Database {
    private static SQLiteDatabase db;

    public static void SetUp (SQLiteDatabase dbHelper) {
        db = dbHelper;
    }

    public static void insert (String tableName, ArrayList<Object> objects) {
        ContentValues cv = new ContentValues();
        for (Object object : objects) {
            Class _class = object.getClass();
            Field[] fields = _class.getFields();
            for (Field field : fields) {
                if(field.getType() == String.class) {
                    try {
                        cv.put(field.getName(), field.get(object).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            db.insert(tableName, null, cv);
        }
    }

    public static void insert (String tableName, Object object) {
        ContentValues cv = new ContentValues();
        Class _class = object.getClass();
        Field[] fields = _class.getFields();
        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    cv.put(field.getName(), field.get(object).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        db.insert(tableName, null, cv);
    }

    public static void updateBracer (int id, String RfcId) {
        ContentValues cv = new ContentValues();
        cv.put("cRfcId", RfcId);
        db.update("tbUsers", cv,
                "id = ?", new String[] { String.valueOf(id) });
    }

    /*public static void update (String tableName, Object object) {
        ContentValues cv = new ContentValues();
        Class _class = object.getClass();
        Field[] fields = _class.getDeclaredFields();
        for (Field field : fields) {
            if(field.getType() == String.class) {
                try {
                    cv.put(field.getName(), field.get(object).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        db.update(tableName, cv,
                "id = ?", new String[] { String.valueOf(cv.get("id")) });
    }*/

    public static boolean isNfcIdAlreadyExist (String RfcId) {
        Cursor cursor = db.query("tbUsers",
                null, "cRfcId = ?",
                new String[] { RfcId },
                null, null, null);

        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

/*
    @Nullable
    public static<T> ArrayList select(String tableName, T model) {
        Cursor cursor = db.query(tableName,
                null, null, null, null, null, null, null);
        Field[] fieldModel = model.getClass().getFields();
        ArrayList listModel = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                model = null;
                String[] names = cursor.getColumnNames();
                for (Field tempField : fieldModel) {
                    for (int i = 0; i < names.length; i++) {
                        if (names[i].equals(tempField.getName()))
                            try {
                                int type = cursor.getType(i);
                                switch (type) {
                                    case Cursor.FIELD_TYPE_INTEGER:
                                        tempField.setInt(model, cursor.getInt(cursor.getColumnIndex(names[i])));
                                        break;
                                    case Cursor.FIELD_TYPE_STRING:
                                        tempField.set(model, cursor.getString(cursor.getColumnIndex(names[i])));
                                        break;
                                    case Cursor.FIELD_TYPE_NULL:
                                        tempField.set(model, null);
                                        break;
                                }
                        } catch (IllegalAccessException e) {
                                e.printStackTrace();
                        }
                    }
                }
                listModel.add(model);
            } while (cursor.moveToNext());
            return listModel;
        } else {
            return null;
        }
    }*/


    @Nullable
    public static User selectUserByRfcId (String RfcId) {
        Cursor cursor = db.query("tbUsers", null, "cRfcId = ?",
                new String[]{RfcId}, null, null, null, "1");
        User user = new User();
        if (cursor.moveToFirst()) {
            user.id = cursor.getInt(0);
            user.cFirstName = cursor.getString(1);
            user.cLastName = cursor.getString(2);
            user.cSurname = cursor.getString(3);
            user.cGroupId = cursor.getString(4);
            user.cBatchId = cursor.getString(5);
            user.cRfcId = cursor.getString(6);
            user.cRouteId = cursor.getString(7);
            user.cIsCap = cursor.getString(8);
            user.cIsDeleted = cursor.getString(9);
            return user;
        } else {
            return null;
        }
    }


    @Nullable
    public static ArrayList<User> selectUsers () {
        Cursor cursor = db.query("tbUsers", null, null,
                null, null, null, null, null);
        ArrayList<User> users = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.id = cursor.getInt(0);
                user.cFirstName = cursor.getString(1);
                user.cLastName = cursor.getString(2);
                user.cSurname = cursor.getString(3);
                user.cRfcId = cursor.getString(4);
                user.cGroupId = cursor.getString(5);
                user.cBatchId = cursor.getString(6);
                user.cRouteId = cursor.getString(7);
                user.cIsCap = cursor.getString(8);
                user.cIsDeleted = cursor.getString(9);
                users.add(user);
            } while (cursor.moveToNext());
            return users;
        } else {
            return null;
        }
    }


    public static void delete (String tableName, int id) {
        db.delete(tableName,
                "id =?",
                new String[] { String.valueOf(id) });
    }
}
