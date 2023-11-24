package com.example.octi.models;

import it.unibo.tuprolog.core.*;
import java.util.List;

// It's important to keep track of the original prolog representation
// of data for future queries.
public class GameState {
    private final Data data;
    private final Struct prologData;

    public GameState(Data data, Struct prologData) {
        this.data = data;
        this.prologData = prologData;
    }

    public Data getData() {
        return data;
    }

    public Struct getPrologData() {
        return prologData;
    }

    public class Data {
        private Game.Team team;
        private List<ArrowCount> arrows;
        private List<Octi> octis;

        public Game.Team getTeam() {
            return team;
        }

        public List<ArrowCount> getArrows() {
            return arrows;
        }

        public List<Octi> getOctis() {
            return octis;
        }
    }
}