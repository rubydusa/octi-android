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

        Repository.getInstance().setLoadGameListener(this);
        Repository.getInstance().readGame(id);
    }


    @Override
    public void updateGame(Game game) {
        if (game.getStatus() == Game.Status.FINISHED) {
            view.navigateToGameOver(game.getGameId());
        }
        this.game = game;
        board.drawBoard(game);
        view.displayGameInfo(game);
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
            Repository.getInstance().updateGame(game);
        } else {
            // notify player of problematic move
        }
    }
}
