package com.media.ui.ServerJobs;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by prabeer.kochar on 26-07-2017.
 */

public interface requestAPI {
    @POST("mob.php")
    Call<pollResponse> poll(@Body pollRequest body);

    @Streaming
    @GET
    Call<ResponseBody> download(@Url String Url);

    @Multipart
    @POST("upload.php")
    Call<ResponseBody> upload(@Part("file") RequestBody name, @Part MultipartBody.Part file);
}
