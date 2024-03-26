package com.example.octi.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.GameOver.GameOverActivity;
import com.example.octi.Models.Game;
import com.example.octi.R;

public class OnlineGameActivity extends AppCompatActivity {
    OnlineGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        BoardFragment board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_fragment);
        presenter = new OnlineGamePresenter(this, board, getIntent().getExtras().getString(getString(R.string.game_id)));
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

    public void navigateToGameOver(String gameId) {
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra(getString(R.string.game_id), gameId);
        startActivity(intent);
        finish();
    }

    public void displayGameInfo(Game game) {
        ((TextView) findViewById(R.id.tv_player1_game)).setText(String.format("Player 1 (red): %s", game.getUser1().getName()));
        ((TextView) findViewById(R.id.tv_player2_game)).setText(String.format("Player 2 (green): %s", game.getUser2().getName()));
        ((TextView) findViewById(R.id.tv_turn_game)).setText(game.getCurrentGameState().getTurn() == Game.Team.RED ? "Turn: red" : "Turn: green");
    }
}