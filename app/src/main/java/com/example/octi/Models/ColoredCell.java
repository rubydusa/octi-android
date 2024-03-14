package com.example.octi.Models;

public class ColoredCell {
    private Vector2D position;
    private Game.Team color;

    public ColoredCell() {}

    public ColoredCell(Vector2D position, Game.Team color) {
        this.position = position;
        this.color = color;
    }

    public Vector2D getPosition() {
        return position;
    }

    public Game.Team getColor() {
        return color;
    }
}
