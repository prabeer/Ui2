package com.media.ui.Util;

import android.util.Log;

import static com.media.ui.constants.LogEnable;

/**
 * Created by prabeer.kochar on 19-07-2017.
 */

class Logger {
    public  Logger(){

    }
    private static String  TAG = "cmds";
    private static boolean enable = LogEnable;
    public static void logg(String val){
        if(enable) {
            Log.d(TAG, "btt: " + val);
        }
    }
}
