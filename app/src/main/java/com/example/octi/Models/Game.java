package com.example.octi.Models;

import java.util.Optional;

// for now, user1 is always red
public class Game {
    public enum Team {
        RED,
        GREEN
    }

    public enum Status {
        NULL,
        PENDING,
        ACTIVE,
        FINISHED
    }

    private int version = 0;
    private String gameId;
    private User user1;
    private User user2;

    private Status status;

    private GameState currentGameState;

    private Team winner;

    private boolean isUser1Red = true;

    public Game() {}

    public Game(String gameId, User user1, User user2) {
        this.gameId = gameId;
        this.user1 = user1;
        this.user2 = user2;
        this.status = Status.PENDING;

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

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public int getVersion() {
        return version;
    }
    public void incrementVersion() {
        version++;
    }

    public boolean isUser1Red() {
        return isUser1Red;
    }

    public void setIsUser1Red(boolean isUser1Red) {
        this.isUser1Red = isUser1Red;
    }

    public Team getUserTeam(String userId) {
        if (!userId.equals(user1.getId()) && !userId.equals((user2.getId()))) {
            throw new RuntimeException("userId not in game");
        }

        boolean isUser1 = userId.equals(user1.getId());

        if (isUser1Red) {
            if (isUser1) {
                return Team.RED;
            } else {
                return Team.GREEN;
            }
        } else {
            if (isUser1) {
                return Team.GREEN;
            } else {
                return Team.RED;
            }
        }
    }

    public User getUserByTeam(Game.Team team) {
        if (isUser1Red) {
            if (team == Team.RED) {
                return user1;
            } else {
                return user2;
            }
        } else {
            if (team == Team.RED) {
                return user2;
            } else {
                return user1;
            }
        }
    }

    public User getOtherUser(String userId) {
        if (!userId.equals(user1.getId()) && !userId.equals((user2.getId()))) {
            throw new RuntimeException("userId not in game");
        }

        if (userId.equals(user1.getId())) {
            return user2;
        } else {
            return user1;
        }
    }
}
