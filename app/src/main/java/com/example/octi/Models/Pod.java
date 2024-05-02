package com.example.octi.Models;

import java.util.ArrayList;

public class Pod {
    private final Game.Team team;
    private final ArrayList<Boolean> prongs = new ArrayList<>(8);
    private Vector2D position;

    public Pod() {
        team = Game.Team.RED;
        for (int i = 0; i < 8; i++) {
            prongs.add(false);
        }
    }
    public Pod(Game.Team team, Vector2D position) {
        this.team = team;
        this.position = position;

        for (int i = 0; i < 8; i++) {
            prongs.add(false);
        }
    }

    public Game.Team getTeam() {

        return team;
    }

    public Vector2D getPosition() {
        return position;
    }

    public ArrayList<Boolean> getProngs() { return prongs; }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
}
