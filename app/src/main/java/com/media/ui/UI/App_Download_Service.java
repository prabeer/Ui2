package com.media.ui.UI;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.media.ui.DataCollector.CnfInstall;
import com.media.ui.Database.UiDB;
import com.media.ui.Database.databaseHandler;
import com.media.ui.constants;

import java.util.ArrayList;

/**
 * Created by prabeer.kochar on 07-11-2017.
 */

public class App_Download_Service extends IntentService {
    UiDB ap;
    Context context;
    databaseHandler md;
    String Sr;
    String loc1 = constants.AppFolder;
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
            md = new databaseHandler(this);
            if (Sr != null) {
                if (Sr.equals("db")) {
                    ArrayList<String> pr = new ArrayList<>();
                    ArrayList<String> Hr = new ArrayList<>();
                    pr = (ArrayList<String>) intent.getSerializableExtra("urlMap");
                    Hr = (ArrayList<String>) intent.getSerializableExtra("urlPack");
                    for (int i = 0; i < pr.size(); i++) {
                        md.insertIntoUI("", Hr.get(i).toString(), pr.get(i).toString(), "");
                        Log.d("harsh", "Intent pkg: " + pr.get(i).toString());
                    }

                }
            }

            try {
                String pkg = "";
                String getLink = "";
                ArrayList<UiDB> data = new ArrayList<UiDB>();
                data = md.selectApp();
                for (int x = 0; x < data.size(); x++) {
                    pkg = data.get(x).getPackage_name().toString();
                    getLink = data.get(x).getApp_url().toString();
                    Log.d("harsh", "pkg:" + x + "-" + pkg);
                }
                new CnfInstall(context).downloadAndInstall(loc1, getLink, pkg, "0");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Log.d("harsh", "service stop");
        stopSelf();
    }
}
