package com.example.octi.UI.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.octi.Models.User;
import com.example.octi.R;

public class AccountActivity extends AppCompatActivity {
    AccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        presenter = new AccountPresenter(this);
    }

    public void onLogOutClick(View view) {
        presenter.logOut();
    }

    public void showUser(User user) {
        TextView username = findViewById(R.id.tv_username_account_activity);
        TextView elo = findViewById(R.id.tv_elo_account);
        TextView wins = findViewById(R.id.tv_wins_account);
        TextView losses = findViewById(R.id.tv_losses_account);

        username.setText(user.getName());
        elo.setText(String.format("ELO: %s", user.getElo()));
        wins.setText(String.format("Wins: %s", user.getWins()));
        losses.setText(String.format("Losses: %s", user.getLosses()));
    }
}