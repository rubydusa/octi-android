package com.example.octi.Firebase;

import android.util.Log;

import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.example.octi.Room.CreateRoomPresenter;
import com.example.octi.RoomCodeGenerator;
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

    public void createNewGame(final Callback<Game> callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference gamesRef = database.getReference("Games");

        Game game = new Game(null, null, null);

        generateRoomCode(gamesRef, newRoomCode -> {
            DatabaseReference newGameRef = gamesRef.child(newRoomCode);
            game.setGameId(newRoomCode);
            newGameRef.setValue(game);

            callback.onComplete(game);
        });
    }

        // assumes gameid is not null
    public void updateGame(Game game) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);

        DatabaseReference myRef = database.getReference("Games/" + game.getGameId());
        myRef.setValue(game);
    }

    public void readGame(String id, LoadGameListener loadGameListener) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Games/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadGameListener.updateGame(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void generateRoomCode(final DatabaseReference gamesRef, final Callback<String> callback) {
        String potentialCode = RoomCodeGenerator.generateCode();
        gamesRef.child(potentialCode).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // This code is unique
                    callback.onComplete(potentialCode);
                } else {
                    // Code already exists, try again
                    generateRoomCode(gamesRef, callback);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error checking game code uniqueness", databaseError.toException());
                callback.onComplete(null);
            }
        });
    }

    public interface LoadGameListener {
        void updateGame(Game game);
    }

    public interface LoadUserListener {
        void updateUser(User user);
    }

    // Define a callback interface
    public interface Callback<T> {
        void onComplete(T result);
    }
}
