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
    private User user1;
    private User user2;

    private Status status;

    private GameState currentGameState;

    public Game() {}

    public Game(String gameId, User user1, User user2) {
        this.gameId = gameId;
        this.user1 = user1;
        this.user2 = user2;

        currentGameState = new GameState();
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }
}
