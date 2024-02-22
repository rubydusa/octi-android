package com.example.octi.Firebase;

import com.example.octi.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Repository {
    private static Repository instance;

    private Repository() {}

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public void addUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://octi-abcf2-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getUid());

        myRef.setValue(user);
    }
}