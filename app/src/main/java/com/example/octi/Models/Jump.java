package com.example.octi.Models;

public class Jump {
    private boolean isEat;
    private Vector2D to;

    public Jump(boolean isEat, Vector2D to) {
        this.isEat = isEat;
        this.to = to;
    }

    public boolean isEat() {
        return isEat;
    }

    public void setEat(boolean eat) {
        isEat = eat;
    }

    public Vector2D getTo() {
        return to;
    }

    public void setTo(Vector2D to) {
        this.to = to;
    }
}
