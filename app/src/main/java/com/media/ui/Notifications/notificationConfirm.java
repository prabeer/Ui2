package com.media.ui.Notifications;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.media.ui.DataCollector.CnfInstall;
import com.media.ui.ServerJobs.poll;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.Util.utility.imi;

public class notificationConfirm extends BroadcastReceiver {
    public notificationConfirm() {
    }
    private static final String YES_ACTION = "YES_ACTION";
    private static final String NO_ACTION = "NO_ACTION";
    private static final String NOTI_ACTION = "NOTI_ACTION";
    private static String IMEI = "";
    private static int server = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Bundle extras = intent.getExtras();
        String camp_id;
        String url;
        if(extras == null) {
            camp_id= null;
            url = null;
        } else {
            camp_id= extras.getString("CAMP_ID");
            url = extras.getString("URL");
        }

        IMEI = imi(context);

        if(YES_ACTION.equals(action)) {
            new CnfInstall(context).sendCnf(camp_id);
            logg("Pressed YES");
        } else if(NO_ACTION.equals(action)) {

            logg("Pressed No");
        }else if(NOTI_ACTION.equals(action)){
            Intent intents = new Intent(Intent.ACTION_VIEW);
            intents.setData(Uri.parse(url));
            PendingIntent pi = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intents, 0);
            try {
                pi.send();
            } catch (PendingIntent.CanceledException e) {
                e.printStackTrace();
            }
            new poll(context).Sendpoll("NotiClick",1,camp_id);
            logg("Pressed Noti"+"|url:"+url+"|campid:"+camp_id);
        }
    }
}
