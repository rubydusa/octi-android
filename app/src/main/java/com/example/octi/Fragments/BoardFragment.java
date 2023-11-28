package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.octi.R;
import com.example.octi.models.Game;

import java.util.Optional;

public class BoardFragment extends Fragment {

    private static final int COLUMNS = 6;
    private static final int ROWS = 7;

    private final Cell[][] cells = new Cell[ROWS][COLUMNS];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        
        GridLayout gridLayout = view.findViewById(R.id.board_grid);
        gridLayout.setColumnCount(COLUMNS);
        gridLayout.setRowCount(ROWS);

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Cell cell = new Cell(
                        gridLayout,
                        i,
                        j,
                        null
                );


                cells[i][j] = cell;
            }
        }

        return view;
    }

    /*
    private void addPieceToCell(int row, int col) {
        FrameLayout cell = cellViews[row][col];
        if (cell != null) {
            PieceFragment pieceFragment = new PieceFragment();

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(cell.getId(), pieceFragment);
            fragmentTransaction.commit();
        }
    }
    */

    private void onCellClicked(Cell cell) {

    }

    private class Cell {
        private boolean selected = false;

        private final FrameLayout frame;

        private final int x;
        private final int y;
        @Nullable
        private PieceFragment piece;

        public Cell(
                GridLayout gridLayout,
                int x,
                int y,
                @Nullable Game.Team color
        ) {
            this.x = x;
            this.y = y;

            Context context = getContext();

            // create frame layout
            frame = new FrameLayout(context);
            frame.setId(View.generateViewId());
            frame.setClickable(true);
            frame.setOnClickListener(v -> onCellClicked(this));

            // set background
            frame.setBackgroundResource(R.drawable.cell_background);

            LayerDrawable backgroundLayer = (LayerDrawable) frame.getBackground();
            Drawable backgroundDrawable = backgroundLayer.getDrawable(1);
            backgroundDrawable.mutate();

            // set color of cell
            int colorResourceId = getCellColorResource(color);
            int colorResource = getResources().getColor(colorResourceId, context.getTheme());

            if (backgroundDrawable instanceof ShapeDrawable) {
                ((ShapeDrawable) backgroundDrawable).getPaint().setColor(colorResource);
            } else if (backgroundDrawable instanceof GradientDrawable) {
                ((GradientDrawable) backgroundDrawable).setColor(colorResource);
            }

            // add cell to grid layout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(y, 1f);
            params.rowSpec = GridLayout.spec(x, 1f);
            gridLayout.addView(frame, params);
        }

        private int getCellColorResource(@Nullable Game.Team color) {
            if (color == null) {
                return R.color.cell_background;
            } else {
                switch (color) {
                    case RED:
                        return R.color.light_team_red;
                    case GREEN:
                        return R.color.light_team_green;
                }
            }

            // TODO: Clarify in code this is unreachable
            return 0;
        }
    }
}