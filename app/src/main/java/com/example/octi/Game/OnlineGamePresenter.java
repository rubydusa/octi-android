package com.example.octi.Game;

import android.util.Log;

import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.Models.Move;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

public class OnlineGamePresenter implements Repository.LoadGameListener, BoardFragment.CellClickListener {
    OnlineGameActivity view;
    BoardFragment board;
    String id;
    Game game;

    public OnlineGamePresenter(OnlineGameActivity view, BoardFragment board, String id) {
        this.view = view;
        this.id = id;
        this.board = board;

        board.setCellClickListener(this);
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
        Pod selectedPod = game.getCurrentGameState().getSelectedPod();
        if (selectedPod == null) {
            return;
        }

        Vector2D target = selectedPod.getPosition();

        Move move = Move.createPlaceMove(target, prong);
        if (game.makeMove(move)) {
            Repository.getInstance().updateGame(game);
        } else {
            // notify player of problematic move
        }
    }

    @Override
    public void onCellClicked(BoardFragment.Cell cell) {
        Pod clickedPod = cell.getPod();
        Pod previouslySelectedPod = game.getCurrentGameState().getSelectedPod();
        game.getCurrentGameState().deselectPod();

        if (clickedPod != null &&
                clickedPod != previouslySelectedPod &&
                clickedPod.getTeam() == game.getCurrentGameState().getTurn()
        ) {
            game.getCurrentGameState().selectPod(clickedPod.getPosition());
        }

        Repository.getInstance().updateGame(game);
    }
}
