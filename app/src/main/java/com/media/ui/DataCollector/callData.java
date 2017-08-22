package com.media.ui.DataCollector;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.CallLog;

import com.media.ui.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

import static com.media.ui.Util.logger.logg;
import static com.media.ui.Util.utility.imi;

/**
 * Created by prabeer.kochar on 22-08-2017.
 */

public class callData {
    String sFileName;
    public callData(Context context){
        long timi = System.currentTimeMillis();
        Uri allCalls = Uri.parse("content://call_log/calls");
        Cursor managedCursor = context.getContentResolver().query( allCalls,null, null,null, null);
        sFileName = constants.callFile + String.valueOf(timi)+ "_"+imi(context)+".csv";
        writeData(managedCursor);

    }
    private void writeData(Cursor cursor){

        try {
            File root = new File(Environment.getExternalStorageDirectory(), constants.DataFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file,true));
            String num= cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));// for  number
            String name= cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));// for name
            String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));// for duration
            int type = Integer.parseInt(cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE)));

            while (cursor.moveToNext()) {
                String phNumber = cursor.getString(Integer.parseInt(num));
                String callType = cursor.getString(type);
                String callDate = cursor.getString(Integer.parseInt(duration));
                Date callDayTime = new Date(Long.valueOf(callDate));
                String callDuration = cursor.getString(Integer.parseInt(duration));
                String dir = null;
                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        dir = "OUTGOING";
                        break;

                    case CallLog.Calls.INCOMING_TYPE:
                        dir = "INCOMING";
                        break;

                    case CallLog.Calls.MISSED_TYPE:
                        dir = "MISSED";
                        break;
                }
                //Which column you want to exprort
                logg(phNumber+","+callType+","+ callDate+","+ String.valueOf(callDayTime)+","+callDuration+","+ dir);
                String arrStr[] = {phNumber, callType, callDate, String.valueOf(callDayTime),callDuration, dir};
                if(file.canWrite()) {
                    csvWrite.writeNext(arrStr);
                    logg("Writable");
                }else{
                    logg("notWritable");
                }

            }
            csvWrite.close();
            cursor.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
