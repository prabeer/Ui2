package com.media.ui.DataCollector;

import android.content.Context;
import android.os.Environment;

import com.media.ui.Database.DBEssentials;
import com.media.ui.Database.databaseHandler;
import com.media.ui.Database.packageMonitorCollectorDB;
import com.media.ui.constants;

import java.io.BufferedWriter;
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
        logg("List Size:"+pmc.getAllPackageMonitorStatus().size());
        logg(pmc.getAllPackageMonitorStatus().get(0).getStatus());
        writeData(pmc.getAllPackageMonitorStatus(),context);
        pmc.close();

    }

    private void writeData(List cursor, Context context) {
        if (cursor.size() != 0) {
            Iterator<packageMonitorCollectorDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = DBEssentials.APPMONITOR + String.valueOf(timi) + constants.UNDERSCORE + imi(context) + constants.CSVEXT;
            try {
                File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
                if (!root.exists()) {
                    root.mkdirs();
                }
                File file = new File(root, sFileName);
                file.createNewFile();
                BufferedWriter csvWrite = new BufferedWriter(new FileWriter(file, true));
                logg("cursor_size:" + String.valueOf(cursor.size()));

                do {
                    packageMonitorCollectorDB t = itr.next();
                    logg(String.valueOf(t.getId()) + "," + t.getStatus() + "," + t.getDate());
                    String[] arr = {String.valueOf(t.getId()),t.getPkgname(), t.getStatus(), t.getDate(),t.getUsedTime()};
                    for (int i = 0; i < arr.length; i++) {
                        csvWrite.write(arr[i] + ",");
                    }
                    csvWrite.newLine();
                } while (itr.hasNext());
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
