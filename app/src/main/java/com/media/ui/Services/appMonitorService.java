package com.media.ui.Services;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.media.ui.Util.logger.logg;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class appMonitorService extends IntentService {

    public appMonitorService() {
        super("appMonitorService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            int x=1;
            int y =0;
            do {
                SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences("monitor", Context.MODE_PRIVATE);
                String  mt =  sharedpreferences.getString("screen", null);
                if (mt.equals("on")) {
                    startCapture();
                } else {
                    this.stopSelf();
                    break;
                }
                y++;
                logg("Count:"+ Integer.toString(y));
            }while (x==1);
logg("Broken");
            this.stopSelf();
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void startCapture(){


        logg("Start Capture");

            ActivityManager am = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

            for (int i = 0; i < runningAppProcessInfo.size(); i++) {

                logg(runningAppProcessInfo.get(i).processName);
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }


}
