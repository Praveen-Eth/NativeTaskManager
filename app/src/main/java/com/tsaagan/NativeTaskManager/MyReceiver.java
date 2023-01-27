package com.tsaagan.NativeTaskManager;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import androidx.core.app.NotificationCompat;

public class MyReceiver extends BroadcastReceiver {
    String channelId = "notification1";




    public MyReceiver() {
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("channelId", "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "channelId")
                .setContentTitle(title)
                .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
                .setContentText(body);
        notificationManager.notify(1, notificationBuilder.build());
    }
}
