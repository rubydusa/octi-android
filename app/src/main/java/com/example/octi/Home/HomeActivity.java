package com.example.octi.Home;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.octi.BroadcastReceivers.ReminderBroadcastReceiver;
import com.example.octi.Entry.EntryActivity;
import com.example.octi.Game.LocalGameActivity;
import com.example.octi.Account.AccountActivity;
import com.example.octi.R;
import com.example.octi.Room.CreateRoomActivity;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements GameOptionsDialog.GameOptionsListener {
    HomePresenter presenter;
    private static final int NOTIFICATION_REQUEST_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupNotifications();

        presenter = new HomePresenter(this);
    }

    public void onPlayClick(View view) {
        GameOptionsDialog dialog = new GameOptionsDialog();
        dialog.setGameOptionsListener(this);
        dialog.show(getSupportFragmentManager(), "GameOptionsDialog");
    }

    public void onAccountClick(View view) {
        Intent intent = new Intent(this, AccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocalGameSelected() {
        Intent intent = new Intent(this, LocalGameActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateRoomSelected() {
        presenter.onClickCreateRoom();
    }

    public void navigateToCreateRoom(String gameId) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        intent.putExtra(getString(R.string.game_id), gameId);
        startActivity(intent);
    }

    @Override
    public void onJoinRoomSelected(String roomCode) {
        presenter.onJoinRoom(roomCode);
    }

    public void forceLogOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, EntryActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]
            permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_REQUEST_ID) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createReminderNotification();
            }
        }
    }

    private void setupNotifications() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED) {
            createReminderNotification();
        } else {
            // no need to request notifications if below tiramisu
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                String[] permissions = new String[]{android.Manifest.permission.POST_NOTIFICATIONS};
                requestPermissions(permissions, NOTIFICATION_REQUEST_ID);
            }
        }
    }

    private void createReminderNotification() {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), ReminderBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);

        // Set the alarm to start at a certain time and repeat at specified intervals
        long startTime = System.currentTimeMillis(); // starting time, e.g., now
        long repeatInterval = 1000 * 60 * 60; // repeat every hour

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, startTime, repeatInterval, pendingIntent);
    }
}