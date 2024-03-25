package com.example.octi.Models;

import android.graphics.Path;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import kotlin.Pair;

public class GameState implements Cloneable {
    private Game.Team turn;

    private int redProngCount;
    private int greenProngCount;
    private ArrayList<Pod> pods;
    private ArrayList<ColoredCell> coloredCells;

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

    public int getProngCount(Game.Team team) {
        if (team == Game.Team.RED) {
            return redProngCount;
        } else {
            return greenProngCount;
        }
    }

    @Nullable
    private Pod findPod(Vector2D position) {
        for (Pod pod:
             pods) {
            if (pod.getPosition().equals(position)) {
                return pod;
            }
        }

        return null;
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
}
