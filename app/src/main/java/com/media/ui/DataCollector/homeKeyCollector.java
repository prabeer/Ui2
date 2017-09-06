package com.media.ui.DataCollector;

import android.content.Context;
import android.os.Environment;

import com.media.ui.Database.DBEssentials;
import com.media.ui.Database.databaseHandler;
import com.media.ui.Database.homekeyDB;
import com.media.ui.constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.Util.utility.imi;

/**
 * Created by prabeer.kochar on 06-09-2017.
 */

public class homeKeyCollector {
    databaseHandler hk;
    public homeKeyCollector(Context context) {
        logg("Bluetooth Collector");
        hk = new databaseHandler(context);
        logg("List Size:"+hk.getAllHOMEKEY().size());
        logg(hk.getAllHOMEKEY().get(0).getStatus());
        writeData(hk.getAllHOMEKEY(),context);
        hk.close();

    }

    private void writeData(List cursor, Context context) {
        if (cursor.size() != 0) {
            Iterator<homekeyDB> itr = cursor.iterator();
            long timi = System.currentTimeMillis();
            String sFileName = DBEssentials.HOME_KEY+ constants.UNDERSCORE + String.valueOf(timi)+constants.UNDERSCORE+imi(context) + constants.CSVEXT;
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
                    homekeyDB t = itr.next();
                    logg(String.valueOf(t.getId()) + "," + t.getStatus() + "," + t.getDate());
                    String[] arr = {String.valueOf(t.getId()), t.getStatus(), t.getDate()};
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
