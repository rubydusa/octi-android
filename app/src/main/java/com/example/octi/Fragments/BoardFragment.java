package com.example.octi.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
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

import com.example.octi.Models.ColoredCell;
import com.example.octi.Models.Jump;
import com.example.octi.Models.Vector2D;
import com.example.octi.R;
import com.example.octi.Models.Game;
import com.example.octi.Models.Pod;

import java.util.ArrayList;
import java.util.List;

public class BoardFragment extends Fragment implements CellView.CellClickListener {

    private static final int COLUMNS = 6;
    private static final int ROWS = 7;

    // first index is y second is x
    private final CellView[][] cells = new CellView[ROWS][COLUMNS];

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
                Vector2D position = new Vector2D(j, i);

                CellView cellView = new CellView(getContext(), gridLayout, position, this);
                cells[i][j] = cellView;
            }
        }

        return view;
    }

    public void setCellClickListener(CellClickListener cellClickListener) {
        this.cellClickListener = cellClickListener;
    }

    public void drawBoard(Game game) {
        lastRenderedGame = game;
        clearCells();
        colorCells(game.getCurrentGameState().getColoredCells());
        drawPieces(game.getCurrentGameState().getPods());
        showJumpedOver(game.getCurrentGameState().getInMoveJumps());
        processSelectedPiece(game.getCurrentGameState().getSelectedPod());
    }

    private void clearCells() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                cells[i][j].clearCell();
            }
        }
    }

    private void colorCells(List<ColoredCell> colors) {
        for (ColoredCell coloredCell : colors) {
            Vector2D coordinates = coloredCell.getPosition();
            Game.Team color = coloredCell.getColor();
            cells[coordinates.getY()][coordinates.getX()].setColor(color);
        }
    }

    private void drawPieces(List<Pod> pieces) {
        for (Pod piece : pieces) {
            Vector2D pos = piece.getPosition();
            int x = pos.getX();
            int y = pos.getY();
            cells[y][x].setPod(piece);
        }
    }

    private void showJumpedOver(List<Jump> jumps) {
        for (Jump jump: jumps) {
            Vector2D pos = jump.getOver();
            int x = pos.getX();
            int y = pos.getY();
            cells[y][x].setEatSelection(jump.isEat());
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


    @Override
    public void onCellClick(CellView cellView) {
        if (cellClickListener != null) {
            cellClickListener.onCellClicked(cellView);
        }
    }


    // every cell holds a piece view but it might be empty
    public interface CellClickListener {
        void onCellClicked(CellView cellView);
    }
}