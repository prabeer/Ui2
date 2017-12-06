package com.media.ui.UI.NetworkCall;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.media.ui.UI.NetworkCall.PollRes.poll;
import static com.media.ui.Util.logger.logg;
import static com.media.ui.constants.URL13;

/**
 * Created by harsh.arora on 31-10-2017.
 */

public class Json_Fetch extends AsyncTask<String, String, ArrayList<String>> {


    String urldisplay =URL13;

    String title;

    ArrayList a;



    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        logg("doInBackground:URL:"+urldisplay);

        String Str= poll(urldisplay);

        a = new ArrayList<>();

        logg("doInBackground:Str:"+Str);
        try {
            logg("doInBackground:entryto try ");
            JSONObject jObject = new JSONObject(Str);
            JSONArray json_Array = jObject.getJSONArray("Json");
            logg("doInBackground: json_Array print to string"+json_Array.toString());

            for(int i=0;i<json_Array.length();i++) {
                JSONObject json_data = json_Array.getJSONObject(i);
                title = json_data.getString("app_catagory");

                logg("doInBackground: json_Array print app_category"+title);

                JSONArray json_Array1 = json_data.getJSONArray("app_details");

                logg("doInBackground: json_Array_1 print "+json_Array1.toString());
                logg("doInBackground: json_Array_1 length "+json_Array1.length());
                for (int j = 0; j < json_Array1.length(); j++) {
                    JSONObject json_data1 = json_Array1.getJSONObject(j);

                    String app_name = json_data1.getString("appName");
                    String image_url = json_data1.getString("imageURL");
                    String app_url = json_data1.getString("APKlocation");
                    String app_package = json_data1.getString("APPPackage");
                   // logg("doInBackground data:" +title+"|"+app_name+"|"+image_url+"|"+app_url+"|"+app_package);
                    String x =  title+"-"+app_name+"|"+image_url+"|"+app_url+"|"+app_package;
                        a.add(x);
                }
            }

            // Isko thek karna hai..................
            String topic = null;
            String app_name = null;
            String image_url = null;
            String app_url = null;
            String app_package = null;
            String data = null;
            for (Object object: a) {
                String[] separated =  object.toString().trim().split("\\-");
                if((topic == null) || !topic.equals(separated[0])){
                topic = separated[0];
                    data = separated[1];
                    String[] dataarr = data.toString().trim().split("\\|");
                    app_name =  dataarr[0];
                    image_url = dataarr[1];
                    app_url = dataarr[2];
                    app_package = dataarr[3];
                    logg("topic"+topic);
                    logg("data:"+app_name+","+image_url);
                }else{
                    data = separated[1];
                    String[] dataarr = data.toString().trim().split("\\|");
                    app_name =  dataarr[0];
                    image_url = dataarr[1];
                    app_url = dataarr[2];
                    app_package = dataarr[3];
                    //logg("topic"+topic);
                    logg("data:"+app_name+","+image_url);
                }

            }

        } catch (Exception e) {
            logg("doInBackground: " + e.getMessage());
        }
        return a;
    }
    protected void onPostExecute(ArrayList<String> result) {
        super.onPostExecute(result);
    }
}
