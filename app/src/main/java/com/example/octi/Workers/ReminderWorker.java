package com.example.octi.Workers;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.octi.Home.HomeActivity;
import com.example.octi.R;

public class ReminderWorker extends Worker {
    private static final String CHANNEL_ID = "REMINDER_CHANNEL_ID";
    private boolean firstTime = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Reminder Channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("send reminders to user to open the app");
        NotificationManager notificationManager = getApplicationContext().getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        if (firstTime) {
            firstTime = false;
            return null;
        }
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FILL_IN_ACTION | PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.octigon_icon)
                .setContentTitle("I miss you")
                .setContentText("Come play with me :)")
                .setContentIntent(pendingIntent)
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        notificationManagerCompat.notify(0, notification);

        return null;
    }

    @Override
    public void onStopped() {
        super.onStopped();
    }


}
