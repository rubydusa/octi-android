package com.example.octi.Models;

import java.util.ArrayList;

public class Move {

    public Move() {}

    public Move(MoveType moveType, Vector2D target, ArrayList<Action> actions) {
        this.moveType = moveType;
        this.target = target;
        this.actions = actions;
    }

    static public Move createPlaceMove(Vector2D target, int prong) {
        ArrayList<Action> actions = new ArrayList<>();
        actions.add(new Action(prong, false));

        return new Move(MoveType.PLACE, target, actions);
    }

    public enum MoveType {
        PLACE,
        JUMP
    }

    private MoveType moveType;
    private Vector2D target;
    // the boolean represents in jumps whether to eat or not
    // in place the boolean must be false
    private ArrayList<Action> actions;

    public MoveType getMoveType() {
        return moveType;
    }

    public Vector2D getTarget() {
        return target;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }
}
