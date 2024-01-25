package com.example.octi.models;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;

public class GameState implements Cloneable {
    private Game.Team turn;
    private TreeMap<Game.Team, Integer> prongCounts;
    private HashMap<Vector2D, Pod> pods;  // (0, 0) is top left

    // default state
    public GameState() {
        turn = Game.Team.RED;

        prongCounts = new TreeMap<>();
        prongCounts.put(Game.Team.RED, 12);
        prongCounts.put(Game.Team.GREEN, 12);

        pods = new HashMap<>();
        pods.put(new Vector2D(1, 1), new Pod(Game.Team.GREEN));
        pods.put(new Vector2D(2, 1), new Pod(Game.Team.GREEN));
        pods.put(new Vector2D(3, 1), new Pod(Game.Team.GREEN));
        pods.put(new Vector2D(4, 1), new Pod(Game.Team.GREEN));
        pods.put(new Vector2D(1, 5), new Pod(Game.Team.RED));
        pods.put(new Vector2D(2, 5), new Pod(Game.Team.RED));
        pods.put(new Vector2D(3, 5), new Pod(Game.Team.RED));
        pods.put(new Vector2D(4, 5), new Pod(Game.Team.RED));
    }

    public Optional<GameState> makeMove(Move move) {
        GameState nextState = this.clone();

        return Optional.empty();
    }

    @NonNull
    @Override
    public GameState clone() {
        try {
            GameState clone = (GameState) super.clone();
            clone.turn = turn;
            clone.prongCounts = (TreeMap<Game.Team, Integer>) prongCounts.clone();
            clone.pods = (HashMap<Vector2D, Pod>) pods.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
