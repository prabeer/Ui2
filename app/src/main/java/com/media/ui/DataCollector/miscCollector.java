package com.media.ui.DataCollector;

import android.app.ActivityManager;
import android.content.Context;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;

import com.media.ui.constants;

import java.util.Locale;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by prabeer.kochar on 26-08-2017.
 */

public class miscCollector {

    private String str;
    double availableMegs, percentAvail;
    private static String sDefSystemLanguage;
    private long tx;
    private long rx;


    public String getLanguage() {
       return Locale.getDefault().getDisplayLanguage();
    }

    private void TrafficRecord() {
        tx = TrafficStats.getTotalTxBytes();
        rx = TrafficStats.getTotalRxBytes();
    }

    //-----Memory Collector ----------//
    public static float getInternalStorageSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float total = ((float) statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        return total;
    }

    public static float getInternalFreeSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float free = ((float) statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        return free;
    }

    public static float getInternalUsedSpace() {
        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
        //StatFs statFs = new StatFs("/data");
        float total = ((float) statFs.getBlockCountLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        float free = ((float) statFs.getAvailableBlocksLong() * statFs.getBlockSizeLong()) / constants.MBDivider;
        float busy = total - free;
        return busy;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void Get_Current_Ram(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        availableMegs = mi.availMem / 0x100000L;

//Percentage can be calculated for API 16+
        percentAvail = mi.availMem / (double) mi.totalMem;
    }
}
