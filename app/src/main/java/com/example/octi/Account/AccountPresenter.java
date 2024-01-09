package com.example.octi.Account;

import android.content.Intent;

import com.example.octi.Entry.EntryActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AccountPresenter {
    FirebaseAuth mAuth;
    AccountActivity view;

    public AccountPresenter(AccountActivity view) {
        mAuth = FirebaseAuth.getInstance();
        this.view = view;
    }

    public void logOut() {
        mAuth.signOut();
        Intent intent = new Intent(view, EntryActivity.class);
        view.startActivity(intent);
    }
}
