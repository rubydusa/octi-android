package com.example.octi;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.Models.Jump;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

import java.util.List;

public class GameHandler implements BoardFragment.CellClickListener {
    private Game game;
    private GameChangesListener listener;

    public GameHandler(Game game, GameChangesListener listener) {
        this.game = game;
        this.listener = listener;
    }

    public void prongPlaceMove(int prong) {
        Pod selectedPod = game.getCurrentGameState().getSelectedPod();
        if (selectedPod == null) {
            return;
        }

        Vector2D target = selectedPod.getPosition();
        try {
            game.getCurrentGameState().placeProng(target, prong);
            listener.realizeGameChanges(game);
        } catch (RuntimeException e) {
            // notify error
        }
    }

    @Override
    public void onCellClicked(BoardFragment.Cell cell) {
        Pod clickedPod = cell.getPod();
        Pod previouslySelectedPod = game.getCurrentGameState().getSelectedPod();
        game.getCurrentGameState().deselectPod();

        if (clickedPod != null &&
                clickedPod != previouslySelectedPod &&
                clickedPod.getTeam() == game.getCurrentGameState().getTurn() &&
                !game.getCurrentGameState().isInMove()
        ) {
            // select pod
            game.getCurrentGameState().selectPod(clickedPod.getPosition());
        } else if (
                clickedPod == null &&
                        previouslySelectedPod != null &&
                        cell.isSelected()
        ) {
            // select possible move
            game.getCurrentGameState().selectPod(previouslySelectedPod.getPosition());
            game.getCurrentGameState().advanceSelectedPod(cell.getPosition());
        } else if (game.getCurrentGameState().isInMove()) {
            // togge eat selection
            List<Jump> jumps = game.getCurrentGameState().getInMoveJumps();
            for (Jump jump: jumps) {
                if (jump.getOver().equals(cell.getPosition())) {
                    jump.setEat(!jump.isEat());
                }
            }
        }

        if (game.getCurrentGameState().isInMove()) {
            game.getCurrentGameState().selectPod(previouslySelectedPod.getPosition());
        }

        listener.realizeGameChanges(game);
    }

    public void finalizeMove() {
        if (game.getCurrentGameState().isInMove()) {
            game.getCurrentGameState().finalizeState();
        }

        listener.realizeGameChanges(game);
    }

    public interface GameChangesListener {
        void realizeGameChanges(Game game);
    }
}
