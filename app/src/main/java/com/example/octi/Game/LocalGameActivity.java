package com.example.octi.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.R;
import com.example.octi.Models.Game;

public class LocalGameActivity extends AppCompatActivity {
    LocalGamePresenter presenter;

    BoardFragment board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_game);

        board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_fragment);
        presenter = new LocalGamePresenter(this, board);
    }

    public void drawBoard(Game game) {
        board.drawBoard(game);
    }

    public void middleRightProngOnClick(View view) {
        prongPlaceMove(0);
    }

    public void topRightProngOnClick(View view) {
        prongPlaceMove(1);
    }

    public void topCenterProngOnClick(View view) {
        prongPlaceMove(2);
    }

    public void topLeftProngOnClick(View view) {
        prongPlaceMove(3);
    }

    public void middleLeftProngOnClick(View view) {
        prongPlaceMove(4);
    }

    public void bottomLeftProngOnClick(View view) {
        prongPlaceMove(5);
    }

    public void bottomCenterProngOnClick(View view) {
        prongPlaceMove(6);
    }

    public void bottomRightProngOnClick(View view) {
        prongPlaceMove(7);
    }

    private void prongPlaceMove(int prong) {
        presenter.prongPlaceMove(prong);
    }
}