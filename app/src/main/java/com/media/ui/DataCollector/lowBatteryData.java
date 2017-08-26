package com.media.ui.DataCollector;

import android.content.Context;
import android.os.Environment;

import com.media.ui.Database.databaseHandler;
import com.media.ui.Database.lowBatteryDB;
import com.media.ui.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 22-08-2017.
 */

public class lowBatteryData {


    databaseHandler lwdb;
    public lowBatteryData(Context context){
        logg("LowBattery Collector");
        lwdb =   new databaseHandler(context);
        writeData(lwdb.getAllLowRecords());
        lwdb.close();
    }
    private void writeData(List cursor) {
        if (cursor.size() != 0) {

            Iterator<lowBatteryDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = constants.LowBattFile + constants.UNDERSCORE + String.valueOf(timi) + constants.CSVEXT;
            try {
                File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File file = new File(root, sFileName);
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));
                 while(itr.hasNext()) {
                     lowBatteryDB t = itr.next();
                    logg(String.valueOf(t.getId())+","+ t.getStatus()+","+ t.getDate());
                    String[] arr = {String.valueOf(t.getId()), t.getStatus(),t.getDate()};
                    csvWrite.writeNext(arr);
                }
                csvWrite.flush();
                csvWrite.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
