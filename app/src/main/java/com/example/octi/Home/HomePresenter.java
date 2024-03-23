package com.example.octi.Home;

import android.util.Log;

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
            view.navigateToCreateRoom(game.getGameId());
        });
    }
}
