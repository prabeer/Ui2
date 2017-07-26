package com.media.ui.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.media.ui.Alarms.installAlarm;
import com.media.ui.Util.sharedPreference;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.constants.db;

public class bootComplete extends BroadcastReceiver {
    private sharedPreference store;
    private installAlarm alarm;
    public bootComplete() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        alarm = new installAlarm();
        store = new sharedPreference();

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            store.setPreference(context,"startflag","1",db);
            alarm.setUpAlarm(context);
            logg( "Alarm Set");
        }
        logg( "Broad Cast Received");
    }
}
