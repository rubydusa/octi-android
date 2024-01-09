package com.example.octi.Login;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.octi.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    FirebaseAuth mAuth;
    LoginActivity view;

    public LoginPresenter(LoginActivity view) {

        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(view, HomeActivity.class);
                            view.startActivity(intent);
                        } else {

                        }
                    }
                });
    }
}