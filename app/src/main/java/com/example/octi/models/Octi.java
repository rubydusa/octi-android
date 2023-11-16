package com.example.octi.models;

import java.util.ArrayList;

public class Octi {
    private final Game.Team team;
    private final Vector2D position;
    private final ArrayList<Vector2D> arrowList;

    public Octi(Game.Team team, Vector2D position, ArrayList<Vector2D> arrowList) {
        this.team = team;
        this.position = position;
        this.arrowList = arrowList;
    }

    public Game.Team getTeam() {
        return team;
    }

    public Vector2D getPosition() {
        return position;
    }

    public ArrayList<Vector2D> getArrowList() {
        return arrowList;
    }
}
