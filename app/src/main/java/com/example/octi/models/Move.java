package com.example.octi.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Move {

    enum MoveType {
        @SerializedName("place")
        PLACE,
        @SerializedName("move")
        MOVE,
        @SerializedName("jump")
        JUMP,
        @SerializedName("chain")
        CHAIN
    }

    public MoveType type;
    public Vector2D target;
    public Vector2D action;
    public List<Vector2D> rest;
}
