package com.example.octi.Game;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.GameHandler;
import com.example.octi.Models.Game;

public class LocalGamePresenter implements GameHandler.GameChangesListener {
    LocalGameActivity view;
    BoardFragment board;
    GameHandler gameHandler;

    public LocalGamePresenter(LocalGameActivity view, BoardFragment board) {
        this.view = view;
        this.board = board;
        Game game = new Game(null, null, null);
        gameHandler = new GameHandler(game, null,this);
        board.setCellClickListener(gameHandler);
        board.drawBoard(game);
    }

    public void finalizeMove() {
        gameHandler.finalizeMove();
    }

    public void prongPlaceMove(int prong) {
        gameHandler.prongPlaceMove(prong);
    }

    @Override
    public void realizeGameChanges(Game game) {
        Game.Team winner = game.getCurrentGameState().getWinner();
        if (winner != null) {
            view.displayWinner(winner);
            gameHandler.lock();
        }
        view.updateGameUI(game);
        board.drawBoard(game);
    }
}
