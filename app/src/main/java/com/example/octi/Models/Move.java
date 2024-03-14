package com.example.octi.Models;

import java.util.ArrayList;

public class Move {

    public Move() {}

    public enum MoveType {
        PLACE,
        JUMP
    }

    public class Action {
        private Integer value;
        private Boolean isEat;
    }

    private MoveType moveType;
    private Vector2D target;
    // the boolean represents in jumps whether to eat or not
    // in place the boolean must be false
    private ArrayList<Action> actions;
}
