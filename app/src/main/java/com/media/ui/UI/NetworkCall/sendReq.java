package com.media.ui.UI.NetworkCall;

import android.content.Context;
import android.os.AsyncTask;

import com.media.ui.ServerJobs.httpClient;
import com.media.ui.Util.utility;
import com.media.ui.constants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.ui.Util.GeneralUtil.isNetworkAvailable;
import static com.media.ui.Util.logger.logg;


/**
 * Created by prabeer.kochar on 06-11-2017.
 */

public class sendReq extends AsyncTask<String, String, ArrayList<UIresponse>> {
    Context mContext = null;

    public sendReq(Context context) {
        mContext = context;
    }

    protected ArrayList<UIresponse> doInBackground(String... urls) {

        String IM = null;
        String st = null;
        String loc = null;
        String mcc = null;
        String cel = null;
        String dev = null;
        HashMap hm = new HashMap();
        ArrayList<Json> x = null;
        Response<ArrayList<UIresponse>> tasks = null;

        IM = utility.imi(mContext);
        st = constants.DEFAULT_STATUS;
        try {
            loc = utility.Gloc(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mcc = utility.operator(mContext);
        try {
            cel = Integer.toString(utility.GetCellid(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dev = utility.DeviceDetails();
        logg("data request");
        UIrequestAPI taskService = httpClient.getClient().create(UIrequestAPI.class);
        Call<ArrayList<UIresponse>> call = taskService.sendReq(new UIrequest(IM, st, loc, mcc, cel, dev));
        try {
            tasks = call.execute();
            if (tasks != null) {
                logg("data received");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (tasks.body() != null) {
            if (tasks.body().get(0).getApp_catagory() != null) {
                logg(String.valueOf(tasks.body().get(0).getApp_catagory()) + "json is not null");
            } else {
                logg("getApp_catagory is  null");
            }
            return tasks.body();
        } else {
            logg("json is  null");
            return null;
        }
    }

    protected void onPostExecute(ArrayList<UIresponse> result) {
        super.onPostExecute(result);
    }
}
