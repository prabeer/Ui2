package com.media.ui.Alarms;

import android.app.IntentService;
import android.content.Intent;

import com.media.ui.Services.pingserver;
import com.media.ui.Util.sharedPreference;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 21-02-2017.
 */

public class setAlarm extends IntentService{

  //  private SharedPreferences sharedPref;
   private sharedPreference store;
    // Context mcontext;
    public setAlarm() {
        super("setAlarm");
    }
    public void onCreate() {
        super.onCreate();
       // store = new sharedPreference();
        // this gets called properly
       // store.setPreference(this,"startflag","1",db);
        logg("Service onCreate()");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // store.setPreference(this,"startflag","1",db);
        logg("Service Reset!");
try {
    Intent dialogIntent = new Intent(getBaseContext(), pingserver.class);
    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    getApplication().startService(dialogIntent);

    logg("Start New Service!");
    stopSelf();
}catch (Exception e){
    stopSelf();
    e.printStackTrace();
}
    }

}
