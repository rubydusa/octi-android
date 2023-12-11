package com.example.octi.Game;

import android.util.Log;

import com.example.octi.models.Game;

public class LocalGamePresenter {
    LocalGameActivity view;
    Game game;

    public LocalGamePresenter(LocalGameActivity view) {
        this.view = view;
        game = new Game(view.getResources());
        view.drawBoard(game);
    }
}
