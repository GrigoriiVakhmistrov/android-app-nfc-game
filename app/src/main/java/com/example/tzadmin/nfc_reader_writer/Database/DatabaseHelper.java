package com.example.tzadmin.nfc_reader_writer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tzadmin on 10.06.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "dbJumanji", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbUsers ("
                + "id integer primary key autoincrement,"
                + "cFirstName text,"
                + "cLastName text,"
                + "cSurname text,"
                + "cRfcId text,"
                + "cGroupId text,"
                //+ "cBatchId text,"
                + "cRouteId text,"
                + "cIsCap text,"
                + "cIsNew text,"
                + "cIsDeleted text" + ");");

        db.execSQL("create table log_tbUsers ("
                + "id integer primary key autoincrement,"
                + "cFirstName text,"
                + "cLastName text,"
                + "cSurname text,"
                + "cRfcId text,"
                + "cGroupId text,"
                //+ "cBatchId text,"
                + "cRouteId text,"
                + "cIsCap text,"
                + "cIsDeleted text" + ");");

        db.execSQL("create table tbRoutes ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "capacity text,"
                + "isdeleted text" + ");");

        db.execSQL("create table tbGroups ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "totemname text,"
                + "totemimage text,"
                + "color text,"
                + "colorhex text,"
                + "isdeleted text" + ");");

        db.execSQL("create table tbMoneyLogs ("
                + "id integer primary key autoincrement,"
                + "userid text,"
                + "money text,"
                + "type text,"
                + "description text,"
                + "isdeleted text" + ");");

        db.execSQL("create table tbShop ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "description text,"
                + "pic text,"
                + "price text,"
                + "isdeleted text" + ");");


        db.execSQL("create table tbGroupPriority ("
                + "id integer primary key autoincrement,"
                + "groupid text,"
                + "p1 text,"
                + "p2 text,"
                + "p3 text,"
                + "p4 text" + ");");

        db.execSQL("create table tbMorda ("
                + "id integer primary key autoincrement,"
                + "fio text,"
                + "description text,"
                + "pic text" + ");");

        db.execSQL("create table tbEvent ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "price text" + ");");

        db.execSQL("create table tbUserMorda ("
                + "id integer primary key autoincrement,"
                + "userid text,"
                + "mordaid text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
