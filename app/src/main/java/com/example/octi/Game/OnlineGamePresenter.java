package com.example.octi.Game;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;

public class OnlineGamePresenter implements Repository.LoadGameListener {
    OnlineGameActivity view;
    String id;

    public OnlineGamePresenter(OnlineGameActivity view, String id) {
        this.view = view;
        this.id = id;

        Repository.getInstance().readGame(id, this);
    }


    @Override
    public void updateGame(Game game) {
        view.drawBoard(game);
    }
}
