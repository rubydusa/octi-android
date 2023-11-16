package com.example.octi.models;

import android.util.Log;

import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.SolverFactory;
import it.unibo.tuprolog.solve.classic.ClassicSolverFactory;
import it.unibo.tuprolog.theory.Theory;
import it.unibo.tuprolog.theory.parsing.ClausesParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private Game.Team turn;
    private Map<Game.Team, java.lang.Integer> arrows;
    private ArrayList<Octi> octis;

    public GameState(Struct gameState) {
        turn = Game.teamFromString(gameState.getArgAt(0).castToAtom().toString());

        Term[] arrowArray = gameState.getArgAt(1).castToList().getUnfoldedArray();
        Struct firstTeam = arrowArray[0].castToStruct();
        Struct secondTeam = arrowArray[1].castToStruct();

        arrows = new HashMap<>();

        arrows.put(
                Game.teamFromString(firstTeam.get(0).toString()),
                firstTeam.get(1).castToInteger().getValue().toInt()
        );
        arrows.put(
                Game.teamFromString(secondTeam.get(0).toString()),
                secondTeam.get(1).castToInteger().getValue().toInt()
        );

        Log.d("fuck", "this");

        octis = new ArrayList<>();
        Term[] octiArray = gameState.get(2).castToList().getUnfoldedArray();

        for (Term octiTerm : octiArray) {
            if (octiTerm.isEmptyList()) {
                continue;
            }
            Struct octiData = octiTerm.castToStruct();

            ArrayList<Vector2D> octiArrowList = new ArrayList<>();

            List octiArrowPrologList = octiData.get(2).castToList();

            if (!octiArrowPrologList.isEmptyList()) {
                Term[] octiArrowArray = octiArrowPrologList.getUnfoldedArray();
                for (Term octiArrowTerm : octiArrowArray) {
                    Log.d("fuck", "fucking");
                    Tuple octiArrowTuple = octiArrowTerm.castToTuple();
                    Log.d("fuck", "shit");
                    int x = octiArrowTuple.get(0).castToInteger().getValue().toInt();
                    int y = octiArrowTuple.get(1).castToInteger().getValue().toInt();

                    octiArrowList.add(new Vector2D(x, y));
                }
            }

            Tuple octiPosition = octiData.get(1).castToTuple();
            int x = octiPosition.get(0).castToInteger().getValue().toInt();
            int y = octiPosition.get(1).castToInteger().getValue().toInt();
            Vector2D position = new Vector2D(x, y);

            octis.add(new Octi(
                    Game.teamFromString(octiData.get(0).toString()),
                    position,
                    octiArrowList
            ));
        }
    }
}