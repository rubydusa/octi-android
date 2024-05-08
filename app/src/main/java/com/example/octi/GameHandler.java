package com.example.octi;

import androidx.annotation.Nullable;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Fragments.CellView;
import com.example.octi.Models.Game;
import com.example.octi.Models.Jump;
import com.example.octi.Models.Pod;
import com.example.octi.Models.Vector2D;

import java.util.List;

public class GameHandler implements BoardFragment.CellClickListener {
    private Game game;
    // allow only specific team to play through game handler
    @Nullable
    private Game.Team permission;
    private int lastHighestVersion = -1;
    private GameChangesListener listener;
    private boolean lock = false;

    public GameHandler(Game game, Game.Team permission, GameChangesListener listener) {
        this.game = game;
        this.listener = listener;
    }

    public void setGame(Game game) {
        if (game.getVersion() <= lastHighestVersion) {
            return;
        }
        lastHighestVersion = game.getVersion();
        this.game = game;
    }

    public void setPermission(@Nullable Game.Team permission) {
        this.permission = permission;
    }

    public void prongPlaceMove(int prong) {
        if (game == null || lock || !checkPermission()) {
            return;
        }

        Pod selectedPod = game.getCurrentGameState().getSelectedPod();
        if (selectedPod == null) {
            return;
        }

        Vector2D target = selectedPod.getPosition();
        try {
            game.getCurrentGameState().placeProng(target, prong);
            startRealizeGameChanges(game);
        } catch (RuntimeException e) {
            // notify error
        }
    }

    @Override
    public void onCellClicked(CellView cellView) {
        if (game == null || lock|| !checkPermission()) {
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

        startRealizeGameChanges(game);
    }

    public void finalizeMove() {
        if (game == null || lock || !checkPermission()) {
            return;
        }

        if (game.getCurrentGameState().isInMove()) {
            game.getCurrentGameState().finalizeState();

            startRealizeGameChanges(game);
        }
    }

    private void startRealizeGameChanges(Game game) {
        game.incrementVersion();
        Game.Team winner = game.getCurrentGameState().getWinner();
        if (winner != null) {
            game.setWinner(winner);
            game.setStatus(Game.Status.FINISHED);
        }
        listener.realizeGameChanges(game);
    }

    private boolean checkPermission() {
        return permission == null || permission == game.getCurrentGameState().getTurn();
    }

    public void lock() {
        lock = true;
    }

    public interface GameChangesListener {
        void realizeGameChanges(Game game);
    }
}
