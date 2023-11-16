package com.example.octi.models;

public class Vector2D {
    private Integer x;
    private Integer y;

    public Vector2D(Integer x, Integer y) {
        setX(x);
        setY(y);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}
