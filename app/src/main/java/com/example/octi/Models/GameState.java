package com.example.octi.Models;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameState implements Cloneable {
    private Game.Team turn;

    private int redProngCount;
    private int greenProngCount;
    private ArrayList<Pod> pods;
    private ArrayList<ColoredCell> coloredCells;

    private boolean inMove = false;
    // while constructing move, keep track of pods jumped over because it's not
    // allowed to jump a pod twice in a turn
    private ArrayList<Action> inMoveActions = new ArrayList<>();
    private Pod selectedPod = null;
    private ArrayList<Vector2D> jumpedPods = new ArrayList<>();
    private static final ArrayList<Vector2D> prong2Direciton;

    static {
        prong2Direciton = new ArrayList<>(8);
        prong2Direciton.add(new Vector2D(1, 0));
        prong2Direciton.add(new Vector2D(1, -1));
        prong2Direciton.add(new Vector2D(0, -1));
        prong2Direciton.add(new Vector2D(-1, -1));
        prong2Direciton.add(new Vector2D(-1, 0));
        prong2Direciton.add(new Vector2D(-1, 1));
        prong2Direciton.add(new Vector2D(0, 1));
        prong2Direciton.add(new Vector2D(1, 1));
    }

    // default state
    public GameState() {
        turn = Game.Team.RED;

        redProngCount = 12;
        greenProngCount = 12;

        pods = new ArrayList<>();
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(1, 1)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(2, 1)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(3, 1)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(4, 1)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(1, 5)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(2, 5)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(3, 5)));
        pods.add(new Pod(Game.Team.RED, new Vector2D(4, 5)));

        coloredCells = new ArrayList<>();
        coloredCells.add(new ColoredCell(new Vector2D(1, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(2, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(3, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(4, 1), Game.Team.GREEN));
        coloredCells.add(new ColoredCell(new Vector2D(1, 5), Game.Team.RED));
        coloredCells.add(new ColoredCell(new Vector2D(2, 5), Game.Team.RED));
        coloredCells.add(new ColoredCell(new Vector2D(3, 5), Game.Team.RED));
        coloredCells.add(new ColoredCell(new Vector2D(4, 5), Game.Team.RED));
    }

    // assumes move structure cannot be wrong, but move may be invalid.
    // so a move targeting an incorrect position is accepted, but a place move
    // without an action argument throws
    // TODO: Seems that I will not be creating a processor that processes move actions all at onces
    // so need to rewrite
    public Optional<GameState> makeMove(Move move) {
        GameState nextState = this.clone();

        if (move.getMoveType() == Move.MoveType.PLACE) {
            // no prongs
            int prongCount = nextState.getProngCount(turn);
            if (prongCount == 0) {
                return Optional.empty();
            }

            // no target pod
            Pod targetPod = nextState.findPod(move.getTarget());
            if (targetPod == null) {
                return Optional.empty();
            }

            Action placeAction = move.getActions().get(0);
            int prong = placeAction.getValue();

            // prong already exists
            if (targetPod.getProngs().get(prong)) {
                return Optional.empty();
            }

            targetPod.getProngs().set(prong, true);
            nextState.useProng(turn);
            nextState.nextTurn();

            nextState.cleanUp();
            return Optional.of(nextState);
        }

        return Optional.empty();
    }

    public List<ColoredCell> getColoredCells() {
        return coloredCells;
    }

    public List<Pod> getPods() {
        return pods;
    }

    public Game.Team getTurn() {
        return turn;
    }

    public int getProngCount(Game.Team team) {
        if (team == Game.Team.RED) {
            return redProngCount;
        } else {
            return greenProngCount;
        }
    }

    public ArrayList<Pair<Boolean, Vector2D>> getJumpedPods() {
        if (inMoveActions.size() != jumpedPods.size()) {
            throw new RuntimeException("inMoveActions and jumpedPods should be of same length");
        }

        ArrayList<Pair<Boolean, Vector2D>> result = new ArrayList<>();
        for (int i = 0; i < inMoveActions.size(); i++) {
            Pair<Boolean, Vector2D> jumpedPair = new Pair<>(
                    inMoveActions.get(i).getEat(),
                    jumpedPods.get(i)
            );

            result.add(jumpedPair);
        }

        return result;
    }

    public void selectPod(Vector2D position) {
        Pod pod = findPod(position);
        if (pod == null) {
            throw new RuntimeException("could not select pod");
        }

        if (pod.getTeam() != turn) {
            throw  new RuntimeException("selected pod of incorrect team");
        }
        selectedPod = pod;
    }

    public void deselectPod() {
        selectedPod = null;
    }

    public ArrayList<Vector2D> nextPossibleMoves() {
        Pod pod = selectedPod;
        if (pod == null) {
            throw new RuntimeException("Pod not selected");
        }

        ArrayList<Vector2D> result = new ArrayList<>();

        ArrayList<Boolean> prongs = pod.getProngs();
        for (int i = 0; i < 8; i++) {
            boolean isProng = prongs.get(i);
            if (!isProng) {
                continue;
            }

            Vector2D direction = prong2Direciton.get(i);
            Vector2D next1 = pod.getPosition().add(direction);
            Vector2D next2 = next1.add(direction);

            // jump option
            if (findPod(next1) != null && findPod(next2) == null) {
                result.add(next2);
            }

            // simple move option
            if (!inMove) {
                if (findPod(next1) == null) {
                    result.add(next1);
                }
            }
        }

        return result;
    }

    public void advanceSelectedPod(Vector2D to) {
        Pod pod = selectedPod;
        if (pod == null) {
            throw new RuntimeException("no selected pod to advance");
        }

        int prong = inferProngFromChange(pod.getPosition(), to);

        boolean hasProng = pod.getProngs().get(prong);
        if (!hasProng) {
            throw new RuntimeException("selected pod doesn't have prong");
        }

        Vector2D direction = prong2Direciton.get(prong);
        Vector2D next1 = pod.getPosition().add(direction);
        Vector2D next2 = next1.add(direction);

        inMove = true;

        // jump option
        if (findPod(next1) != null && findPod(next2) == null) {
            jumpedPods.add(next1);
            inMoveActions.add(new Action(prong, false));
            selectedPod.setPosition(next2);
        }

        // simple move option
        if (findPod(next1) == null) {
            selectedPod.setPosition(next1);
            nextTurn();
            cleanUp();
        }
    }

    @Nullable
    public Pod findPod(Vector2D position) {
        for (Pod pod:
             pods) {
            if (pod.getPosition().equals(position)) {
                return pod;
            }
        }

        return null;
    }

    private int inferProngFromChange(Vector2D base, Vector2D to) {
        for (int i = 0; i < 8; i++) {
            Vector2D direction = prong2Direciton.get(i);
            Vector2D next1 = base.add(direction);
            Vector2D next2 = next1.add(direction);

            if (to.equals(next1) || to.equals(next2)) {
                return i;
            }
        }

        throw new RuntimeException("no valid prong inferred");
    }

    private void useProng(Game.Team team) {
        if (team == Game.Team.RED) {
            redProngCount--;
        } else {
            greenProngCount--;
        }
    }

    private void nextTurn() {
        if (turn == Game.Team.RED) {
            turn = Game.Team.GREEN;
        } else {
            turn = Game.Team.RED;
        }
    }

    private void cleanUp() {
        inMove = false;
        inMoveActions.clear();
        deselectPod();
        jumpedPods.clear();
    }

    @NonNull
    @Override
    public GameState clone() {
        try {
            GameState clone = (GameState) super.clone();
            clone.pods = (ArrayList<Pod>) pods.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Pod getSelectedPod() {
        return selectedPod;
    }

    public boolean isInMove() {
        return inMove;
    }

    public void finalizeState() {
        // remove eaten pods
        nextTurn();
        cleanUp();
    }
}
