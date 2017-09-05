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
    public static final String STATUS = "status";
    public static final String BLUETOOTH_STATUS = "status";
    public static final String BLUETOOTH_DATETIME = "date_time";
    public static final String LOWBATTERY_STATUS = "status";
    public static final String LOWBATTERY_DATETIME = "date_time";
    public static final String PACKAGE_NAME = "package_name";
    public static final String PACKAGE_STATUS = "package_status";
    public static final String PACKAGE_MONITOR_LASTUSED = "package_last_used";
    public static final String PHONE_LOCK_STATE = "Phone_Lock";

    public databaseHandler(Context context) {
        super(context, DBEssentials.DB, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + DBEssentials.BLUETOOTH_TABLE + " " +
                        "(" + COLUMN_ID + " integer primary key, " + BLUETOOTH_STATUS + " text," + BLUETOOTH_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.LOWBATTERY + " " +
                        "(" + COLUMN_ID + " integer primary key, " + LOWBATTERY_STATUS + " text," + LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPINSTALL + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPMONITOR + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + PACKAGE_MONITOR_LASTUSED + " text," + LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.PHONELOCK + " " +
                        "(" + COLUMN_ID + " integer primary key, " + BLUETOOTH_STATUS + " text," + BLUETOOTH_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.BLUETOOTH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.LOWBATTERY);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPINSTALL);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPMONITOR);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.PHONELOCK);
        onCreate(db);

    }
    public boolean insertLockMonitor(String status) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, status);
        try {
            if(checkTable(DBEssentials.PHONELOCK)) {
               // logg("APPMONITOR Insert");
                db.insert(DBEssentials.PHONELOCK, null, contentValues);
            }else{
               // logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.PHONELOCK + " " +
                                "(" + COLUMN_ID + " integer primary key, " + BLUETOOTH_STATUS + " text," + BLUETOOTH_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.PHONELOCK, null, contentValues);
            }
        }catch (Exception e){
            e.getMessage();
        }finally {
            db.close();
        }

        db.close();
        return true;
    }

    public List<lockUnlockDB> getAllLockMonitor() {

        List<lockUnlockDB> lockUnlockDBList = new ArrayList<lockUnlockDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + DBEssentials.PHONELOCK, null);
        if (res.moveToFirst()) {
            do {
                lockUnlockDB btdb = new lockUnlockDB();
                logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                btdb.setStatus(res.getString(1));
                btdb.setId(Integer.parseInt(res.getString(0)));
                btdb.setDate(res.getString(2));
                // Adding contact to list
                lockUnlockDBList.add(btdb);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return lockUnlockDBList;
    }
    public boolean insertPackageMonitor(String pkg, String status, String last_used_time) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PACKAGE_NAME, pkg);
        contentValues.put(PACKAGE_STATUS, status);
        contentValues.put(PACKAGE_MONITOR_LASTUSED, last_used_time);
        try {
            if(checkTable(DBEssentials.APPMONITOR)) {
                logg("APPMONITOR Insert");
                db.insert(DBEssentials.APPMONITOR, null, contentValues);
            }else{
                logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.APPMONITOR + " " +
                                "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + PACKAGE_MONITOR_LASTUSED + " text," + LOWBATTERY_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.APPMONITOR, null, contentValues);
            }
        }catch (Exception e){
            e.getMessage();
        }finally {
            db.close();
        }

        return true;
    }

    public List<packageMonitorCollectorDB> getAllPackageMonitorStatus() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<packageMonitorCollectorDB> packageMonitorCollectorDBList = new ArrayList<packageMonitorCollectorDB>();
        //hp = new HashMap();

        Cursor res = db.rawQuery("select * from " + DBEssentials.APPMONITOR, null);
        if (res.moveToFirst()) {
            do {
                packageMonitorCollectorDB PMDB = new packageMonitorCollectorDB();
                PMDB.setStatus(res.getString(1));
                PMDB.setId(Integer.parseInt(res.getString(0)));
                PMDB.setDate(res.getString(2));
                PMDB.setPkgname(res.getString(3));
                PMDB.setUsedTime(res.getString(4));
                // Adding contact to list
                packageMonitorCollectorDBList.add(PMDB);
            } while (res.moveToNext());
        }
        res.close();
        db.close();
        return packageMonitorCollectorDBList;
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

        Cursor res = db.rawQuery("select * from " + DBEssentials.APPINSTALL, null);
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

        Cursor res = db.rawQuery("select * from " + DBEssentials.LOWBATTERY, null);
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

    public boolean insertBTdata(String status) {
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
        Cursor res = db.rawQuery("select * from " + DBEssentials.BLUETOOTH_TABLE, null);
        if (res.moveToFirst()) {
            do {
                bluetoothDB btdb = new bluetoothDB();
                logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
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

    public boolean truncateBT() {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        db.execSQL("delete from " + DBEssentials.BLUETOOTH_TABLE, null);
        return true;
    }

    public void closedb() {
        this.close();
    }

    private boolean checkTable(String table_name){
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + table_name + "'", null);
        if(cursor.getCount() > 0){
            cursor.close();
            db.close();
            return true;
        }else{
            cursor.close();
            db.close();
            return false;
        }

    }
}
