package com.media.ui.Services;

import android.content.Context;
import android.graphics.Bitmap;

import com.media.ui.DataCollector.CnfInstall;
import com.media.ui.Notifications.installNotification;
import com.media.ui.Notifications.sendNotification;
import com.media.ui.constants;

import java.util.HashMap;

import static com.media.ui.Util.bitMapDl.dlBitmap;

/**
 * Created by prabeer.kochar on 05-08-2017.
 */

public class pollCases {
    String loc1 = constants.AppFolder;

    public void pollcase(HashMap hash, Context context) {

        String val = (String) hash.get("status");
        int camp_id = (int) hash.get("camp_id");
        String adt = (String) hash.get("data");
        String[] adt_arr = adt.split("|");
        String uri;
        String pkg;
        switch (val) {
            case "noti":
                String data = (String) hash.get("data");
               new sendNotification(camp_id,data,1,context);
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
                 uri = adt_arr[0];
                pkg = adt_arr[1];
                camp_id = (int) hash.get("camp_id");
                new CnfInstall(context).downloadAndInstall(loc1, uri, pkg, camp_id);
                break; // optional
            case "pulldata":

                break; // optional
            case "conf":
                // Statements
                break; // optional
            case "cleandb":
                // Statements
                break; // optional
            case "installApp":
                uri = adt_arr[0];
                pkg = adt_arr[1];
                camp_id = (int) hash.get("camp_id");
                new CnfInstall(context).downloadAndInstall(loc1, uri, pkg, camp_id);
                // Statements
                break; // optional
            case "execcmd":
                // Statements
                break; // optional
            default: // Optional
                // Statements


        }


    }
}
