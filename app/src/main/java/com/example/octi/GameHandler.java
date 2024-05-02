package com.example.octi;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Fragments.CellView;
import com.example.octi.Models.Game;
import com.example.octi.Models.Jump;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

import java.util.List;

public class GameHandler implements BoardFragment.CellClickListener {
    private Game game;
    private boolean lock = false;
    private GameChangesListener listener;

    public GameHandler(Game game, GameChangesListener listener) {
        this.game = game;
        this.listener = listener;
    }

    public void unlock() {
        lock = false;
    }

    public void setGame(Game game) {
        this.game = game;
        lock = false;
    }

    public void prongPlaceMove(int prong) {
        if (game == null || lock) {
            return;
        }

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
    public void onCellClicked(CellView cellView) {
        if (game == null || lock) {
            return;
        }

        Pod clickedPod = cellView.getPod();
        Pod previouslySelectedPod = game.getCurrentGameState().getSelectedPod();
        game.getCurrentGameState().deselectPod();

        if (clickedPod != null &&
                !clickedPod.equals(previouslySelectedPod) &&
                clickedPod.getTeam() == game.getCurrentGameState().getTurn() &&
                !game.getCurrentGameState().isInMove()
        ) {
            // select pod
            game.getCurrentGameState().selectPod(clickedPod.getPosition());
        } else if (
                clickedPod == null &&
                        previouslySelectedPod != null &&
                        ((CellView) cellView).isSelected()
        ) {
            // select possible move
            game.getCurrentGameState().selectPod(previouslySelectedPod.getPosition());
            game.getCurrentGameState().advanceSelectedPod(cellView.getPosition());
        } else if (
                game.getCurrentGameState().isInMove() &&
                previouslySelectedPod != null
        ) {
            // toggle eat selection
            game.getCurrentGameState().selectPod(previouslySelectedPod.getPosition());
            List<Jump> jumps = game.getCurrentGameState().getInMoveJumps();
            for (Jump jump: jumps) {
                if (jump.getOver().equals(cellView.getPosition())) {
                    jump.setEat(!jump.isEat());
                }
            }
        }

        lock = true;
        listener.realizeGameChanges(game);
    }

    public void finalizeMove() {
        if (game == null || lock) {
            return;
        }

        if (game.getCurrentGameState().isInMove()) {
            game.getCurrentGameState().finalizeState();
        }

        lock = true;
        listener.realizeGameChanges(game);
    }

    public interface GameChangesListener {
        void realizeGameChanges(Game game);
    }
}
