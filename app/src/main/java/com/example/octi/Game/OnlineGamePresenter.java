package com.example.octi.Game;

import android.util.Log;

import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.Models.Move;
import com.example.octi.Models.Vector2D;

public class OnlineGamePresenter implements Repository.LoadGameListener {
    OnlineGameActivity view;
    BoardFragment board;
    String id;
    Game game;

    public OnlineGamePresenter(OnlineGameActivity view, BoardFragment board, String id) {
        this.view = view;
        this.id = id;
        this.board = board;

        Repository.getInstance().readGame(id, this);
    }


    @Override
    public void updateGame(Game game) {
        Log.d("ASAD", "updateGame: updating board");
        this.game = game;
        board.drawBoard(game);
    }

    public void prongPlaceMove(int prong) {
        BoardFragment.Cell selectedCell = board.getSelectedCell();
        if (selectedCell == null) {
            Log.d("ASAD", "prongPlaceMove: no selected cell");
            return;
        }

        // the xs and ys are switched ui wise and I can't bother to change it
        int y = selectedCell.getX();
        int x = selectedCell.getY();

        Log.d("ASAD", "x: " + x + " y: " + y);

        Vector2D target = new Vector2D(x, y);

        Move move = Move.createPlaceMove(target, prong);
        if (game.makeMove(move)) {
            Log.d("ASAD", "prongPlaceMove: updateGame");
            Repository.getInstance().updateGame(game);
        } else {
            // notify player of problematic move
        }
    }
}
