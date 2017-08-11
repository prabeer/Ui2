package com.media.ui.DataCollector;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.media.ui.ServerJobs.httpClient;
import com.media.ui.ServerJobs.poll;
import com.media.ui.ServerJobs.requestAPI;
import com.media.ui.Util.installApp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.media.ui.Util.logger.logg;

/**
 * Created by prabeer.kochar on 10-08-2017.
 */

public class CnfInstall {

    String loc;
    Context context;
    String pkg;
    int camp_id;

    public  CnfInstall(Context context){
        this.context = context;
    }
    public void sendCnf(int camp_id){
        new poll(this.context).Sendpoll("inscnf",1,camp_id);
    }

    public void downloadAndInstall(String loc1, String uri,String pkg1,int camp_id1){
        loc = loc1;
        pkg = pkg1;
        camp_id = camp_id1;
        requestAPI apiservice = httpClient.getClient().create(requestAPI.class);
        Call<ResponseBody> downloadResponseCall = apiservice.download(uri);
        downloadResponseCall.enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> downloadResponseCall, final Response<ResponseBody> response) {
                Log.d("BTT", "Async");
                if (response.isSuccessful()) {
                    final Uri t = getFileUri(context, loc);
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            Log.d("BTT", "Download Start");
                            boolean writtenToDisk = false;
                            try {
                                writtenToDisk = writeResponseBodyToDisk(response.body());
                                try {
                                    Log.d("BTT", "Upload Start");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (writtenToDisk) {
                                if(new installApp(context).install(loc,pkg)){
                                    new poll(context).Sendpoll("insComp",1,camp_id);
                                }
                                Log.d("BTT", "Download done");
                            } else {
                                //Toast.makeText(getApplicationContext(), "download Failed", Toast.LENGTH_LONG).show();
                                Log.d("BTT", "Download failed");
                            }
                            return null;
                        }

                    }.execute();
                }
    }
            public void onFailure(Call<ResponseBody> downloadResponseCall, Throwable t) {

                Log.d("BTT", t.toString());
            }
        });
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) throws IOException {
        logg("Check if folder exists");
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, loc);
        logg("Folder:" + loc);
        if (!file.exists()) {
            logg("Create Folder");
            String[] path = loc.split("\\\\");
            int l = path.length;
            String p = "";
            for (int i = 0; i < l - 1; i++) {
                p += path[i];
            }
            logg("Folder:" + p);
            logg("apk:" + path[l - 1]);
            File dr = new File(sdcard, p);
            for (int x = 0; x < 4; x++) {
                if (dr.mkdirs()) {
                    logg("Dir Created Dl Start");
                    File app = new File(sdcard + "/" + p, "/" + path[l - 1]);
                    FileOutputStream fileOutput = new FileOutputStream(app);
                    InputStream inputStream =  new BufferedInputStream(body.byteStream(), 1024 * 8);
                    int totalSize = (int) body.contentLength();
                    int downloadedSize = 0;
                    byte[] buffer = new byte[1024];
                    int bufferLength = 0;
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        if (downloadedSize == 0) {
                            logg("dl:Start");
                        }
                        //add the data in the buffer to the file in the file output stream (the file on the sd card
                        fileOutput.write(buffer, 0, bufferLength);
                        //add up the size so we know how much is downloaded
                        downloadedSize += bufferLength;
                        //this is where you would do something to report the prgress, like this maybe
                        //logg("dlSize:"+downloadedSize);
                    }
                    if (downloadedSize == totalSize) {
                        fileOutput.close();
                        return true;
                    }
                    logg("dlSize:" + downloadedSize);
                    fileOutput.close();
                } else {

                    if (dr.mkdirs()) {
                        File app = new File(sdcard + "/" + p, "/" + path[l - 1]);
                        FileOutputStream fileOutput = new FileOutputStream(app);
                        InputStream inputStream = new BufferedInputStream(body.byteStream(), 1024 * 8);
                        int totalSize = (int) body.contentLength();
                        int downloadedSize = 0;
                        byte[] buffer = new byte[1024];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            //add the data in the buffer to the file in the file output stream (the file on the sd card
                            fileOutput.write(buffer, 0, bufferLength);
                            //add up the size so we know how much is downloaded
                            downloadedSize += bufferLength;
                            //this is where you would do something to report the prgress, like this maybe
                            logg("dlSize:" + downloadedSize);
                        }
                        if (downloadedSize == totalSize) {
                            fileOutput.close();
                            return true;
                        }
                        fileOutput.close();
                    } else {
                        logg(new String("failed to create dir complete :"));
                        return false;
                    }
                }
            }
            logg(new String("Unable to Download"));
            return false;
        }
        return false;
    }

    private Uri getFileUri(Context context, String fileName) {
        String completePath = context.getFilesDir() + "/" + fileName;

        File file = new File(completePath);
        Uri uri = Uri.fromFile(file);
        return uri;
    }

}
