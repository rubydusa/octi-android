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

    private MoveType type;
    private Vector2D target;
    private Vector2D action;
    private List<Vector2D> rest;
}
