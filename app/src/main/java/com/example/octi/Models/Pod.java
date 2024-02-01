package com.example.octi.Models;

public class Pod {
    private final Game.Team team;
    private final boolean[] prongs = new boolean[8];

    public Pod(Game.Team team) {
        this.team = team;
    }

    public Game.Team getTeam() {

        return Game.Team.RED;
    }
}
