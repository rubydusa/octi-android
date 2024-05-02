package com.example.octi.Game;

import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.GameHandler;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;
import com.google.firebase.auth.FirebaseAuth;

public class OnlineGamePresenter implements Repository.LoadGameListener, GameHandler.GameChangesListener {
    OnlineGameActivity view;
    BoardFragment board;

    GameHandler gameHandler;
    String id;

    public OnlineGamePresenter(OnlineGameActivity view, BoardFragment board, String id) {
        this.view = view;
        this.id = id;
        this.board = board;

        // game locked until loaded
        gameHandler = new GameHandler(null, null, this);
        board.setCellClickListener(gameHandler);
        Repository.getInstance().setLoadGameListener(this);
        Repository.getInstance().readGame(id);
    }


    @Override
    public void updateGame(Game game) {
        if (game.getStatus() == Game.Status.FINISHED) {
            view.navigateToGameOver(game.getGameId());
            return;
        }

        if (!gameHandler.isPermissioned()) {
            Game.Team permission;
            if (FirebaseAuth.getInstance().getUid().equals(game.getUser1().getId())) {
                permission = Game.Team.RED;
            } else {
                permission = Game.Team.GREEN;
            }

            gameHandler.setPermission(permission);
        }

        gameHandler.setGame(game);
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
