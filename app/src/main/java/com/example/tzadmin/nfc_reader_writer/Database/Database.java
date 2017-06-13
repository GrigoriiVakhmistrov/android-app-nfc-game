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

    public static void update (String tableName, Object object) throws IllegalAccessException {
        ContentValues cv = new ContentValues();
        Class _class = object.getClass();
        Field[] fields = _class.getDeclaredFields();
        for (Field field : fields) {
            if(field.getType() == String.class) {
                cv.put(field.getName(), field.get(object).toString());
            }
        }
        db.update(tableName, cv,
                "id = ?", new String[] { String.valueOf(cv.get("id")) });
    }

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

    @Nullable
    public static ArrayList<User> selectUsers () {
        Cursor cursor = db.query("tbUsers", null, null,
                null, null, null, null, null);
        ArrayList<User> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                User user = new User(false);
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
                result.add(user);
            } while (cursor.moveToNext());
            return result;
        } else {
            return  null;
        }
    }

    @Nullable
    public static User selectUsersByNfcId (String id) {
        Cursor cursor = db.query("tbUsers", null, "cRfcId = ?",
                new String[] { id }, null, null, null, "1");
        User user = new User(true);
        if(cursor.moveToFirst()) {
            user.cFirstName = cursor.getString(cursor.getColumnIndex("cFirstName"));
            user.cLastName = cursor.getString(cursor.getColumnIndex("cLastName"));
            user.cSurname = cursor.getString(cursor.getColumnIndex("cSurname"));
            return user;
        } else {
            return  null;
        }
    }

    public static void delete (String tableName, int id) {
        db.delete(tableName,
                "id =?",
                new String[] { String.valueOf(id) });
    }
}
