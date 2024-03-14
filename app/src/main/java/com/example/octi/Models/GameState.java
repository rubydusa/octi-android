package com.example.octi.Models;

import androidx.annotation.NonNull;

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
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(1, 5)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(2, 5)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(3, 5)));
        pods.add(new Pod(Game.Team.GREEN, new Vector2D(4, 5)));

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

    public Optional<GameState> makeMove(Move move) {
        GameState nextState = this.clone();

        return Optional.empty();
    }

    public List<ColoredCell> getColoredCells() {
        return coloredCells;
    }

    public List<Pod> getPods() {
        return pods;
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
