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
        HomeWatcher mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
               logg("Home Press");
            }
            @Override
            public void onHomeLongPressed() {
                logg("Home Long Press");
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
