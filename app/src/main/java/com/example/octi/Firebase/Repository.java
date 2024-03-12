package com.example.octi.Firebase;

import android.util.Log;

import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.example.octi.Room.CreateRoomPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Repository {
    private static Repository instance;

    private Repository() {}

    final String URL = "https://octi-abcf2-default-rtdb.europe-west1.firebasedatabase.app/";

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public void addUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Users/" + FirebaseAuth.getInstance().getUid());

        myRef.setValue(user);
    }

    public void readUser(String id, LoadUserListener loadUserListener){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Users/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                loadUserListener.updateUser(user);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void updateGame(Game game) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef;
        if (game.getGameId() == null) {
            Log.d("fuck", "updateGame: no game id");
            myRef = database.getReference("Games/").push();
            game.setGameId(myRef.getKey());
        }
        else {
            Log.d("fuck", "updateGame: gameId: " + game.getGameId());
            myRef = database.getReference("Games/" + game.getGameId());
        }
        // myRef.setValue(game);
        myRef.setValue(game, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.d("updateGame", "Data could not be saved " + databaseError.getMessage());
                } else {
                    Log.d("updateGame", "Data saved successfully.");
                }
            }
        });
    }

    LoadGameListener gLoadGameListener;
    public void readGame(String id, LoadGameListener loadGameListener) {
        gLoadGameListener = loadGameListener;
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Games/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("fuck", "onDataChange: readGame " + id);

                gLoadGameListener.updateGame(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public interface LoadGameListener {
        void updateGame(Game game);
    }

    public interface LoadUserListener {
        void updateUser(User user);
    }
}
