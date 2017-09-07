package com.media.ui.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
    public static final String DATETIME = "date_time";
    public static final String PACKAGE_NAME = "package_name";
    public static final String PACKAGE_STATUS = "package_status";
    public static final String PACKAGE_MONITOR_LASTUSED = "package_last_used";
    public static final String NETWORKTYPE = "network_type";

    public databaseHandler(Context context) {
        super(context, DBEssentials.DB, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + DBEssentials.BLUETOOTH_TABLE + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.LOWBATTERY + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPINSTALL + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.APPMONITOR + " " +
                        "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + PACKAGE_MONITOR_LASTUSED + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.PHONELOCK + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.EAR_JACK + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.HOME_KEY + " " +
                        "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
        db.execSQL(
                "create table " + DBEssentials.NETWORK_CHANGE_TABLE + " " +
                        "(" + COLUMN_ID + " integer primary key, " + NETWORKTYPE + " text," + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.BLUETOOTH_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.LOWBATTERY);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPINSTALL);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.APPMONITOR);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.PHONELOCK);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.EAR_JACK);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.HOME_KEY);
        db.execSQL("DROP TABLE IF EXISTS " + DBEssentials.NETWORK_CHANGE_TABLE);
        onCreate(db);
    }

    /*
************************** HOME KEY DATA ******************
 */
    public boolean insertHOMEKEY(String status) {
        if (checkTable(DBEssentials.HOME_KEY)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Insert");
                db.insert(DBEssentials.HOME_KEY, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.HOME_KEY + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.HOME_KEY, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<homekeyDB> getAllHOMEKEY() {

        List<homekeyDB> homekeyDBList = new ArrayList<homekeyDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.HOME_KEY, null);
            if (res.moveToFirst()) {
                do {
                    homekeyDB btdb = new homekeyDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    homekeyDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return homekeyDBList;
    }

    public boolean deleteRecordsHOMEKEY(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.rawQuery("delete from " + DBEssentials.HOME_KEY + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return true;
    }

//---------------------------------//----------------------------------

    /*
    ************************** EAR JACK DATA ******************
     */
    public boolean insertEARJACK(String status) {
        if (checkTable(DBEssentials.EAR_JACK)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Insert");
                db.insert(DBEssentials.EAR_JACK, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                db.execSQL(
                        "create table " + DBEssentials.EAR_JACK + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Create Table");

                db.insert(DBEssentials.EAR_JACK, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<earjackDB> getAllEARJACK() {

        List<earjackDB> earjackDBList = new ArrayList<earjackDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.EAR_JACK, null);
            if (res.moveToFirst()) {
                do {
                    earjackDB btdb = new earjackDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    earjackDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return earjackDBList;
    }

    public boolean deleteRecordsEARJACK(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.rawQuery("delete from " + DBEssentials.EAR_JACK + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return true;
    }

//---------------------------------//----------------------------------

    /*
************************** LOCK MONITOR DATA ******************
 */
    public boolean insertLockMonitor(String status) {


        if (checkTable(DBEssentials.PHONELOCK)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.PHONELOCK, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                // logg("APPMONITOR Create Table");
                db.execSQL(
                        "create table " + DBEssentials.PHONELOCK + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.PHONELOCK, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }
        return true;
    }

    public List<lockUnlockDB> getAllLockMonitor() {

        List<lockUnlockDB> lockUnlockDBList = new ArrayList<lockUnlockDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.PHONELOCK, null);
            if (res.moveToFirst()) {
                do {
                    lockUnlockDB btdb = new lockUnlockDB();
                    // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    lockUnlockDBList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return lockUnlockDBList;
    }

    public boolean deleteRecordsLockMonitor(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.rawQuery("delete from " + DBEssentials.PHONELOCK + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    /*
    ***************************Package Monitor *********************
     */
    public boolean insertPackageMonitor(String pkg, String status, String last_used_time) {
        if (checkTable(DBEssentials.APPMONITOR)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                contentValues.put(PACKAGE_MONITOR_LASTUSED, last_used_time);

                if (db != null) {
                    long chk = db.insert(DBEssentials.APPMONITOR, null, contentValues);
                    if (chk != 0) {
                        logg("insert success:" + Long.toString(chk));
                    } else {
                        logg("insert fail:" + Long.toString(chk));
                    }
                } else {
                    logg("db not accessible");
                }
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            logg("APPMONITOR Create Table");
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                contentValues.put(PACKAGE_MONITOR_LASTUSED, last_used_time);
                db.execSQL(
                        "create table " + DBEssentials.APPMONITOR + " " +
                                "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + PACKAGE_MONITOR_LASTUSED + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                db.insert(DBEssentials.APPMONITOR, null, contentValues);
            } catch (SQLiteException e) {

                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        }
        return true;
    }

    public List<packageMonitorCollectorDB> getAllPackageMonitorStatus() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();

        List<packageMonitorCollectorDB> packageMonitorCollectorDBList = new ArrayList<packageMonitorCollectorDB>();
        //hp = new HashMap();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.APPMONITOR, null);
            if (res.moveToFirst()) {
                do {
                    packageMonitorCollectorDB PMDB = new packageMonitorCollectorDB();
                    PMDB.setStatus(res.getString(2));
                    PMDB.setId(Integer.parseInt(res.getString(0)));
                    PMDB.setDate(res.getString(4));
                    PMDB.setPkgname(res.getString(1));
                    PMDB.setUsedTime(res.getString(3));
                    // Adding contact to list
                    packageMonitorCollectorDBList.add(PMDB);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return packageMonitorCollectorDBList;
    }

    public boolean deleteRecordspackageMonitor(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            logg("delete from " + DBEssentials.APPMONITOR + " where " + DATETIME + "< DATETIME('now', '-"+hr+" hours')");
            db.rawQuery("delete from " + DBEssentials.APPMONITOR + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }
    ////////////////////////////////////////////////////////////////////////////////

    public boolean insertPackageStatus(String pkg, String status) {
        if (checkTable(DBEssentials.APPINSTALL)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                db.insert(DBEssentials.APPINSTALL, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {

                ContentValues contentValues = new ContentValues();
                contentValues.put(PACKAGE_NAME, pkg);
                contentValues.put(PACKAGE_STATUS, status);
                db.insert(DBEssentials.APPINSTALL, null, contentValues);
                db.execSQL(
                        "create table " + DBEssentials.APPINSTALL + " " +
                                "(" + COLUMN_ID + " integer primary key, " + PACKAGE_NAME + " text," + PACKAGE_STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
            } catch (SQLiteException e) {
                e.printStackTrace();
            } finally {
                db.close();
            }
        }

        return true;
    }

    public List<packageInstallCollectorDB> getAllPackageStatus() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<packageInstallCollectorDB> packageInstallCollectorDBList = new ArrayList<packageInstallCollectorDB>();
        //hp = new HashMap();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.APPINSTALL, null);
            if (res.moveToFirst()) {
                do {
                    packageInstallCollectorDB PIDB = new packageInstallCollectorDB();
                    PIDB.setStatus(res.getString(2));
                    PIDB.setPkgname(res.getString(1));
                    PIDB.setId(Integer.parseInt(res.getString(0)));
                    PIDB.setDate(res.getString(3));
                    // Adding contact to list
                    packageInstallCollectorDBList.add(PIDB);
                } while (res.moveToNext());
                res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return packageInstallCollectorDBList;
    }

    public boolean deleteRecordpackageInstall(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.rawQuery("delete from " + DBEssentials.APPINSTALL + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    ///////////////////////////////////////////////////////////////////////
    /*
    ******************************** LOW BATTERY STATUS *************************
     */
    public boolean insertLBdata(String status) {
        if (checkTable(DBEssentials.LOWBATTERY)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.LOWBATTERY, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                db.execSQL(
                        "create table " + DBEssentials.LOWBATTERY + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.LOWBATTERY, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        }
        return true;
    }


    public List<lowBatteryDB> getAllLowRecords() {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        List<lowBatteryDB> LowstatusList = new ArrayList<lowBatteryDB>();
        //hp = new HashMap();
        try {
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
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return LowstatusList;
    }

    public boolean deleteRecordLowBattery(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.rawQuery("delete from " + DBEssentials.LOWBATTERY + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    /////////////////////////////////////////////////////////////////////////////////////////

   /*
    ******************************** BLUETOOTH DATA **************************************
     */

    public boolean insertBTdata(String status) {
        if (checkTable(DBEssentials.BLUETOOTH_TABLE)) {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.BLUETOOTH_TABLE, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }

        } else {
            SQLiteDatabase db;
            db = this.getWritableDatabase();
            try {
                db.execSQL(
                        "create table " + DBEssentials.BLUETOOTH_TABLE + " " +
                                "(" + COLUMN_ID + " integer primary key, " + STATUS + " text," + DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP)"
                );
                ContentValues contentValues = new ContentValues();
                contentValues.put(STATUS, status);
                db.insert(DBEssentials.BLUETOOTH_TABLE, null, contentValues);
            } catch (SQLiteException e) {
                e.printStackTrace();
                return false;
            } finally {
                db.close();
            }
        }
        return true;
    }

    public List<bluetoothDB> getAllBTRecords() {

        List<bluetoothDB> BTstatusList = new ArrayList<bluetoothDB>();
        SQLiteDatabase db;
        //hp = new HashMap();
        db = this.getReadableDatabase();
        try {
            Cursor res = db.rawQuery("select * from " + DBEssentials.BLUETOOTH_TABLE, null);
            if (res.moveToFirst()) {
                do {
                    bluetoothDB btdb = new bluetoothDB();
                   // logg("InsertData:" + res.getString(0) + "," + res.getString(1) + "," + res.getString(2));
                    btdb.setStatus(res.getString(1));
                    btdb.setId(Integer.parseInt(res.getString(0)));
                    btdb.setDate(res.getString(2));
                    // Adding contact to list
                    BTstatusList.add(btdb);
                } while (res.moveToNext());
            }
            res.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return BTstatusList;
    }


    public boolean deleteRecordBTRecords(String hr) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            db.rawQuery("delete from " + DBEssentials.BLUETOOTH_TABLE + " where " + DATETIME + " < DATETIME('now', '-"+hr+" hours')", null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
        return true;
    }

    public boolean truncateAllTables() {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        try {
            logg("data delete");
            db.execSQL("delete from " + DBEssentials.BLUETOOTH_TABLE, null);
            db.execSQL("delete from " + DBEssentials.APPINSTALL, null);
            db.execSQL("delete from " + DBEssentials.LOWBATTERY, null);
            db.execSQL("delete from " + DBEssentials.APPMONITOR, null);
            db.execSQL("delete from " + DBEssentials.PHONELOCK, null);
            db.execSQL("delete from " + DBEssentials.EAR_JACK, null);
            db.execSQL("delete from " + DBEssentials.NETWORK_CHANGE_TABLE, null);
            db.execSQL("delete from " + DBEssentials.HOME_KEY, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }

        return true;
    }

    public void closedb() {
        this.close();
    }

    private boolean checkTable(String table_name) {
        SQLiteDatabase db;
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + table_name + "'", null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }

    }
}
