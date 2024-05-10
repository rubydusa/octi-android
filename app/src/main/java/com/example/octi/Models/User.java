package com.example.octi.Models;

public class User {
    String email;
    String name;
    String id;

    int wins;

    int losses;

    int elo;

    public User() {}

    public User(String email, String name, String id) {
        this.email = email;
        this.name = name;
        this.id = id;
        wins = 0;
        losses = 0;
        elo = 1200;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public int getWins() { return wins; }

    public int getLosses() { return losses; }

    public int getElo() { return elo; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }
}
