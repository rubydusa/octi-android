package com.example.octi.UI.Login;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.octi.UI.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private FirebaseAuth mAuth;
    private LoginActivity view;

    public LoginPresenter(LoginActivity view) {

        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password) {
        if (email.length() == 0) {
            view.loginFailed("Email field can not be empty");
            return;
        }

        if (password.length() == 0) {
            view.loginFailed("Password field can not be empty");
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            view.navigateToHome();
                        } else {
                            Toast toast = Toast.makeText(view, "Login failed", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
    }
}