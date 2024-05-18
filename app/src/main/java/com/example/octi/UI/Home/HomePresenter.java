package com.example.octi.UI.Home;

import android.widget.Toast;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;
import com.example.octi.Models.User;

public class HomePresenter implements Repository.LoadUserListener {
    HomeActivity view;

    public HomePresenter(HomeActivity view) {

        this.view = view;
        Repository.getInstance().setLoadUserListener(this);
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
        Repository.getInstance().gameExists(roomCode, status -> {
            if (status == null) {
                Toast.makeText(view, "Error joining game. Try again", Toast.LENGTH_SHORT).show();
            } else if (status == Game.Status.NULL) {
                Toast.makeText(view, "Room code does not exist", Toast.LENGTH_SHORT).show();
            } else if (status == Game.Status.FINISHED) {
                Toast.makeText(view, "Game finished", Toast.LENGTH_SHORT).show();
            } else {
                view.navigateToCreateRoom(roomCode);
            }
        });
    }

    // check if user is expired
    @Override
    public void updateUser(User user) {
        if (user == null) {
            view.forceLogOut();
        }
    }
}
