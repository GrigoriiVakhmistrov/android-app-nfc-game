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

    public static void insert (String tableName, ArrayList<Object> objects) throws IllegalAccessException {
        ContentValues cv = new ContentValues();
        for (Object object : objects) {
            Class _class = object.getClass();
            Field[] fields = _class.getFields();
            for (Field field : fields) {
                if(field.getType() == String.class) {
                    cv.put(field.getName(), field.get(object).toString());
                }
            }
            db.insert(tableName, null, cv);
        }
    }

    public static void update (String tableName, Object object) throws IllegalAccessException {
        ContentValues cv = new ContentValues();
        Class _class = object.getClass();
        Field[] fields = _class.getDeclaredFields();
        for (Field field : fields) {
            cv.put(field.getName(), field.get(object).toString());
        }
        db.insert(tableName,
                "id =" + cv.get("id"), cv);
    }

   /* @Nullable
    public static ArrayList<User> selectUsers() {
        Cursor cursor = db.query("user", null, null,
                null, null, null, null, null);
        ArrayList<User> deliveries = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.id = cursor.getInt(0);
                user.idUser = cursor.getInt(1);
                user.DeliveryDate = cursor.getString(2);
                user.day = cursor.getInt(3);
                user.month = cursor.getInt(4);
                user.year = cursor.getInt(5);
                user.DocID = cursor.getString(6);
                user.SerialNumber = cursor.getString(7);
                user.Client = cursor.getString(8);
                user.Address = cursor.getString(9);
                deliveries.add(delivery);
            } while (cursor.moveToNext());
            return deliveries;
        }
        else
            return null;
    }*/


    @Nullable
    public static ArrayList<Object> select (String tableName) {
        Cursor cursor = db.query(tableName, null,
                null,
                null, null, null, null, null);
        ArrayList<Object> result = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                String[] names = cursor.getColumnNames();
                Object object = new Object();
                for (int i = 0; i < names.length; i++) {
                    int type = cursor.getType(i);
                    switch (type) {
                        case Cursor.FIELD_TYPE_INTEGER:
                            object = cursor.getInt(cursor.getColumnIndex(names[i]));
                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            object = cursor.getString(cursor.getColumnIndex(names[i]));
                            break;
                        case Cursor.FIELD_TYPE_NULL:
                            object = null;
                            break;
                    }
                }
                result.add(object);
            } while (cursor.moveToNext());
            return result;
        } else {
            return  null;
        }
    }

    public static void delete (String tableName, int id) {
        db.delete(tableName,
                "id =?",
                new String[] { String.valueOf(id) });
    }


    /*Todo : delete Blinds*/
    public static void Blinds_insert_user(User user) {
        /*User user = new User();
        user.firstname = "Антон";
        user.lastname = "Иванов";
        user.patronymic = "Эдуардович";
        user.batchild = "1";
        user.groupid = "2";
        user.iscap = "3";
        user.routeid = "4";
        user.rfcid = "12-12-32-32";
        user.isdeleted = "0";*/

        ContentValues cv = new ContentValues();
        cv.put("firstname", user.firstname);
        cv.put("lastname", user.lastname);
        cv.put("patronymic", user.patronymic);
        cv.put("rfcid", user.rfcid);
        cv.put("groupid", user.groupid);
        cv.put("batchild", user.batchild);
        cv.put("routeid", user.routeid);
        cv.put("iscap", user.iscap);
        cv.put("isdeleted", user.isdeleted);
        db.insert("user", null, cv);
    }
}
