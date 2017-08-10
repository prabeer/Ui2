package com.media.ui.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.media.ui.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import static android.app.Notification.DEFAULT_ALL;
import static com.media.ui.constants.NOTI_ACTION;

/**
 * Created by prabeer.kochar on 20-07-2017.
 */

public class sendNotification {

    Context context;
    String camp_id;
    String header;
    String desc;
    String Noti_Intent;
    Bitmap Appicon;
    String NotiType;
    Bitmap ban;
    public sendNotification(String cap_id, HashMap details, String server, Context mcontext) {
        header = (String) details.get("heading");
       desc = (String) details.get("desc");
        NotiType = (String) details.get("Noti_type");
        Noti_Intent = (String) details.get("Noti_Intent");
        Appicon = dlBitmap((String) details.get("Noti_Icon"));
        if (NotiType.equals("banner")) {
             ban = dlBitmap((String) details.get("Noti_Banner"));
        }
        camp_id = cap_id;
        context = mcontext;
    }
    public void CreateNoti(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(header);
        builder.setContentText(desc);
        if(NotiType.equals("banner")) {
            NotificationCompat.BigPictureStyle s = new NotificationCompat.BigPictureStyle().bigPicture(ban);
            s.setSummaryText(desc);
            builder.setStyle(s);
        }
        builder.setSmallIcon(R.drawable.power);
        builder.setLargeIcon(Appicon);
        builder.setAutoCancel(true);
        builder.setDefaults(DEFAULT_ALL);


        Intent intents = new Intent(context, notificationConfirm.class);
        intents.setAction(NOTI_ACTION);
        Bundle extras = new Bundle();
        extras.putString("URL",Noti_Intent);
        extras.putString("CAMP_ID",camp_id);
        intents.putExtras(extras);

        PendingIntent pendingIntentNoti = PendingIntent.getBroadcast(context, (int) System.currentTimeMillis(), intents, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntentNoti);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(8935, builder.build());
    }
    private Bitmap dlBitmap(String ur) {
        Bitmap img = null;
        try {
            URL url = new URL(ur);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream in = connection.getInputStream();
            img = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;

    }
}
