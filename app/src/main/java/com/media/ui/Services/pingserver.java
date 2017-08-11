package com.media.ui.Services;

import android.app.IntentService;
import android.content.Intent;

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
            d.Sendpoll(constants.DEFAULT_STATUS,1,0);
           // d.Sendpoll(constants.DEFAULT_STATUS,2);

        }else{
            logg("NULL");
        }

    }
}

