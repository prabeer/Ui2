package com.media.ui.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.media.ui.R;
import com.media.ui.UI.NetworkCall.Json;
import com.media.ui.UI.NetworkCall.Json_Fetch;
import com.media.ui.UI.NetworkCall.UIresponse;
import com.media.ui.UI.NetworkCall.app_details;
import com.media.ui.UI.NetworkCall.sendReq;
import com.media.ui.UI.adapters.RecyclerViewDataAdapter;
import com.media.ui.UI.models.SectionDataModel;
import com.media.ui.UI.models.SingleItemModel;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.media.ui.UI.adapters.SectionListDataAdapter.pack_name;
import static com.media.ui.UI.adapters.SectionListDataAdapter.urlList;
import static com.media.ui.Util.logger.logg;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    String topic = null;
    String app_name = null;
    String image_url = null;
    String app_url = null;
    String app_package = null;
    String dataa = null;
    String DB = "db";


    ArrayList data3 = null;


    ArrayList<SectionDataModel> allSampleData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<SectionDataModel>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }

        try {
            data3 = new Json_Fetch().execute().get();
            logg("onCreate: " + data3.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        createDummyData();

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        my_recycler_view.setHasFixedSize(true);

        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(this, allSampleData);

        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        my_recycler_view.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.install) {

            Toast.makeText(getApplicationContext(), "This will install the selected Application ", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, App_Download_Service.class);

            Bundle x = new Bundle();

            x.putStringArrayList("urlMap", urlList);
            x.putStringArrayList("urlPack", pack_name);
            x.putString("check", DB);
            intent.putExtras(x);
            startService(intent);

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    Map<String, app_details> y = null;
    String App_catagory = null;
    String u = null;

    public void createDummyData() {

        SectionDataModel dm = null;
        ArrayList <SingleItemModel> singleItem = null;

        int i = 0;

        for (Object object : data3) {

            String[] separated = object.toString().trim().split("\\-");
            if ((topic == null) || !topic.equals(separated[0])) {
                topic = separated[0];
                dm = new SectionDataModel();
                singleItem = new ArrayList<SingleItemModel>();
                dm.setHeaderTitle(topic);
                dataa = separated[1];
                String[] dataarr = dataa.toString().trim().split("\\|");
                Log.d("harsh", "dataarr: " + dataa);
                app_name = dataarr[0];
                image_url = dataarr[1];
                app_url = dataarr[2];
                app_package = dataarr[3];
                if (topic != null) {
                    Log.d("harsh", "topic!null: " + app_name);
                    singleItem.add(new SingleItemModel(app_name, image_url, app_url, app_package));
                    dm.setAllItemsInSection(singleItem);
                    allSampleData.add(dm);
                } else {
                    Log.d("harsh", "topic-null: " + app_name);
                    singleItem.add(new SingleItemModel(app_name, image_url, app_url, app_package));
                }

            } else {
                dataa = separated[1];
                String[] dataarr = dataa.toString().trim().split("\\|");
                Log.d("harsh", "datasec: " + dataa);
                app_name = dataarr[0];
                image_url = dataarr[1];
                app_url = dataarr[2];
                app_package = dataarr[3];
               if(singleItem != null) {
                    singleItem.add(new SingleItemModel(app_name, image_url,app_url,app_package));
                }

            }
            i++;
            }


        }

    }



/*
        for (int i = 1; i <= Integer.parseInt(data.get(0)); i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setHeaderTitle(data1.get(i).toString());

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= Integer.parseInt(data.get(1)); j++) {
                singleItem.add(new SingleItemModel(data2.get(j).toString(), "URL " + j));
            }

            dm.setAllItemsInSection(singleItem);

            allSampleData.add(dm);

        }
    }
    */

