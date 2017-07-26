package com.media.ui.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 24-07-2017.
 */

public class httpHelper {

    public String sendPost(String server, String data){
        String type = server.substring(0,server.indexOf(':'));
if(type.isEmpty()) {
    type = "http";
}
            int responseCode = 0;
if(type == "http") {
    try{
    URL url = new URL(server);

    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setReadTimeout(15000 /* milliseconds */);
    conn.setConnectTimeout(15000 /* milliseconds */);
    conn.setRequestMethod("POST");
    conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    conn.setDoInput(true);
    conn.setDoOutput(true);
    OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
    writer.write(data);

    writer.flush();
    writer.close();
    logg( "ACk IMEI Complete");
    responseCode = conn.getResponseCode();
    logg(String.valueOf(responseCode));
    if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new
                InputStreamReader(
                conn.getInputStream()));

        StringBuffer sb = new StringBuffer("");
        String line = "";

        while ((line = in.readLine()) != null) {
            // Log.d("cmds", String.valueOf(line));
            sb.append(line);
            // break;
        }
        logg("Response:" + sb.toString());
        String str = sb.toString();
        in.close();
        return str;


    }
    } catch (ProtocolException e) {
        e.printStackTrace();
        return "false";
    } catch (MalformedURLException e) {
        e.printStackTrace();
        return "false";
    } catch (IOException e) {
        e.printStackTrace();
        return "false";
    }

}else {
    try {
        URL url = new URL(server);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setReadTimeout(15000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(data);

        writer.flush();
        writer.close();
        logg("ACk IMEI Complete");
        responseCode = conn.getResponseCode();
        logg(String.valueOf(responseCode));

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(
                    conn.getInputStream()));

            StringBuffer sb = new StringBuffer("");
            String line = "";

            while ((line = in.readLine()) != null) {
                // Log.d("cmds", String.valueOf(line));
                sb.append(line);
                // break;
            }
            logg("Response:" + sb.toString());
            String str = sb.toString();


            in.close();

        }
     } catch (ProtocolException e) {
            e.printStackTrace();
            return "false";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }

       return "False";
    }

}
