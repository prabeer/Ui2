package com.media.ui.ServerJobs;

import com.media.ui.Util.logger;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by prabeer.kochar on 03-08-2017.
 */

public class poll {

    HashMap<String,String> hm;

    public HashMap<String,String> poll(String IM, final String st, String loc, String mcc, String cel, String dev){


        requestAPI apiservice = httpClient.getClient().create(requestAPI.class);
        Call<pollResponse> call = apiservice.poll(new pollRequest(IM, st, loc, mcc, cel, dev));
        call.enqueue(new Callback<pollResponse>() {
            @Override
            public void onResponse(Call<pollResponse> call, Response<pollResponse> response) {
                String data = response.body().getData();
                String status = response.body().getStatus();
                hm.put("data",data);
                hm.put("status",status);
            }

            @Override
            public void onFailure(Call<pollResponse> call, Throwable t) {
                logger.logg("Call Failed");
            }
        });
            return hm;
    }
}
