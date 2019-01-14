package com.example.nir30.newsly2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    public AlarmBroadcastReceiver() {
        super();
    }

    @Override
    public IBinder peekService(Context myContext, Intent service) {
        return super.peekService(myContext, service);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    }

}
