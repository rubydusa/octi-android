package com.example.octi.Models;

public class Pod {
    private final Game.Team team;
    private final boolean[] prongs = new boolean[8];
    private Vector2D position;

    public Pod() { team = Game.Team.RED; }
    public Pod(Game.Team team, Vector2D position) {
        this.team = team;
        this.position = position;
    }

    public Game.Team getTeam() {

        return team;
    }

    public Vector2D getPosition() {
        return position;
    }

    public boolean[] getProngs() { return prongs; }
}
