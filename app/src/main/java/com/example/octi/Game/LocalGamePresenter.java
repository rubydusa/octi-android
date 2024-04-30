package com.example.octi.Game;

import android.util.Log;

import com.example.octi.Firebase.Repository;
import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.Models.Move;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

public class LocalGamePresenter implements BoardFragment.CellClickListener {
    LocalGameActivity view;
    BoardFragment board;
    Game game;

    public LocalGamePresenter(LocalGameActivity view, BoardFragment board) {
        this.view = view;
        this.board = board;
        game = new Game(null, null, null);
        board.setCellClickListener(this);
        view.drawBoard(game);
    }

    public void prongPlaceMove(int prong) {
        Pod selectedPod = game.getCurrentGameState().getSelectedPod();
        if (selectedPod == null) {
            return;
        }

        Vector2D target = selectedPod.getPosition();

        Move move = Move.createPlaceMove(target, prong);
        if (game.makeMove(move)) {
            view.drawBoard(game);
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

        view.drawBoard(game);
    }
}
