package com.media.ui.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.media.ui.Services.appMonitorService;

import static com.media.ui.Util.logger.logg;

public class ScreenUnlockReceiver extends BroadcastReceiver {
    public ScreenUnlockReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            SharedPreferences  sharedpreferences =  context.getSharedPreferences("monitor", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("screen", "on");
            editor.commit();
            Intent service = new Intent(context, appMonitorService.class);
            context.startService(service);
            logg("unlock");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            SharedPreferences  sharedpreferences =  context.getSharedPreferences("monitor", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("screen", "off");
            editor.commit();
            logg("lock");
        }
    }
}
