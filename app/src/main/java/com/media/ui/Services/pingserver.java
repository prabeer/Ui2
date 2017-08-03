package com.media.ui.Services;

import android.app.IntentService;
import android.content.Intent;

import com.media.ui.Util.sharedPreference;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.constants.db;

public class pingserver extends IntentService {

    private sharedPreference store;
    public pingserver() {
        super("pingserver");
    }

    public void onCreate() {
        super.onCreate();
        store = new sharedPreference();
    }
    protected void onHandleIntent(Intent intent) {
        String camp_id = "";
        logg("Service Started!");
        store.getPreference(this,"startflag",db);
        if(intent !=null){
logg("test");
        }

    }
}

