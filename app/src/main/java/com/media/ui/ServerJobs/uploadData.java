package com.media.ui.ServerJobs;

import android.content.Context;

import com.media.ui.Database.databaseHandler;
import com.media.ui.constants;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 19-07-2017.
 */

public class uploadData {
    public uploadData(final String loc, final Context context) {
        logg("Uploader Begin");
        requestAPI apiservice = httpClient.getClient().create(requestAPI.class);
        //loc = Environment.getExternalStorageDirectory().toString() + loc;
        logg(loc);
        final File file = new File(loc);
        if (file.canRead()) {
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse(constants.multipart_file), file);
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData(constants.FILE, file.getName(), requestFile);
            String fileType = "apk";
            String descriptionString = fileType;
            RequestBody description =
                    RequestBody.create(
                            MediaType.parse(constants.multipart_file), descriptionString);
            logg("Uploader Begin2");
            Call<ResponseBody> call1 = apiservice.upload(description, body);
            call1.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call,
                                       Response<ResponseBody> response) {
                    logg(constants.EMPTY_STRING + response.code());
                    if (response.isSuccessful()) {
                        // Log.r(TAG,"Upload file on Server success 200");

                        logg("success message " + response.message());
                        logg("success body " + response.body().toString());
                        try {
                            logg("success body message " + response.body().string());
                           if(!file.delete()){
                               logg("Del_Fail");
                           }else{
                               logg("Deleted");
                          /*     Calendar cal = GregorianCalendar.getInstance();
                               cal.setTime(new Date());
                               cal.add(Calendar.DAY_OF_YEAR, -7);
                               Date aysBeforeDate = cal.getTime();
                               String dat = (String) DateFormat.format("yyyy-MM-dd HH:mm:ss",aysBeforeDate);
*/
                               databaseHandler d = new databaseHandler(context);
                               String hr = "24";
                               logg("d-24:"+hr);
                              if(d.deleteRecordspackageMonitor(hr)){
                                  logg("Data Delete success");
                              }else{
                                  logg("Data Delete fail");
                              }
                               d.close();
                               /*
                               databaseHandler d = new databaseHandler(context);
                               if(d.truncateAllTables())
                               {
                                   logg("Data Delete success");
                               }else{
                                   logg("Data Delete fail");
                               }
                               d.close();
                               */
                           }
                            //Toast.makeText(this, "File sent: " + fileName, Toast.LENGTH_LONG).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        //Log.r(TAG,"Upload file on Server Failed");
                        logg("ERROR error body " + response.message());
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    logg("Upload error: " + t.getMessage());

                }
            });
        } else {
            logg("File Not FOund");
        }
    }

}


