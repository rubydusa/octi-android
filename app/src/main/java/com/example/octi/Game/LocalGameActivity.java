package com.example.octi.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.R;
import com.example.octi.Models.Game;

public class LocalGameActivity extends AppCompatActivity {
    LocalGamePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_game);

        BoardFragment board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_fragment);
        presenter = new LocalGamePresenter(this, board);
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

    public void finalizeOnClick(View view) {
        presenter.finalizeMove();
    }

    public void displayWinner(Game.Team winner) {
        TextView tvWinner = findViewById(R.id.tv_winner_local_game);
        tvWinner.setText(winner == Game.Team.RED ? "Red wins" : "Green wins");
    }

    public void updateGameUI(Game game) {
        TextView tvTurn = findViewById(R.id.tv_turn_local_game);
        TextView tvRedProngs = findViewById(R.id.tv_red_prongs_local_game);
        TextView tvGreenProngs = findViewById(R.id.tv_green_prongs_local_game);

        tvTurn.setText(String.format("Turn: %s", game.getCurrentGameState().getTurn() == Game.Team.RED ? "Red" : "Green"));
        tvRedProngs.setText(String.format("Red prongs: %s", game.getCurrentGameState().getTeamProngCount(Game.Team.RED)));
        tvGreenProngs.setText(String.format("Green prongs: %s", game.getCurrentGameState().getTeamProngCount(Game.Team.GREEN)));
    }
}