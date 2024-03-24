package com.example.octi.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.R;

public class OnlineGameActivity extends AppCompatActivity {
    OnlineGamePresenter presenter;

    BoardFragment board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_fragment);
        presenter = new OnlineGamePresenter(this, getIntent().getExtras().getString(getString(R.string.game_id)));
    }

    public void drawBoard(Game game) {
        board.drawBoard(game);
    }

    public void topLeftProngOnClick(View view) {
    }

    public void topCenterProngOnClick(View view) {
    }

    public void topRightProngOnClick(View view) {
    }

    public void middleLeftProngOnClick(View view) {
    }

    public void middleRightProngOnClick(View view) {
    }

    public void bottomLeftProngOnClick(View view) {
    }

    public void bottomCenterProngOnClick(View view) {
    }

    public void bottomRightProngOnClick(View view) {
    }
}