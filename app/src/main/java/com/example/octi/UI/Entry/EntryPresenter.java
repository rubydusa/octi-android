package com.example.octi.UI.Entry;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EntryPresenter {
    FirebaseAuth mAuth;
    EntryActivity view;

    public EntryPresenter(EntryActivity view) {
        mAuth = FirebaseAuth.getInstance();
        this.view = view;
    }

    public void checkSignedInOnStart() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            view.navigateToHome();
        }
    }
}
