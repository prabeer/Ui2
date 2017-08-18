package com.media.ui.Util;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.widget.Toast;

import com.media.ui.constants;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by prabeer.kochar on 17-08-2017.
 */

public class FileWriter extends Writer {

    public FileWriter(File gpxfile) {
    }

    public void DBtoCSV(Context context, String sFileName, Cursor cur ) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), constants.AppFolder);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            csvWrite.writeNext(cur.getColumnNames());
            while (cur.moveToNext()) {
                //Which column you want to exprort
                String arrStr[] = {cur.getString(0), cur.getString(1), cur.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            cur.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        //CSVWriter csvWrite = new CSVWriter(new FileWriter(file));


        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }
}
