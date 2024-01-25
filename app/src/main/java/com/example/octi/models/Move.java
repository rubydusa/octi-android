package com.example.octi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Move {

    public enum MoveType {
        @SerializedName("place")
        PLACE,
        @SerializedName("move")
        MOVE,
        @SerializedName("jump")
        JUMP,
        @SerializedName("chain")
        CHAIN
    }

    private MoveType type;
    private Vector2D target;
    private Vector2D action;
    private List<Vector2D> rest;

    public Move(MoveType type, Vector2D target, Vector2D action, List<Vector2D> rest) {
        this.type = type;
        this.target = target;
        this.action = action;
        this.rest = rest;
    }
}
