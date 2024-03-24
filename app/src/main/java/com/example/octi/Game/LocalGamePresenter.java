package com.example.octi.Game;

import com.example.octi.Models.Game;

public class LocalGamePresenter {
    LocalGameActivity view;
    Game game;

    public LocalGamePresenter(LocalGameActivity view) {
        this.view = view;
        game = new Game(null, null, null);
        view.drawBoard(game);
    }
}
