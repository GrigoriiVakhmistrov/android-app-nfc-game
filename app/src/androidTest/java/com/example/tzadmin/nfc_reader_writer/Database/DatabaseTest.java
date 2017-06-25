package com.example.tzadmin.nfc_reader_writer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    @Test
    public void insertDeadlockTest() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Database database = Database.get();
        database.appContext = appContext;

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 100);
        contentValues.put("userid", 100);
        contentValues.put("mordaid", 100);
        database.insert("tbUserMorda", null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("id", 200);
        contentValues1.put("userid", 200);
        contentValues1.put("mordaid", 200);
        database.insert("tbUserMorda", null, contentValues1);
    }

    @Test
    public void updateDeadlockTest() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Database database = Database.get();
        database.appContext = appContext;

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 100);
        contentValues.put("userid", 100);
        contentValues.put("mordaid", 100);
        database.insert("tbUserMorda", null, contentValues);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("id", 100);
        contentValues1.put("userid", 200);
        contentValues1.put("mordaid", 100);
        database.update("tbUserMorda", contentValues1, "id = ?", new String[]{"100"});

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("id", 100);
        contentValues2.put("userid", 300);
        contentValues2.put("mordaid", 100);
        database.update("tbUserMorda", contentValues2, "id = ?", new String[]{"100"});
    }

    @Test
    public void testWorking() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Database database = Database.get();
        database.appContext = appContext;
        Database.db.execSQL("delete from tbUserMorda where id = 200");

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", 200);
        contentValues.put("userid", 100);
        contentValues.put("mordaid", 100);
        database.insert("tbUserMorda", null, contentValues);

        Collection<Map<String, String>> ret1 = database.select("tbUserMorda", null, null, null, null, null, null, null);
        boolean ok1 = false;
        for(Map<String, String> stringStringMap : ret1) {
            if("200".equals(stringStringMap.get("id")) && "100".equals(stringStringMap.get("userid")) && "100".equals(stringStringMap.get("mordaid"))) {
                ok1 = true;
                break;
            }
        }
        assertTrue(ok1);

        ContentValues contentValues1 = new ContentValues();
        contentValues1.put("id", 200);
        contentValues1.put("userid", 200);
        contentValues1.put("mordaid", 200);
        database.update("tbUserMorda", contentValues1, "id = ?", new String[]{"200"});

        Collection<Map<String, String>> ret2 = database.select("tbUserMorda", null, null, null, null, null, null, null);
        boolean ok2 = false;
        for(Map<String, String> stringStringMap : ret2) {
            if("200".equals(stringStringMap.get("id")) && "200".equals(stringStringMap.get("userid")) && "200".equals(stringStringMap.get("mordaid"))) {
                ok2 = true;
                break;
            }
        }
        assertTrue(ok2);
    }
}