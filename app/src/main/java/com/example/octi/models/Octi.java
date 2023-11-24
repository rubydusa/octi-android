package com.example.octi.models;

import java.util.List;

public class Octi {
    private Game.Team team;
    private Vector2D position;
    private List<Vector2D> arrows;

    public Game.Team getTeam() {
        return team;
    }

    public Vector2D getPosition() {
        return position;
    }

    public List<Vector2D> getArrows() {
        return arrows;
    }
}