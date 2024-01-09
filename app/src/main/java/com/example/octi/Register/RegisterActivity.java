package com.example.octi.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.octi.Home.HomeActivity;
import com.example.octi.R;

public class RegisterActivity extends AppCompatActivity {

    RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
    }

    public void onRegisterClick(View view) {
        EditText usernameField = findViewById(R.id.et_username_register_activity);
        EditText passwordField = findViewById(R.id.et_password_register_activity);

        presenter.register(
                usernameField.getText().toString(),
                passwordField.getText().toString()
        );
    }
}