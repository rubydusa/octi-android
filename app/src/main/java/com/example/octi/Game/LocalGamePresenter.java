package com.example.octi.Game;

import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.Models.Move;
import com.example.octi.Models.Vector2D;

public class LocalGamePresenter {
    LocalGameActivity view;
    BoardFragment board;
    Game game;

    public LocalGamePresenter(LocalGameActivity view, BoardFragment board) {
        this.view = view;
        this.board = board;
        game = new Game(null, null, null);
        view.drawBoard(game);
    }

    public void prongPlaceMove(int prong) {
        BoardFragment.Cell selectedCell = board.getSelectedCell();
        if (selectedCell == null) {
            return;
        }

        // the xs and ys are switched ui wise and I can't bother to change it
        int y = selectedCell.getX();
        int x = selectedCell.getY();

        Vector2D target = new Vector2D(x, y);

        Move move = Move.createPlaceMove(target, prong);
        if (game.makeMove(move)) {
            view.drawBoard(game);
        } else {
            // notify player of problematic move
        }
    }
}
