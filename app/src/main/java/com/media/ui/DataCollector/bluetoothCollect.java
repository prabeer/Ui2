package com.media.ui.DataCollector;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;

import com.media.ui.Database.BluetoothDB;
import com.media.ui.Util.FileWriter;
import com.media.ui.constants;
import com.opencsv.CSVWriter;

import java.io.File;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 18-08-2017.
 */

public class bluetoothCollect {
    Cursor BTcurser;
    BluetoothDB btdb;
    public bluetoothCollect(Context context){
        logg("Bluetooth Collector");
        btdb =   new BluetoothDB(context);
        writeData(btdb.getAllBTRecords());
        btdb.close();
    }
    private void writeData(Cursor cursor){
        long timi = System.currentTimeMillis();
        String sFileName = constants.BTFile + String.valueOf(timi);
        try {
            File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            csvWrite.writeNext(cursor.getColumnNames());
            while (cursor.moveToNext()) {
                //Which column you want to exprort
                logg(cursor.getString(0)+","+cursor.getString(1)+","+cursor.getString(2));
                String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
