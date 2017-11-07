package com.media.ui.UI;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by prabeer.kochar on 07-11-2017.
 */

public class App_Download_Service extends IntentService {
    InstallApp ap;
    Context context;
    Main_DataBase md;
    String Sr;
    // HashMap<Integer,String> pr = new HashMap<>();

    public App_Download_Service() {

        super("App_Download_Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("harsh", "service start");
        if (intent != null) {
            Log.d("harsh", "intent found");
            Sr = (String) intent.getSerializableExtra("check");
            md = new Main_DataBase(this);
            if(Sr != null) {
                if (Sr.equals("db")) {
                    ArrayList<String> pr = new ArrayList<>();
                    ArrayList<String> Hr = new ArrayList<>();
                    pr = (ArrayList<String>) intent.getSerializableExtra("urlMap");
                    Hr = (ArrayList<String>) intent.getSerializableExtra("urlPack");
                    for (int i = 0; i < pr.size(); i++) {
                        md.insertINSTALL_TABLE("", Hr.get(i).toString(), pr.get(i).toString(), "");
                        Log.d("harsh", "Intent pkg: " + pr.get(i).toString());
                    }

                }
            }

            try {
                String pkg = "";
                for(int x = 0; x < md.selectDATA().size();x++ ) {
                    pkg = md.selectDATA().get(x).getPackage_name().toString();
                    Log.d("harsh","pkg:"+x+"-"+ pkg);
                }


                ArrayList<InstallTable> data = new ArrayList<InstallTable>();
                data =  md.selectDATA();
                String getLink = data.get(0).getApp_url().toString();
                Download dl = new Download();
                Log.d("harsh", "link:URL "+ getLink);
                String loc = dl.DownloadFiles(getLink);
                Log.d("harsh", "Location:"+loc);
                // Change status coulmn in Table
                md.insertUPDATE_TABLE1("Downloading",pkg);
                Log.d("harsh", "pkg:"+pkg);
                if (!loc.equals("")) {
                    Log.d("harsh", "dlcomplete");
                    // Updating Location in DataBase
                    md.insertINSTALL_TABLE2(loc,pkg);
                    // Change status column in Table
                    md.insertUPDATE_TABLE1("Downloaded",pkg);
                    ap = new InstallApp(this);
                    Log.d("harsh", loc);

                    // Added loc in table.
                    //  md.insertUPDATE_TABLE1(loc,pkg);

                    if (ap.install(loc, pkg)) {

                        md.insertUPDATE_TABLE1("InstallStart",pkg);
                        Log.d("harsh", "install done");
                    } else {
                        Log.e("harsh", "install fail");
                    }


                } else {
                    Log.d("harsh", "doInBackground: LOL");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return;
        }
        Log.d("harsh", "service stop");
        stopSelf();
}
