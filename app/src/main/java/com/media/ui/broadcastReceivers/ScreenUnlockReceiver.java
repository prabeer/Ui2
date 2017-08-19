package com.media.ui.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.media.ui.Services.appMonitorService;

import static com.media.ui.Util.logger.logg;

public class ScreenUnlockReceiver extends BroadcastReceiver {
    public ScreenUnlockReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if(Intent.ACTION_SCREEN_ON.equals(action)) {
            context.startService(new Intent(context, appMonitorService.class));
            logg("unlock");
        } else if(Intent.ACTION_SCREEN_OFF.equals(action)) {
            context.stopService(new Intent(context, appMonitorService.class));
            logg("lock");
        }
    }
}
