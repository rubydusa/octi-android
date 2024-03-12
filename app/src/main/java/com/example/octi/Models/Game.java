package com.example.octi.Models;

public class Game {
    public enum Team {
        RED,
        GREEN
    }

    public enum Status {
        PENDING,
        ACTIVE,
        FINISHED
    }

    private String gameId;
    private String user1Id;
    private String user2Id;

    private Status status;

    // private GameState currentGameState;

    public Game() {}

    public Game(String gameId, String user1Id, String user2Id) {
        this.gameId = gameId;
        this.user1Id = user1Id;
        this.user2Id = user2Id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
