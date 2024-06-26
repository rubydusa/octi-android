package com.example.octi.UI.Game;

import com.example.octi.Helpers.EloLib;
import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Helpers.GameHandler;
import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.google.firebase.auth.FirebaseAuth;

public class OnlineGamePresenter implements Repository.LoadGameListener, GameHandler.GameChangesListener {
    private OnlineGameActivity view;
    private BoardFragment board;

    private GameHandler gameHandler;

    private boolean gotFirstGame = false;

    private boolean isFlipped = false;

    public OnlineGamePresenter(OnlineGameActivity view, BoardFragment board, String id) {
        this.view = view;
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
        view.updateGameUI(game);
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
        if (FirebaseAuth.getInstance().getUid().equals(game.getUserByTeam(Game.Team.RED).getId())) {
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
