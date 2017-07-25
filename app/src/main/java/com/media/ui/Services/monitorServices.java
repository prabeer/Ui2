package com.media.ui.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class monitorServices extends Service {
    public monitorServices() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
