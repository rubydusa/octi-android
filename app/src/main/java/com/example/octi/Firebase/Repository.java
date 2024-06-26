package com.example.octi.Firebase;

import android.util.Log;

import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.example.octi.Helpers.RoomCodeGenerator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Repository {
    private static Repository instance;

    private Repository() {}

    private static final String URL = "https://octi-abcf2-default-rtdb.europe-west1.firebasedatabase.app/";

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }

        return instance;
    }

    public void updateUser(User user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Users/" + user.getId());

        myRef.setValue(user);
    }

    private LoadUserListener currentLoadUserListener;
    public void setLoadUserListener(LoadUserListener listener) {
        currentLoadUserListener = listener;
    }
    public void readUser(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Users/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                currentLoadUserListener.updateUser(user);
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

        generateRoomCode(newRoomCode -> {
            if (newRoomCode == null) {
                callback.onComplete(null);
                return;
            }

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

    private LoadGameListener currentLoadGameListener;
    public void setLoadGameListener(LoadGameListener listener) {
        currentLoadGameListener = listener;
    }
    public void readGame(String id) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference myRef = database.getReference("Games/" + id);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentLoadGameListener.updateGame(dataSnapshot.getValue(Game.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    public void gameExists(String id, final Callback<Game.Status> callback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance(URL);
        DatabaseReference gamesRef = database.getReference("Games");
        gamesRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    callback.onComplete(dataSnapshot.getValue(Game.class).getStatus());
                } else {
                    callback.onComplete(Game.Status.NULL);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error checking game code existence", databaseError.toException());
                callback.onComplete(null);
            }
        });
    }

    private void generateRoomCode(final Callback<String> callback) {
        String potentialCode = RoomCodeGenerator.generateCode();
        gameExists(potentialCode, status -> {
            if (status == null) {
                callback.onComplete(null);
                return;
            }

            if (status != Game.Status.NULL) {
                generateRoomCode(callback);
            } else {
                callback.onComplete(potentialCode);
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
