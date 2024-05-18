package com.example.octi.UI.Account;

import android.content.Intent;

import com.example.octi.UI.Entry.EntryActivity;
import com.example.octi.Firebase.Repository;
import com.example.octi.Models.User;
import com.google.firebase.auth.FirebaseAuth;

public class AccountPresenter implements Repository.LoadUserListener {
    private FirebaseAuth mAuth;
    private AccountActivity view;

    public AccountPresenter(AccountActivity view) {
        mAuth = FirebaseAuth.getInstance();
        this.view = view;
        Repository.getInstance().setLoadUserListener(this);
        Repository.getInstance().readUser(FirebaseAuth.getInstance().getUid());
    }

    public void logOut() {
        mAuth.signOut();
        view.logOut();
    }

    @Override
    public void updateUser(User user) {
        view.showUser(user);
    }
}
