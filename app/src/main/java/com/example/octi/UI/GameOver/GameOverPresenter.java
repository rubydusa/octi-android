package com.example.octi.UI.GameOver;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;

public class GameOverPresenter implements Repository.LoadGameListener {
    GameOverActivity view;
    String id;

    public GameOverPresenter(GameOverActivity view, String id) {
        this.view = view;
        this.id = id;

        Repository.getInstance().setLoadGameListener(this);
        Repository.getInstance().readGame(id);
    }

    @Override
    public void updateGame(Game game) {
        view.displayGame(game);
    }
}
