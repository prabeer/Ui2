package com.media.ui.Services;
/*
* Screen Lock  receiver register
* Headset Plug receiver register
* Home screen reciever register
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.media.ui.Database.databaseHandler;
import com.media.ui.broadcastReceivers.HomeWatcher;
import com.media.ui.broadcastReceivers.ScreenUnlockReceiver;

import static com.media.ui.Util.logger.logg;

public class registerBroadcastLock extends Service {
    IBinder mBinder = null;
    BroadcastReceiver mReceiver;
    public registerBroadcastLock() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }
    public void onCreate() {
        logg("Create Monitor Service");
        super.onCreate();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
         mReceiver = new ScreenUnlockReceiver();
        registerReceiver(mReceiver, filter);
     //   final databaseHandler d = new databaseHandler(this);
        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                logg("homekey");
                databaseHandler d = new databaseHandler(getApplicationContext());
               d.insertHOMEKEY("HOME");
                d.close();
            }
            @Override
            public void onHomeLongPressed() {
                logg("recent");
                databaseHandler d = new databaseHandler(getApplicationContext());
                d.insertHOMEKEY("RECENT");
                d.close();
            }
        });
        mHomeWatcher.startWatch();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        logg("Start Service");
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
