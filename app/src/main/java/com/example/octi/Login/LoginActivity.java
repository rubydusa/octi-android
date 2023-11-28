package com.example.octi.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.octi.Game.LocalGameActivity;
import com.example.octi.R;
import com.example.octi.models.Game;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Game game = new Game(getResources());
        startActivity(new Intent(this, LocalGameActivity.class));
    }
}