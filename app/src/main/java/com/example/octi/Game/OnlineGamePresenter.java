package com.example.octi.Game;

import android.view.View;

import com.example.octi.EloLib;
import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.GameHandler;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;
import com.example.octi.Models.User;
import com.example.octi.Models.Vector2D;
import com.google.firebase.auth.FirebaseAuth;

public class OnlineGamePresenter implements Repository.LoadGameListener, GameHandler.GameChangesListener {
    OnlineGameActivity view;
    BoardFragment board;

    GameHandler gameHandler;
    String id;

    boolean gotFirstGame = false;

    boolean isFlipped = false;

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
            Game.Team winnerTeam = game.getWinner();
            User winnerUser = game.getUserByTeam(winnerTeam);
            User loserUser = game.getOtherUser(winnerUser.getId());

            EloLib.updateUserScores(winnerUser, loserUser);
            winnerUser.setWins(winnerUser.getWins() + 1);
            loserUser.setLosses(loserUser.getLosses() + 1);

            Repository.getInstance().updateUser(winnerUser);
            Repository.getInstance().updateUser(loserUser);

            view.navigateToGameOver(game.getGameId());
            return;
        }

        if (!gotFirstGame) {
            gotFirstGame = true;
            handleFirstGame(game);
        }

        gameHandler.setGame(game);
        board.drawBoard(game);
        view.displayGameInfo(game);
    }

    public void finalizeMove() {
        gameHandler.finalizeMove();
    }

    public void prongPlaceMove(int prong) {
        if (isFlipped) {
            prong = (8 - prong) % 8;
        }

        gameHandler.prongPlaceMove(prong);
    }

    private void handleFirstGame(Game game) {
        // set up permission and flipping
        Game.Team permission;
        if (FirebaseAuth.getInstance().getUid().equals(game.getUser1().getId())) {
            permission = Game.Team.RED;
            isFlipped = false;
        } else {
            permission = Game.Team.GREEN;
            isFlipped = true;
        }

        board.setFlipped(isFlipped);
        gameHandler.setPermission(permission);
    }


    @Override
    public void realizeGameChanges(Game game) {
        Repository.getInstance().updateGame(game);
    }
}
