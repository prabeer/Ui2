package com.media.ui.DataCollector;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.media.ui.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.media.ui.Util.utility.imi;

/**
 * Created by prabeer.kochar on 19-07-2017.
 */

public class packageDetails {
    String ins_loc;
    String ins_time;
    String lst_update;
    String src;
    String ver = "0";
    String verno = "0";
    public packageDetails(Context context) {

        try {
        long timi = System.currentTimeMillis();
        String sFileName = constants.PACKAGE_DETAILS + String.valueOf(timi)+constants.UNDERSCORE +imi(context)+ constants.CSVEXT;
        File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
        if (!root.exists()) {
            root.mkdirs();
        }
        File file = new File(root, sFileName);
        file.createNewFile();
        CSVWriter csvWrite = new CSVWriter(new FileWriter(file,true));

        final PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
            String HeadStr[] = {"PkgName", "Inst_Loc", "Inst_Time", "Lst_Update", "verno", "inst_src"};
            csvWrite.writeNext(HeadStr);
        for (ApplicationInfo packageInfo : packages) {
            try {

                PackageInfo pInfo = context.getPackageManager().getPackageInfo(packageInfo.packageName, PackageManager.GET_META_DATA);
                ver = pInfo.versionName;
                src = pm.getInstallerPackageName(packageInfo.packageName);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                ins_loc = String.valueOf(pInfo.applicationInfo.sourceDir);
                ins_time = String.valueOf(sdf.format(new Date(pInfo.firstInstallTime)));
                lst_update = String.valueOf(sdf.format(new Date(pInfo.lastUpdateTime)));
                verno = String.valueOf(pInfo.versionCode);

                String arrStr[] = {packageInfo.packageName, ins_loc, ins_time, lst_update, verno, src};
                csvWrite.writeNext(arrStr);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
            csvWrite.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
