package com.example.octi.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.octi.Game.LocalGameActivity;
import com.example.octi.Account.AccountActivity;
import com.example.octi.R;
import com.example.octi.Room.CreateRoomActivity;

public class HomeActivity extends AppCompatActivity implements GameOptionsDialog.GameOptionsListener {
    HomePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
}