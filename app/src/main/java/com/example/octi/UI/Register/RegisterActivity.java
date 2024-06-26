package com.example.octi.UI.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.octi.R;
import com.example.octi.UI.Home.HomeActivity;

public class RegisterActivity extends AppCompatActivity {

    private RegisterPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        presenter = new RegisterPresenter(this);
    }

    public void onRegisterClick(View view) {
        EditText usernameField = findViewById(R.id.et_username_register_activity);
        EditText emailField = findViewById(R.id.et_email_register_activity);
        EditText passwordField = findViewById(R.id.et_password_register_activity);

        presenter.register(
                usernameField.getText().toString(),
                emailField.getText().toString(),
                passwordField.getText().toString()
        );
    }

    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void registerFailed(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}