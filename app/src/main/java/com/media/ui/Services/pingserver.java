package com.media.ui.Services;

import android.app.IntentService;
import android.content.Intent;

import com.media.ui.Util.sharedPreference;

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

    }
}

