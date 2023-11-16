package com.example.octi;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

/*
import it.unibo.tuprolog.core.*;
import it.unibo.tuprolog.solve.Solution;
import it.unibo.tuprolog.solve.SolverFactory;
import it.unibo.tuprolog.theory.parsing.ClausesParser;
import it.unibo.tuprolog.theory.Theory;
import it.unibo.tuprolog.solve.Solver;
import it.unibo.tuprolog.solve.classic.ClassicSolverFactory;
import kotlin.sequences.Sequence;

import android.util.Log;

        Theory t = ClausesParser
                .withDefaultOperators()
                .parseTheory("fact(x).");

        SolverFactory factory = ClassicSolverFactory.INSTANCE;

        Solver solver = factory.solverWithDefaultBuiltins(
                factory.getDefaultUnificator(),
                factory.getDefaultRuntime(),
                factory.getDefaultFlags(),
                t,
                factory.getDefaultDynamicKb(),
                factory.getDefaultInputChannel(),
                factory.getDefaultErrorChannel(),
                factory.getDefaultErrorChannel(),
                factory.getDefaultWarningsChannel()
        );

        Sequence<Solution> solutions = solver.solve(Struct.of("fact", Var.of("X")));

        Solution s = solutions.iterator().next();

        Log.d("fuck", Boolean.toString(s.isYes()));
 */