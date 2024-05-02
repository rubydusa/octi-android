package com.example.octi.Game;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.GameHandler;
import com.example.octi.Models.Game;

public class LocalGamePresenter implements GameHandler.GameChangesListener {
    LocalGameActivity view;
    BoardFragment board;
    GameHandler gameHandler;
    Game game;

    public LocalGamePresenter(LocalGameActivity view, BoardFragment board) {
        this.view = view;
        this.board = board;
        game = new Game(null, null, null);
        gameHandler = new GameHandler(game, this);
        board.setCellClickListener(gameHandler);
        view.drawBoard(game);
    }

    public void finalizeMove() {
        gameHandler.finalizeMove();
    }

    public void prongPlaceMove(int prong) {
        gameHandler.prongPlaceMove(prong);
    }

    @Override
    public void realizeGameChanges(Game game) {
        view.drawBoard(game);
    }
}
