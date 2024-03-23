package com.example.octi.Home;

import android.util.Log;
import android.widget.Toast;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;
import com.google.firebase.auth.FirebaseAuth;

public class HomePresenter {
    HomeActivity view;

    public HomePresenter(HomeActivity view) {
        this.view = view;
    }

    public void onClickCreateRoom() {
        Repository.getInstance().createNewGame(game -> {
            if (game == null) {
                Toast.makeText(view, "Error creating game. Try again", Toast.LENGTH_SHORT).show();
                return;
            }
            view.navigateToCreateRoom(game.getGameId());
        });
    }

    public void onJoinRoom(String roomCode) {
        Repository.getInstance().gameExists(roomCode, gameExists -> {
            if (gameExists == null) {
                Toast.makeText(view, "Error joining game. Try again", Toast.LENGTH_SHORT).show();
            } else if (!gameExists) {
                Toast.makeText(view, "Room code does not exist", Toast.LENGTH_SHORT).show();
            } else {
                view.navigateToCreateRoom(roomCode);
            }
        });
    }
}
