package com.media.ui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prabeer.kochar on 01-08-2017.
 */

public class lowBatteryDB extends SQLiteOpenHelper {

    public static final String LOWBATTERY_COLUMN_ID = "id";
    public static final String LOWBATTERY_STATUS = "status";
    public static final String LOWBATTERY_DATETIME = "date_time";
    SQLiteDatabase db;
    public lowBatteryDB(Context context) {
        super(context, DBEssentials.DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + DBEssentials.LOWBATTERY + " " +
                        "(" + LOWBATTERY_COLUMN_ID + " integer primary key, " + LOWBATTERY_STATUS + " text," + LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.LOWBATTERY);
        onCreate(db);
    }

    public boolean insertLBdata(String status) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOWBATTERY_STATUS, status);
        db.insert(DBEssentials.LOWBATTERY, null, contentValues);
        return true;
    }

    public Cursor getAllLowRecords() {
        db = this.getReadableDatabase();
        Cursor res = null;
        try {
            res = db.rawQuery("select * from " + DBEssentials.LOWBATTERY, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void closedb(){
        db.close();
    }
}