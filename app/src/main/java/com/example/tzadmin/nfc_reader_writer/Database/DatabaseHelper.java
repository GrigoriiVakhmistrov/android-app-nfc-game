package com.example.tzadmin.nfc_reader_writer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tzadmin.nfc_reader_writer.R;

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



        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Фламинго', 'albat_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Антилопы', 'antilop_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Медведи', 'bear_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Бегемоты', 'bigimot_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Крокодилы', 'crocodile_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Еноты', 'enot_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Лягушки', 'jaba_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Жирафы', 'jiraf_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Пантеры', 'leo_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Львы', 'lion_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Обезьяны', 'monkey_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Летучие мыши', 'mouse_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Носороги', 'nosorog_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Попугаи', 'popygai_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Слоны', 'slon_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Кобры', 'snake_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Тигры', 'tiger_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Волки', 'wolf_xxxhdpi');");
        db.execSQL("insert table tbGroups (totemname, totemimage) values ('Зебры', 'zebra_xxxhdpi');");

        db.execSQL("insert table tbRoute (name, capacity) values ('Route1', '15');");
        db.execSQL("insert table tbRoute (name, capacity) values ('Route2', '45');");
        db.execSQL("insert table tbRoute (name, capacity) values ('Route3', '5');");
        db.execSQL("insert table tbRoute (name, capacity) values ('Route4', '14');");
        db.execSQL("insert table tbRoute (name, capacity) values ('Route5', '11');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
