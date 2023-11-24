package com.example.octi.models;

import it.unibo.tuprolog.core.*;
import java.util.ArrayList;
import java.util.List;

public class Octi {
    private Game.Team team;
    private Vector2D position;
    private List<Vector2D> arrows;
}
/*
public class Octi {
    private final Game.Team team;
    private final Vector2D position;
    private final ArrayList<Vector2D> arrowList;

    public Octi(Struct octiData) {
        arrowList = new ArrayList<>();

        List octiArrowPrologList = octiData.get(2).castToList();

        if (!octiArrowPrologList.isEmptyList()) {
            Term[] octiArrowArray = octiArrowPrologList.getUnfoldedArray();
            for (Term octiArrowTerm : octiArrowArray) {
                Vector2D arrow = Game.parseVector2D(octiArrowTerm.castToTuple());

                arrowList.add(arrow);
            }
        }

        Tuple octiPosition = octiData.get(1).castToTuple();
        int x = octiPosition.get(0).castToInteger().getValue().toInt();
        int y = octiPosition.get(1).castToInteger().getValue().toInt();
        position = new Vector2D(x, y);

        team = Game.parseTeam(octiData.get(0).toString());
    }

    public Game.Team getTeam() {
        return team;
    }

    public Vector2D getPosition() {
        return position;
    }

    public ArrayList<Vector2D> getArrowList() {
        return arrowList;
    }
}
*/