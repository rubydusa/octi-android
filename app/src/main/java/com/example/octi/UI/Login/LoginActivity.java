package com.example.octi.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.octi.R;
import com.example.octi.UI.Home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
    }

    public void onLoginClick(View view) {
        EditText emailField = findViewById(R.id.et_email_login_activity);
        EditText passwordField = findViewById(R.id.et_password_login_activity);

        presenter.login(
                emailField.getText().toString(),
                passwordField.getText().toString()
        );
    }

    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void loginFailed(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
