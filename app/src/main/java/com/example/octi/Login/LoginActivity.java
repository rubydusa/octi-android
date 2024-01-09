package com.example.octi.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.octi.Game.LocalGameActivity;
import com.example.octi.Home.HomeActivity;
import com.example.octi.R;
import com.example.octi.models.Game;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
    }

    public void onLoginClick(View view) {
        EditText usernameField = findViewById(R.id.et_username_login_activity);
        EditText passwordField = findViewById(R.id.et_password_login_activity);

        presenter.login(
                usernameField.getText().toString(),
                passwordField.getText().toString()
        );
    }
}