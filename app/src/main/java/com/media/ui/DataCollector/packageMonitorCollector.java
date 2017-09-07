package com.media.ui.DataCollector;

import android.content.Context;
import android.os.Environment;

import com.media.ui.Database.DBEssentials;
import com.media.ui.Database.databaseHandler;
import com.media.ui.Database.packageMonitorCollectorDB;
import com.media.ui.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.Util.utility.imi;

/**
 * Created by prabeer.kochar on 05-09-2017.
 */

public class packageMonitorCollector {
    databaseHandler pmc;

    public packageMonitorCollector(Context context) {
        logg("Bluetooth Collector");
        pmc = new databaseHandler(context);
      //  logg("List Size:"+pmc.getAllPackageMonitorStatus().size());
      //  logg(pmc.getAllPackageMonitorStatus().get(0).getStatus());
        writeData(pmc.getAllPackageMonitorStatus(),context);
        pmc.close();

    }

    private void writeData(List cursor, Context context) {
        if (cursor.size() != 0) {
            Iterator<packageMonitorCollectorDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = DBEssentials.APPMONITOR +constants.UNDERSCORE+ String.valueOf(timi) + constants.UNDERSCORE + imi(context) + constants.CSVEXT;
            try {
                File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File file = new File(root, sFileName);
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file, true));
                logg("cursor_size:" + String.valueOf(cursor.size()));
                String[] arr1 = {"id","pkg_name", "status", "date","used_time"};
                csvWrite.writeNext(arr1);
                while(itr.hasNext()) {
                    packageMonitorCollectorDB t = itr.next();
//                   / logg(String.valueOf(t.getId()) + "," + t.getStatus() + "," + t.getDate());
                    String[] arr = {String.valueOf(t.getId()),t.getPkgname(), t.getStatus(), t.getDate(),t.getUsedTime()};
                    csvWrite.writeNext(arr);
                }
                csvWrite.flush();
                csvWrite.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            logg("cursor:"+cursor.size());
        }
    }
}
