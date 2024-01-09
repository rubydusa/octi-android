package com.example.octi.Register;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.octi.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter {
    FirebaseAuth mAuth;
    RegisterActivity view;

    public RegisterPresenter(RegisterActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(view, HomeActivity.class);
                            view.startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
                });
    }
}
