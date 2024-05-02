package com.example.octi.Models;

public class Jump {
    private boolean isEat;
    private Vector2D over;

    public Jump() {}

    public Jump(boolean isEat, Vector2D over) {
        this.isEat = isEat;
        this.over = over;
    }

    public boolean isEat() {
        return isEat;
    }

    public void setEat(boolean eat) {
        isEat = eat;
    }

    public Vector2D getOver() {
        return over;
    }

    public void setOver(Vector2D over) {
        this.over = over;
    }
}
