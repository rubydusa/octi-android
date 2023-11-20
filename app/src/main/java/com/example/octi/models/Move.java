package com.example.octi.models;

import it.unibo.tuprolog.core.*;
import kotlin.sequences.Sequence;

import java.util.ArrayList;
import java.util.Iterator;

public class Move {
    public static final String PLACE = "place";
    // these are differentiated in prolog for practical reasons but
    // they are all MOVE
    public static final String MOVE = "move";
    public static final String JUMP = "jump";
    public static final String CHAIN = "chain";
    enum MoveType {
        PLACE,
        MOVE,
    }

    private final Struct originalMove;
    private final MoveType moveType;
    private final Vector2D target;
    private final Vector2D action;  // for place, this is the arrow. for move, this is the first direction
    private final ArrayList<Vector2D> rest;  // only relevant for chain moves

    public Move(Struct move) {
        originalMove = move.freshCopy();

        String stringMoveType = move.get(0).toString();

        rest = new ArrayList<>();

        switch (stringMoveType) {
            case PLACE:
                moveType = MoveType.PLACE;
                target = Game.parseVector2D(move.get(1).castToTuple());
                action = Game.parseVector2D(move.get(2).castToTuple());
                break;
            case MOVE:
            case JUMP:
                moveType = MoveType.MOVE;
                target = Game.parseVector2D(move.get(1).castToTuple());
                action = Game.parseVector2D(move.get(2).castToTuple());
                break;
            case CHAIN:
                moveType = MoveType.MOVE;
                // list MUST have at least 3 (ensured by prolog code)
                Iterator<Term> positions = move.get(1).castToList().getUnfoldedSequence().iterator();
                target = Game.parseVector2D(positions.next().castToTuple());
                action = Game.parseVector2D(positions.next().castToTuple());
                positions.forEachRemaining((Term t) -> {
                    rest.add(Game.parseVector2D(t.castToTuple()));
                });
                break;
            default:
                throw new IllegalArgumentException();
        }
    }

    public Struct getOriginalMove() {
        return originalMove;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public Vector2D getTarget() {
        return target;
    }

    public Vector2D getAction() {
        return action;
    }

    public ArrayList<Vector2D> getRest() {
        return rest;
    }
}
