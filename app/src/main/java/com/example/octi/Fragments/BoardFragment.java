package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.octi.Models.ColoredCell;
import com.example.octi.Models.Jump;
import com.example.octi.Models.Vector2D;
import com.example.octi.R;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;

import java.util.ArrayList;
import java.util.List;

public class BoardFragment extends Fragment {

    private static final int COLUMNS = 6;
    private static final int ROWS = 7;

    private final Cell[][] cells = new Cell[ROWS][COLUMNS];

    private Game lastRenderedGame;

    private CellClickListener cellClickListener;

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
                        j,
                        i,
                        null
                );


                cells[i][j] = cell;
            }
        }

        return view;
    }

    public void setCellClickListener(CellClickListener cellClickListener) {
        this.cellClickListener = cellClickListener;
    }

    public void drawBoard(Game game) {
        lastRenderedGame = game;
        clearPreviouslySelectedCells();
        colorCells(game.getCurrentGameState().getColoredCells());
        drawPieces(game.getCurrentGameState().getPods());
        // showJumpedOver(game.getCurrentGameState().getInMoveJumps());
        processSelectedPiece(game.getCurrentGameState().getSelectedPod());
    }

    private void clearPreviouslySelectedCells() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cells[i][j].setSelection(false);
                cells[i][j].clearCell();
            }
        }
    }

    private void colorCells(List<ColoredCell> colors) {
        for (ColoredCell coloredCell : colors) {
            Vector2D coordinates = coloredCell.getPosition();
            Game.Team color = coloredCell.getColor();
            cells[coordinates.getY()][coordinates.getX()].setCellColor(color);
        }
    }

    private void drawPieces(List<Pod> pieces) {
        for (Pod piece : pieces) {
            Vector2D pos = piece.getPosition();
            int x = pos.getX();
            int y = pos.getY();
            cells[y][x].setPiece(piece);
        }
    }

    private void showJumpedOver(List<Jump> jumps) {
        for (Jump jump: jumps) {
            Vector2D pos = jump.getTo();
            int x = pos.getX();
            int y = pos.getY();
            cells[y][x].setJumpedOver(jump.isEat());
        }
    }

    private void processSelectedPiece(Pod piece) {
        if (piece == null) {
            return;
        }
        Vector2D pod = piece.getPosition();
        int x = pod.getX();
        int y = pod.getY();
        cells[y][x].setSelection(true);

        ArrayList<Vector2D> possibleNextMoves = lastRenderedGame.getCurrentGameState().nextPossibleMoves();
        for (Vector2D nextMove : possibleNextMoves) {
            cells[nextMove.getY()][nextMove.getX()].setSelection(true);
        }
    }

    private void onCellClicked(Cell cell) {
        if (cellClickListener != null) {
            cellClickListener.onCellClicked(cell);
        }
    }

    // every cell holds a piece view but it might be empty
    public class Cell {
        private boolean selected = false;

        private final FrameLayout frame;

        private final Context context;

        private final int x;
        private final int y;

        private PieceView piece;

        public Cell(
                GridLayout gridLayout,
                int x,
                int y,
                @Nullable Game.Team color
        ) {
            this.x = x;
            this.y = y;

            context = getContext();
            piece = new PieceView(context);

            // create frame layout
            frame = new FrameLayout(context);
            frame.setId(View.generateViewId());
            frame.setClickable(true);
            frame.setOnClickListener(v -> onCellClicked(this));

            // set background
            frame.setBackgroundResource(R.drawable.cell_background);
            frame.addView(piece);

            setCellColor(color);

            // add cell to grid layout
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(x, 1f);
            params.rowSpec = GridLayout.spec(y, 1f);
            gridLayout.addView(frame, params);
        }

        public Pod getPod() {
            if (piece != null) {
                return piece.getPod();
            }
            return null;
        }

        public void setCellColor(@Nullable Game.Team color) {
            LayerDrawable backgroundLayer = (LayerDrawable) frame.getBackground();
            Drawable backgroundDrawable = backgroundLayer.getDrawable(1);
            backgroundDrawable.mutate();

            int colorResourceId = getCellColorResource(color);
            int colorResource = getResources().getColor(colorResourceId, context.getTheme());

            if (backgroundDrawable instanceof ShapeDrawable) {
                ((ShapeDrawable) backgroundDrawable).getPaint().setColor(colorResource);
            } else if (backgroundDrawable instanceof GradientDrawable) {
                ((GradientDrawable) backgroundDrawable).setColor(colorResource);
            }
        }

        public void clearCell() {
            piece.clearEatSelection();
            piece.setPod(null);
        }

        public void setPiece(Pod pod) {
            piece.setPod(pod);
        }

        public void setSelection(boolean state) {
            // optimize rendering, clearing all cells each time is heavy
            if (selected == state) {
                return;
            }
            selected = state;
            if (piece.getPod() == null) {
                if (selected) {
                    frame.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    frame.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                } else {
                    frame.getBackground().clearColorFilter();
                    frame.getBackground().clearColorFilter();
                }
            } else {
                piece.setSelection(selected);
            }
        }

        public void setJumpedOver(boolean eatState) {
            piece.setEatSelection(eatState);
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

        public boolean isSelected() {
            return selected;
        }

        public Vector2D getPosition() {
            return new Vector2D(x, y);
        }
    }

    public interface CellClickListener {
        void onCellClicked(Cell cell);
    }
}