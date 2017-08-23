package com.media.ui.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.media.ui.Database.lowBatteryDB;

import static com.media.ui.Util.logger.logg;

public class lowBattery extends BroadcastReceiver {
    public lowBattery() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            logg("Low Battery Broadcast2");

            lowBatteryDB LBD = new lowBatteryDB(context);
            logg("Low Battery Broadcast");
            LBD.insertLBdata("LOW_BATTERY");
          LBD.closedb();
        }
    }

}

