package com.example.octi.UI.Register;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.octi.Firebase.Repository;
import com.example.octi.UI.Home.HomeActivity;
import com.example.octi.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterPresenter {
    private FirebaseAuth mAuth;
    private RegisterActivity view;

    public RegisterPresenter(RegisterActivity view) {
        this.view = view;
        mAuth = FirebaseAuth.getInstance();
    }

    public void register(String username, String email, String password) {
        if (username.length() == 0) {
            view.registerFailed("Username can not be empty");
            return;
        }

        if (email.length() == 0) {
            view.registerFailed("Email field can not be empty");
            return;
        }

        if (password.length() == 0) {
            view.registerFailed("Password field can not be empty");
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(view, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User newUser = new User(email, username, FirebaseAuth.getInstance().getUid());
                            Repository.getInstance().updateUser(newUser);

                            view.navigateToHome();
                        } else {
                            Exception e = task.getException();
                            if (e != null) {
                                String message = e.getLocalizedMessage();
                                Toast toast = Toast.makeText(view, message, Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                });
    }
}
