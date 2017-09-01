package com.media.ui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.media.ui.Util.logger.logg;


/**
 * Created by prabeer.kochar on 24-08-2017.
 */

public class databaseHandler extends SQLiteOpenHelper {

    public static final String COLUMN_ID = "id";
    public static final String BLUETOOTH_STATUS = "status";
    public static final String BLUETOOTH_DATETIME = "date_time";
    public static final String LOWBATTERY_STATUS = "status";
    public static final String LOWBATTERY_DATETIME = "date_time";
    public static final String PACKAGE_NAME = "package_name";
    public static final String PACKAGE_STATUS = "package_status";

    public databaseHandler(Context context) {
        super(context, DBEssentials.DB, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table "+DBEssentials.BLUETOOTH_TABLE+" " +
                        "("+COLUMN_ID+" integer primary key, "+BLUETOOTH_STATUS+" text,"+BLUETOOTH_DATETIME+" DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.LOWBATTERY + " " +
                        "(" + COLUMN_ID + " integer primary key, " + LOWBATTERY_STATUS + " text," + LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPINSTALL + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS +" text,"+ LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ DBEssentials.BLUETOOTH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.LOWBATTERY);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPINSTALL);
        onCreate(db);

    }
    public boolean insertPackageStatus(String pkg, String status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PACKAGE_NAME, pkg);
        contentValues.put(PACKAGE_STATUS, status);
        db.insert(DBEssentials.APPINSTALL, null, contentValues);
        db.close();
        return true;
    }

    public List<packageInstallCollectorDB> getAllPackageStatus() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<packageInstallCollectorDB> packageInstallCollectorDBList = new ArrayList<packageInstallCollectorDB>();
        //hp = new HashMap();

        Cursor res =  db.rawQuery( "select * from "+DBEssentials.APPINSTALL, null );
        if (res.moveToFirst()) {
            do {
                packageInstallCollectorDB PIDB = new packageInstallCollectorDB();
                PIDB.setStatus(res.getString(1));
                PIDB.setId(Integer.parseInt(res.getString(0)));
                PIDB.setDate(res.getString(2));
                // Adding contact to list
                packageInstallCollectorDBList.add(PIDB);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return packageInstallCollectorDBList;
    }

    public boolean insertLBdata(String status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LOWBATTERY_STATUS, status);
        db.insert(DBEssentials.LOWBATTERY, null, contentValues);
        db.close();
        return true;
    }

    public List<lowBatteryDB> getAllLowRecords() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<lowBatteryDB> LowstatusList = new ArrayList<lowBatteryDB>();
         //hp = new HashMap();

        Cursor res =  db.rawQuery( "select * from "+DBEssentials.LOWBATTERY, null );
        if (res.moveToFirst()) {
            do {
                lowBatteryDB lowdb = new lowBatteryDB();
                lowdb.setStatus(res.getString(1));
                lowdb.setId(Integer.parseInt(res.getString(0)));
                lowdb.setDate(res.getString(2));
                // Adding contact to list
                LowstatusList.add(lowdb);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return LowstatusList;
    }

    public boolean insertBTdata (String status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BLUETOOTH_STATUS, status);
        db.insert(DBEssentials.BLUETOOTH_TABLE, null, contentValues);
        db.close();
        return true;
    }

    public List<bluetoothDB> getAllBTRecords() {

        List<bluetoothDB> BTstatusList = new ArrayList<bluetoothDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+DBEssentials.BLUETOOTH_TABLE, null );
        if (res.moveToFirst()) {
            do {
                bluetoothDB btdb = new bluetoothDB();
                logg("InsertData:"+res.getString(0)+","+res.getString(1)+","+res.getString(2));
                btdb.setStatus(res.getString(1));
                btdb.setId(Integer.parseInt(res.getString(0)));
                btdb.setDate(res.getString(2));
                // Adding contact to list
                BTstatusList.add(btdb);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return BTstatusList;
    }
    public boolean truncateBT(){
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("delete from "+DBEssentials.BLUETOOTH_TABLE,null);
        return true;
    }

    public void closedb(){
                this.close();
    }
}
