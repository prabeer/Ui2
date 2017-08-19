package com.media.ui.Services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.media.ui.Util.logger.logg;

public class appMonitorService extends Service {
    public appMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    public void onCreate() {
        logg("Create Monitor Service");
        super.onCreate();
    }
int x = 1;
    public int onStartCommand(Intent intent, int flags, int startId) {
        logg("Start Service");
        startCapture();
        return START_STICKY;
    }
    private void startCapture(){
        logg("Start Capture");
        do {
            ActivityManager am = (ActivityManager) getSystemService(this.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo = am.getRunningAppProcesses();

            for (int i = 0; i < runningAppProcessInfo.size(); i++) {
                logg(runningAppProcessInfo.get(i).processName);
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (x==1);
    }
}
