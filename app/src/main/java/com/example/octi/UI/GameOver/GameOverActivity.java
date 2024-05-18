package com.example.octi.UI.GameOver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.octi.UI.Home.HomeActivity;
import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.example.octi.R;

public class GameOverActivity extends AppCompatActivity {
    GameOverPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        presenter = new GameOverPresenter(this, getIntent().getExtras().getString(getString(R.string.game_id)));
    }

    public void displayGame(Game game) {
        TextView player1 = findViewById(R.id.tv_player1_game_over);
        TextView player2 = findViewById(R.id.tv_player2_game_over);

        player1.setText(String.format("PLAYER 1: %s", game.getUser1().getName()));
        player2.setText(String.format("PLAYER 2: %s", game.getUser2().getName()));

        User winner;
        if (game.getWinner() == Game.Team.RED) {
            winner = game.getUser1();
        } else {
            winner = game.getUser2();
        }

        TextView winnerTv = findViewById(R.id.tv_winner_game_over);
        winnerTv.setText(String.format("WINNER: %s", winner.getName()));
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}