package com.media.ui.broadcastReceivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.NotificationCompat;

import com.media.ui.Alarms.installAlarm;
import com.media.ui.Database.databaseHandler;
import com.media.ui.R;
import com.media.ui.ServerJobs.poll;
import com.media.ui.Services.registerBroadcastLock;
import com.media.ui.Util.sharedPreference;
import com.media.ui.constants;

import java.util.Arrays;

import static android.app.Notification.DEFAULT_ALL;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.media.ui.Util.logger.logg;
import static com.media.ui.Util.pollFlagsConstants.FInstallComplete;
import static com.media.ui.Util.pollFlagsConstants.InstallComplete;
import static com.media.ui.Util.pollFlagsConstants.InvalidAppInstall;
import static com.media.ui.Util.pollFlagsConstants.SelfUpdateComplete;
import static com.media.ui.Util.pollFlagsConstants.first_launch_pkg;
import static com.media.ui.constants.APP_INSTALL_COMPLETE_NOTI_DESC;
import static com.media.ui.constants.APP_INSTALL_COMPLETE_NOTI_TOPIC;
import static com.media.ui.constants.APP_VER;
import static com.media.ui.constants.SAFE_PACKAGES;
import static com.media.ui.constants.SELF_PACKAGE;
import static com.media.ui.constants.db;

public class appInstallComplete extends BroadcastReceiver {
    public appInstallComplete() {
    }
    private sharedPreference store;
    private installAlarm alarm;
    @Override
    public void onReceive(Context context, Intent intent) {
        logg("Broadcast Received");
        String actionStr = intent.getAction();
        try {
            if (Intent.ACTION_PACKAGE_ADDED.equals(actionStr)) {
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                databaseHandler PIS = new databaseHandler(context);
                PIS.insertPackageStatus(packageName, "INSTALL");
                logg(packageName+":INSTALL");
                final PackageManager pm = context.getPackageManager();
                String inst = pm.getInstallerPackageName(packageName);
                /*
                * Alert for Invalid install
                */
                if (!Arrays.asList(SAFE_PACKAGES).contains(inst)) {
                    new poll(context).Sendpoll(InvalidAppInstall+":"+packageName+":"+inst,1,"0");
                    databaseHandler camp = new databaseHandler(context);
                    camp.insertCAMPDetails("0",InvalidAppInstall+":"+packageName+":"+inst);
                    camp.closedb();
                }
                /* *************************************************** */

                SharedPreferences sharedpreferences = context.getSharedPreferences(constants.pakage, Context.MODE_PRIVATE);
                if (sharedpreferences.contains("pkg")) {
                    String pak = sharedpreferences.getString("pkg", null);
                    String camp_id = sharedpreferences.getString("camp_id", null);
                    String ins_type = sharedpreferences.getString("ins_type", null);

                    logg("Shared-pkg:" + pak);
                    if (ins_type.equals("askins")) {
                        if (pak.equals(packageName)) {
                            logg("Package Found");
                            createMyNotification(APP_INSTALL_COMPLETE_NOTI_TOPIC, APP_INSTALL_COMPLETE_NOTI_DESC, packageName, context);
                            new poll(context).Sendpoll(InstallComplete, 1, camp_id);
                            databaseHandler camp = new databaseHandler(context);
                            camp.insertCAMPDetails(camp_id,InstallComplete);
                            camp.closedb();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove("pkg");
                            editor.remove("camp_id");
                            editor.remove("ins_type");
                            editor.commit();
                        }
                        logg("pkg:" + packageName);
                    } else if (ins_type.equals("frcins")) {
                        if (pak.equals(packageName)) {
                            logg("Package Found");
                            new poll(context).Sendpoll(FInstallComplete, 1, camp_id);
                            databaseHandler camp = new databaseHandler(context);
                            camp.insertCAMPDetails(camp_id,FInstallComplete);
                            camp.closedb();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.remove("pkg");
                            editor.remove("camp_id");
                            editor.remove("ins_type");
                            editor.commit();
                        }
                    }
                }

            }else if(Intent.ACTION_PACKAGE_REMOVED.equals(actionStr)){
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                databaseHandler PUS = new databaseHandler(context);
                PUS.insertPackageStatus(packageName, "UNINSTALL");
                logg(packageName+":UNINSTALL");
                /* Self Update To restart app*/
                if(packageName.equals(SELF_PACKAGE)){
                    alarm = new installAlarm();
                    store = new sharedPreference();
                    store.setPreference(context,"startflag","1",db);
                    alarm.setUpAlarm(context);
                    logg( "Alarm Set");
                    Intent service = new Intent(context, registerBroadcastLock.class);
                    context.startService(service);
                    new poll(context).Sendpoll(SelfUpdateComplete, 1, APP_VER);
                    databaseHandler camp = new databaseHandler(context);
                    camp.insertCAMPDetails(APP_VER,SelfUpdateComplete);
                     camp.closedb();
                }
                /* ********************/
            }else if(Intent.ACTION_PACKAGE_FIRST_LAUNCH.equals(actionStr)){
                String packageName = intent.getData().getEncodedSchemeSpecificPart();
                databaseHandler PUS = new databaseHandler(context);
                PUS.insertPackageStatus(packageName, first_launch_pkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createMyNotification(String titl, String titlcont, String apppack, Context mContext) {

        PackageManager pm = mContext.getPackageManager();
        Intent LaunchIntent = null;
        String name = "";
        String stat = "";
        logg("creating Notification");
        try {
            if (pm != null) {
                logg("launch Noti:" + apppack);
                ApplicationInfo app = mContext.getPackageManager().getApplicationInfo(apppack, 0);
                name = (String) pm.getApplicationLabel(app);
                LaunchIntent = pm.getLaunchIntentForPackage(apppack);

            } else {
                logg("fail Noti");
                stat = "fl";

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            stat = "exc";
            logg("Launch Excep");
        }
        if (stat.equals("")) {
            logg("stats:" + stat);
            try {
                // NotificationManager notificationmanager = (NotificationManager) mContext
                //        .getSystemService(NOTIFICATION_SERVICE);
                // notificationmanager.cancel(8935);

                Intent intent = LaunchIntent; // new Intent();
                PendingIntent pIntent = PendingIntent.getActivity(mContext, (int) System.currentTimeMillis(), intent, 0);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
                builder.setContentTitle(titl);
                builder.setContentText(titlcont);
                builder.setSmallIcon(R.drawable.power);
                builder.setAutoCancel(true);
                builder.setDefaults(DEFAULT_ALL);
                builder.setContentIntent(pIntent);

                NotificationManager manager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);
                manager.notify(8945, builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
            logg("stats:" + stat);
        } else {
            logg("stats:" + stat);
        }
    }
}
