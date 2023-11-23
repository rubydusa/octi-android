package com.example.octi.models;

import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.SolverFactory;
import it.unibo.tuprolog.solve.classic.ClassicSolverFactory;
import it.unibo.tuprolog.theory.Theory;
import it.unibo.tuprolog.theory.parsing.ClausesParser;

import android.content.res.Resources;
import android.util.Log;

import com.example.octi.R;

import java.io.InputStream;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

// All game instances share a single static solver.
public class Game {
    public static final String BASE_GAME = "base_game";
    public static final String RED = "red";

    public enum Team {
        RED,
        GREEN
    }
    static private Solver solver;

    private GameState gameState;

    public Game(Resources res) {
        initializeSolver(res);

        Var baseGameVar = Var.of("BaseGame");
        Var jsonRepresentationVar = Var.of("JsonRepresentation");

        Solution baseGameSolution = solver.solveOnce(
                Struct.of(BASE_GAME, baseGameVar)
        );

        Struct jsonRepresentationQuery = Struct.of(
                "json",
                baseGameVar.get(baseGameSolution.getSubstitution()),
                jsonRepresentationVar);

        Solution jsonRepresentationSolution = solver.solveOnce(jsonRepresentationQuery);

        Term json = jsonRepresentationVar.get(jsonRepresentationSolution.getSubstitution());

        String result = TermFormatter
                .prettyExpressions(solver.getOperators())
                .format(json);

        Log.d("fuck", result);

        // gameState = new GameState(s.getSubstitution().getByName("X").castToStruct());
    }

    // Parse functions for game-logic global concepts that
    // don't deserve a class of their own

    public static Team parseTeam(String s) {
        return Objects.equals(s, RED) ? Team.RED : Team.GREEN;
    }

    // (X, Y) (only ints)
    public static Vector2D parseVector2D(Tuple tuple) {
        int x = tuple.get(0).castToInteger().getValue().toInt();
        int y = tuple.get(1).castToInteger().getValue().toInt();

        return new Vector2D(x, y);
    }

    // Compound term with a minus functor -(X, Y)
    public static Map.Entry<Game.Team, java.lang.Integer> parseTeamArrowCount(Struct struct) {
        return new AbstractMap.SimpleEntry<>(
                Game.parseTeam(struct.get(0).toString()),
                struct.get(1).castToInteger().getValue().toInt()
        );
    }

    private static void initializeSolver(Resources res) {
        if (solver == null) {
            InputStream stream = res.openRawResource(R.raw.octi);
            Scanner s = new Scanner(stream).useDelimiter("\\A");

            String theorySource = s.next();

            Theory theory = ClausesParser
                    .withDefaultOperators()
                    .parseTheory(theorySource);

            SolverFactory factory = ClassicSolverFactory.INSTANCE;

            solver = factory.solverWithDefaultBuiltins(
                factory.getDefaultUnificator(),
                factory.getDefaultRuntime(),
                factory.getDefaultFlags(),
                theory,
                factory.getDefaultDynamicKb(),
                factory.getDefaultInputChannel(),
                factory.getDefaultErrorChannel(),
                factory.getDefaultErrorChannel(),
                factory.getDefaultWarningsChannel()
            );
        }
    }
}