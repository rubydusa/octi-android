package com.example.octi.models;

import android.content.res.Resources;

import com.example.octi.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import it.unibo.tuprolog.core.Struct;
import it.unibo.tuprolog.core.Term;
import it.unibo.tuprolog.core.TermFormatter;
import it.unibo.tuprolog.core.Var;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.SolverFactory;
import it.unibo.tuprolog.solve.classic.ClassicSolverFactory;
import it.unibo.tuprolog.theory.Theory;
import it.unibo.tuprolog.theory.parsing.ClausesParser;
import kotlin.Pair;

// All game instances share a single static solver.
public class Game {
    public enum Team {
        @SerializedName("red")
        RED,
        @SerializedName("green")
        GREEN
    }

    // names of prolog predicates;
    public static final String BASE_GAME = "base_game";
    public static final String JSON = "json";

    // singletones
    static private Gson gson;
    static private Solver solver;

    // fields
    private GameState gameState;

    // should ideally be retrieved from prolog, but because no custom game modes
    // initiating from code now
    private final ArrayList<Pair<Vector2D, Game.Team>> cellColors;

    public Game(Resources res) {
        initializeGson();
        initializeSolver(res);

        cellColors = defaultCellColors();

        // variable names are only descriptive and can be changed
        Var baseGameVar = Var.of("BaseGame");
        Var jsonRepresentationVar = Var.of("JsonRepresentation");

        // get prolog-representation of initial game state
        Solution baseGameSolution = solver.solveOnce(
                Struct.of(BASE_GAME, baseGameVar)
        );
        Struct prologData = baseGameVar.get(baseGameSolution.getSubstitution()).castToStruct();

        // transform into json-representation of data through prolog
        Struct jsonRepresentationQuery = Struct.of(
                JSON,
                baseGameVar.get(baseGameSolution.getSubstitution()),
                jsonRepresentationVar);
        Solution jsonRepresentationSolution = solver.solveOnce(jsonRepresentationQuery);
        Term json = jsonRepresentationVar.get(jsonRepresentationSolution.getSubstitution());

        // no built-in option for enclosing atoms with quotes, so regex
        String jsonString = TermFormatter
                .prettyExpressions(solver.getOperators())
                .format(json)
                .replaceAll("([a-zA-Z_][a-zA-Z0-9_]*)", "\"$1\"");

        GameState.Data gameStateData = gson.fromJson(jsonString, GameState.Data.class);
        gameState = new GameState(gameStateData, prologData);
    }

    public ArrayList<Pair<Vector2D, Team>> getCellColors() {
        return cellColors;
    }

    public GameState getGameState() { return gameState; }

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

    private static ArrayList<Pair<Vector2D, Game.Team>> defaultCellColors() {
        ArrayList<Pair<Vector2D, Game.Team>> array = new ArrayList<>();
        array.add(new Pair<>(new Vector2D(1, 5), Team.RED));
        array.add(new Pair<>(new Vector2D(2, 5), Team.RED));
        array.add(new Pair<>(new Vector2D(3, 5), Team.RED));
        array.add(new Pair<>(new Vector2D(4, 5), Team.RED));
        array.add(new Pair<>(new Vector2D(1, 1), Team.GREEN));
        array.add(new Pair<>(new Vector2D(2, 1), Team.GREEN));
        array.add(new Pair<>(new Vector2D(3, 1), Team.GREEN));
        array.add(new Pair<>(new Vector2D(4, 1), Team.GREEN));

        return array;
    }

    private static void initializeGson() {
        if (gson == null) {
            gson = new Gson();
        }
    }
}