package com.media.ui.UI.NetworkCall;

import android.content.Context;

import com.media.ui.ServerJobs.httpClient;
import com.media.ui.ServerJobs.httpClient2;
import com.media.ui.ServerJobs.pollCases;
import com.media.ui.ServerJobs.pollRequest;
import com.media.ui.ServerJobs.pollResponse;
import com.media.ui.ServerJobs.requestAPI;
import com.media.ui.Util.utility;
import com.media.ui.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.ui.Util.GeneralUtil.isNetworkAvailable;
import static com.media.ui.Util.GeneralUtil.myAppVersion;
import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 06-11-2017.
 */

public class sendReq {

    String IM = null;
    String st = null;
    String loc = null;
    String mcc = null;
    String cel = null;
    String dev = null;
    Context context;
    HashMap hm = new HashMap();

    public sendReq(Context context) {
        IM = utility.imi(context);
        st = constants.DEFAULT_STATUS;
        try {
            loc = utility.Gloc(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mcc = utility.operator(context);
        try {
            cel = Integer.toString(utility.GetCellid(context));
        } catch (Exception e) {
            e.printStackTrace();
        }
        dev = utility.DeviceDetails();
        this.context = context;
    }

    private void Sendpoll(String status, int server) {

        getAllApps (new Callback<UIresponse>() {
            String data = constants.EMPTY_STRING;
            String status = constants.EMPTY_STRING;
            String camp_id_res = "0";
            String App_catagory = null;
            ArrayList<Json> x = null;
            Map<String, app_details> y = null;
            String u;
            @Override
            public void onResponse(Call<UIresponse> call, Response<UIresponse> response) {
                if (response.body() != null)
                {
                    try {
                        x = response.body().getJson();

                        for (int i = 0; i < x.size(); i++) {
                            App_catagory = x.get(i).getApp_catagory();
                            y = x.get(i).getApp_details();
                            Iterator<Map.Entry<String, app_details>> it = y.entrySet().iterator();
                            while (it.hasNext()) {
                                Map.Entry<String, app_details> pair = it.next();
                                u += pair.getKey() + pair.getValue().getAPKlocation();
                            }
                        }
                        //      logg(data + "|" + status + "|" + camp_id_res);
                        if (data == null) {
                            data = "No";
                        }
                        if (status == null) {
                            status = "No";
                        }

                        //      logg(data + "|" + status + "|" + camp_id_res);
                        hm.put(1, data);
                        hm.put(2, status);
                        hm.put(3, camp_id_res);
                        pollCases cases = new pollCases();
                        cases.pollcase(hm, context);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<UIresponse> call, Throwable t) {
               // Log.d("LOG", "Something went wrong :c");
            }

        });
    }

    private long getAllApps(Callback<UIresponse> callback)
    {
        long timestamp = 0;
        Object api = null;
        if(isNetworkAvailable(this.context)) {
            UIrequestAPI apiservice;
            apiservice = httpClient.getClient().create(UIrequestAPI.class);
            retrofit2.Call<UIresponse> call = apiservice.sendReq(new UIrequest(IM, st, loc, mcc, cel, dev));

            call.enqueue(callback);
        }

        return timestamp;
    }


}
