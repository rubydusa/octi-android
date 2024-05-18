package com.example.octi.UI.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.UI.GameOver.GameOverActivity;
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
        TextView tvTurn = findViewById(R.id.tv_turn_online_game);
        TextView tvPlayer1 = findViewById(R.id.tv_player1_online_game);
        TextView tvPlayer2 = findViewById(R.id.tv_player2_online_game);

        tvTurn.setText(game.getCurrentGameState().getTurn() == Game.Team.RED ? "Turn: red" : "Turn: green");

        String user1Team = game.getTeamStringRep(game.getUserTeam(game.getUser1().getId()));
        String user2Team = game.getTeamStringRep(game.getUserTeam(game.getUser2().getId()));

        tvPlayer1.setText(
                String.format("Player 1 (%s): %s (%s prongs)",
                        user1Team,
                        game.getUser1().getName(),
                        game.getCurrentGameState().getTeamProngCount(Game.Team.RED)
                )
        );
        tvPlayer2.setText(
                String.format("Player 2 (%s): %s (%s prongs)",
                        user2Team,
                        game.getUser2().getName(),
                        game.getCurrentGameState().getTeamProngCount(Game.Team.GREEN)
                )
        );
    }

    public void finalizeOnClick(View view) {
        presenter.finalizeMove();
    }
}