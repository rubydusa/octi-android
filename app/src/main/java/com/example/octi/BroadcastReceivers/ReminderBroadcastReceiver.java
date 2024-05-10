package com.example.octi.BroadcastReceivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.octi.R;

public class ReminderBroadcastReceiver extends  BroadcastReceiver {
    private static final String CHANNEL_ID = "REMINDER_CHANNEL_ID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("fuck", "onReceive: fuck you");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("I am a reminder channel");
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("I miss you")
                .setContentText("Come play with me :)")
                .setSmallIcon(R.drawable.octigon_icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();

        notificationManager.notify(0, notification);
    }
}
