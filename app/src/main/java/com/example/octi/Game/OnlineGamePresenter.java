package com.example.octi.Game;

import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.GameHandler;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

public class OnlineGamePresenter implements Repository.LoadGameListener, GameHandler.GameChangesListener {
    OnlineGameActivity view;
    BoardFragment board;

    GameHandler gameHandler;
    String id;

    public OnlineGamePresenter(OnlineGameActivity view, BoardFragment board, String id) {
        this.view = view;
        this.id = id;
        this.board = board;

        gameHandler = new GameHandler(null, this);
        board.setCellClickListener(gameHandler);
        Repository.getInstance().setLoadGameListener(this);
        Repository.getInstance().readGame(id);
    }


    @Override
    public void updateGame(Game game) {
        if (game.getStatus() == Game.Status.FINISHED) {
            view.navigateToGameOver(game.getGameId());
        }

        gameHandler.setGame(game);
        gameHandler.unlock();
        board.drawBoard(game);
        view.displayGameInfo(game);
    }

    public void finalizeMove() {
        gameHandler.finalizeMove();
    }

    public void prongPlaceMove(int prong) {
        gameHandler.prongPlaceMove(prong);
    }

    @Override
    public void realizeGameChanges(Game game) {
        Repository.getInstance().updateGame(game);
    }
}
