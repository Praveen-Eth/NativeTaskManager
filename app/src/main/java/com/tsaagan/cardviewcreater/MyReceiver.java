package com.tsaagan.cardviewcreater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notificationBuilder =  notificationHelper.getChannelNotification("You received a Message","it's time go for work",R.mipmap.ic_launcher);
        notificationHelper.getManager().notify(1,notificationBuilder.build());

    }
}
