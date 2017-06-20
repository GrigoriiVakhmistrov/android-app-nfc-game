package com.example.tzadmin.nfc_reader_writer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import com.example.tzadmin.nfc_reader_writer.Models.User;
import com.example.tzadmin.nfc_reader_writer.SharedApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * Created by forz on 11.06.17.
 */

public class Database {
    static SQLiteDatabase db; //Private instance of SQLite database
    private static Database instance; //private variable of singlton

    private Context appContext;
    private Semaphore threadMutex = new Semaphore(1);

    //Entry point to singlton with ThreadSafe manner
    //USE ONLY THIS METHOD TO ACCESS DATABASE
    public static Database get() {
        if(instance == null) instance = getSync();
        return instance;
    }

    private static synchronized Database getSync() {
        if(instance == null) instance = new Database();
        return instance;
    }

    private Database(){
        // here you can directly access the Application context calling
        appContext = SharedApplication.get();
        DatabaseHelper dbHelper = new DatabaseHelper(appContext);
        db = dbHelper.getReadableDatabase();
    }

    public long insert (String table, String nullColumnHack, ContentValues values) {
        try {
            threadMutex.acquire();
            return db.insert(table, nullColumnHack, values);
        }catch (Exception ex) {
        }finally {
            threadMutex.release();
            return -1;
        }
    }

    public int update (String table, ContentValues values, String whereClause, String[] whereArgs) {
        try {
            threadMutex.acquire();
            return db.update(table, values, whereClause, whereArgs);
        }catch (Exception ex) {
        }finally {
            threadMutex.release();
            return -1;
        }
    }

    public Collection<Map<String, String>> select (String table, String[] columns, String selection,
                                                   String[] selectionArgs, String groupBy, String having,
                                                   String orderBy, String limit) {
        Collection<Map<String, String>> retData = new ArrayList<>();

        try {
            threadMutex.acquire();

            Cursor c = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy, limit);

            if (c.moveToFirst()) {
                do {
                    Map<String, String> data = new HashMap<>();
                    for (int i = 0; i < c.getColumnCount(); i++) {
                        data.put(c.getColumnName(i), c.getString(i));
                    }

                    retData.add(data);
                } while (c.moveToNext());
            }

        }catch (Exception ex) {
        }finally {
            threadMutex.release();
        }

        return retData;
    }

    public void insert (String tableName, ArrayList<Object> objects) {
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

    public void insert (String tableName, Object object) {
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

    public void update (String tableName, Object object) {
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
    }

    public boolean isNfcIdAlreadyExist (String RfcId) {
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
    //public static<T> void select (String tableName, T model, ArrayList listModel) {
    public static<T> ArrayList<T> select (String tableName) {
        ArrayList<T> listModel = new ArrayList<>();
        T model = new T();
        Cursor cursor = db.query(tableName,
                null, null, null, null, null, null, null);
        //ArrayList listModel = new ArrayList<>();
        Field[] fieldModel = model.getClass().getFields();
        if (cursor.moveToFirst()) {
            do {
                //Object object = new Object();
                model = null;
                String[] names = cursor.getColumnNames();
                for (Field tempField : fieldModel) {
                    for (int i = 0; i < names.length; i++) {
                        if (names[i].equals(tempField.getName())) {
                            int type = cursor.getType(i);
                            try {
                                switch (type) {
                                    case Cursor.FIELD_TYPE_INTEGER:
                                        tempField.setInt(model, cursor.getInt(cursor.getColumnIndex(names[i])));
                                        break;
                                    case Cursor.FIELD_TYPE_STRING:
                                        String tempStr = cursor.getString(cursor.getColumnIndex(names[i]));
                                        tempField.set(model, tempStr);
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
                }
                listModel.add(model);
            } while (cursor.moveToNext());
            return listModel;
        } else {
            return  null;
        }
    }

        @Nullable
        public User selectUserByRfcId (String RfcId) {
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
    public ArrayList<User> selectUsers () {
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
                user.cGroupId = cursor.getString(4);
                user.cBatchId = cursor.getString(5);
                user.cRfcId = cursor.getString(6);
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


    public void delete (String tableName, int id) {
        db.delete(tableName,
                "id =?",
                new String[] { String.valueOf(id) });
    }
}
