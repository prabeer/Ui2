package com.media.ui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by prabeer.kochar on 01-08-2017.
 */

public class BluetoothDB extends SQLiteOpenHelper {

    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String BLUETOOTH_STATUS = "status";
    public static final String BLUETOOTH_DATETIME = "date_time";


    public BluetoothDB(Context context) {
        super(context, DBEssentials.DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+DBEssentials.BLUETOOTH_TABLE+" " +
                        "("+CONTACTS_COLUMN_ID+" integer primary key, "+BLUETOOTH_STATUS+" text,"+BLUETOOTH_DATETIME+" DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBEssentials.BLUETOOTH_TABLE);
        onCreate(db);
    }
    public boolean insertBTdata (String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BLUETOOTH_STATUS, status);
        db.insert(DBEssentials.BLUETOOTH_TABLE, null, contentValues);
        return true;
    }

    public Cursor getAllBTRecords() {

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DBEssentials.BLUETOOTH_TABLE, null );

        return res;
    }
    public boolean truncateBT(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+DBEssentials.BLUETOOTH_TABLE,null);
        return true;
    }
}
