package com.example.octi.Home;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;
import com.google.firebase.auth.FirebaseAuth;

public class HomePresenter {
    HomeActivity view;

    public HomePresenter(HomeActivity view) {
        this.view = view;
    }

    public void onClickCreateRoom() {
        Game game = new Game(FirebaseAuth.getInstance().getUid(), null, null);
        Repository.getInstance().updateGame(game);
        view.navigateToCreateRoom(game.getGameId());
    }
}
