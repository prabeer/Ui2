package com.media.ui.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.media.ui.Database.lowBatteryDB;

public class lowBattery extends BroadcastReceiver {
    public lowBattery() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        lowBatteryDB LBD = new lowBatteryDB(context);
        if (intent.getAction().equals(Intent.ACTION_BATTERY_LOW)) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                LBD.insertBTdata("LOW_BATTERY");
            }
        }

    }
}
