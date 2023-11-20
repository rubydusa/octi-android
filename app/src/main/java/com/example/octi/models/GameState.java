package com.example.octi.models;

import it.unibo.tuprolog.core.*;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameState {
    private final Struct originalGameState;
    private final Game.Team turn;
    private final Map<Game.Team, java.lang.Integer> arrows;
    private final ArrayList<Octi> octis;

    public GameState(Struct gameState) {
        originalGameState = gameState.freshCopy();
        turn = Game.parseTeam(gameState.getArgAt(0).castToAtom().toString());

        // [
        // -(TEAM1, COUNT1),
        // -(TEAM2, COUNT2)
        // ]
        Term[] arrowArray = gameState.getArgAt(1).castToList().getUnfoldedArray();
        Struct firstTeam = arrowArray[0].castToStruct();
        Struct secondTeam = arrowArray[1].castToStruct();

        arrows = new HashMap<>();

        Map.Entry<Game.Team, java.lang.Integer> firstTeamEntry = Game.parseTeamArrowCount(firstTeam);
        Map.Entry<Game.Team, java.lang.Integer> secondTeamEntry = Game.parseTeamArrowCount(secondTeam);

        arrows.put(firstTeamEntry.getKey(), firstTeamEntry.getValue());
        arrows.put(secondTeamEntry.getKey(), secondTeamEntry.getValue());

        octis = new ArrayList<>();
        Term[] octiArray = gameState.get(2).castToList().getUnfoldedArray();

        for (Term octiTerm : octiArray) {
            // 2PKT is dumb and includes empty list as the last element
            if (octiTerm.isEmptyList()) {
                continue;
            }

            octis.add(new Octi(octiTerm.castToStruct()));
        }
    }

    public Struct getOriginalGameState() {
        return originalGameState;
    }

    public Game.Team getTurn() {
        return turn;
    }

    public Map<Game.Team, Integer> getArrows() {
        return arrows;
    }

    public ArrayList<Octi> getOctis() {
        return octis;
    }
}