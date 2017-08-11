package com.media.ui.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prabeer.kochar on 11-08-2017.
 */

public class bitMapDl {
    public static Bitmap dlBitmap(String ur) {
        Bitmap img = null;
        try {
            URL url = new URL(ur);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream in = connection.getInputStream();
            img = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return img;

    }
}
