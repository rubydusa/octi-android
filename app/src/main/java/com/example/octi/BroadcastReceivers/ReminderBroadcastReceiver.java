package com.example.octi.BroadcastReceivers;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.octi.R;

public class ReminderBroadcastReceiver extends  BroadcastReceiver {
    private static final String CHANNEL_ID = "REMINDER_CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("I miss you")
                .setContentText("Come play with me :)")
                .setSmallIcon(R.drawable.octigon_icon)
                .build();
        notificationManager.notify(1, notification);
    }
}
