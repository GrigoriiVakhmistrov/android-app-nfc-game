package com.example.tzadmin.nfc_reader_writer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tzadmin on 10.06.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "dbName", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*db.execSQL("create table tableName ("
                + "id integer primary key autoincrement,"
                + "Name text,"
                + "Name text,"
                + "Name integer" + ");");

        db.execSQL("create table tableName ("
                + "id integer primary key autoincrement,"
                + "Name integer,"
                + "Name text,"
                + "FOREIGN KEY (columName) REFERENCES tableName(columName)" + ");");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
