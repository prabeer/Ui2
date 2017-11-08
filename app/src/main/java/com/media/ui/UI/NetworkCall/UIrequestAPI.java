package com.media.ui.UI.NetworkCall;

import com.media.ui.ServerJobs.pollRequest;
import com.media.ui.ServerJobs.pollResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import static com.media.ui.constants.PING_PAGE;
import static com.media.ui.constants.UI_POST;

/**
 * Created by prabeer.kochar on 06-11-2017.
 */

public interface UIrequestAPI {

    @POST(UI_POST)
    Call<ArrayList<UIresponse>> sendReq(@Body UIrequest body);
}
