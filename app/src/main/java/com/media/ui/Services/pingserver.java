package com.media.ui.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.media.ui.Database.databaseHandler;
import com.media.ui.ServerJobs.poll;
import com.media.ui.Util.sharedPreference;
import com.media.ui.constants;

import static com.media.ui.Util.logger.logg;

public class pingserver extends IntentService {

    private sharedPreference store;
    public pingserver() {
        super("pingserver");
    }

    public void onCreate() {
        super.onCreate();
       // store = new sharedPreference();
    }
    protected void onHandleIntent(Intent intent) {
        logg("Service Started!");
     //   store.getPreference(this,"startflag",db);
        if(intent !=null){
            poll d = new poll(this);
            d.Sendpoll(constants.DEFAULT_STATUS,1,"0");
           // d.Sendpoll(constants.DEFAULT_STATUS,2);
            cleanDB(this);


        }else{
            logg("NULL");
        }

    }

    private void cleanDB(Context context){
        databaseHandler d = new databaseHandler(context);
        String hr = "24";
        logg("h-:"+hr);
        try {
            d.deleteRecordspackageMonitor(hr);
            d.deleteRecordBTRecords(hr);
            d.deleteRecordLowBattery(hr);
            d.deleteRecordpackageInstall(hr);
            d.deleteRecordsEARJACK(hr);
            d.deleteRecordsHOMEKEY(hr);
            d.deleteRecordsLockMonitor(hr);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            d.close();
        }

    }
}

