package com.example.octi.Game;

import android.util.Log;

import com.example.octi.models.Game;
import com.example.octi.models.Move;
import com.example.octi.models.Vector2D;

import java.util.ArrayList;

public class LocalGamePresenter {
    LocalGameActivity view;
    Game game;

    public LocalGamePresenter(LocalGameActivity view) {
        this.view = view;
        game = new Game(view.getResources());
        view.drawBoard(game);

        Move move = new Move(
                Move.MoveType.PLACE,
                new Vector2D(1, 5),
                new Vector2D(1, 0),
                new ArrayList<Vector2D>()
        );

        boolean result = game.isMovePossible(move);

        Log.d("fuck", "LocalGamePresenter: " + result);
    }
}
