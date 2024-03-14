package com.example.octi.Room;

import android.util.Log;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class CreateRoomPresenter implements Repository.LoadGameListener, Repository.LoadUserListener {
    CreateRoomActivity view;
    String id;

    Game game;

    User player1;
    User player2;

    public CreateRoomPresenter(CreateRoomActivity view, String id) {
        this.view = view;
        this.id = id;

        Log.d("CreateRoomPresenter", id);
        Repository.getInstance().readGame(id, this);
    }

    @Override
    public void updateGame(Game game) {
        this.game = game;
        if(game.getStatus()==Game.Status.ACTIVE){
            view.navigateToGame(game.getGameId());
            return;
        }
        Repository.getInstance().readUser(FirebaseAuth.getInstance().getUid(), this);
    }

    @Override
    public void updateUser(User user) {
        if (game.getUser1() == null) {
            game.setUser1(user);
            Repository.getInstance().updateGame(game);
        } else if (game.getUser2() == null && !Objects.equals(game.getUser1().getId(), user.getId())) {
            game.setUser2(user);
            Repository.getInstance().updateGame(game);
        }

        if (game.getUser1() != null) {
            view.setUI1(game.getUser1());
        }
        if (game.getUser2() != null) {
            view.setUI2(game.getUser2());
        }
    }

    public void updateStartButton() {
        view.setButton(player1 != null && player2 != null);
    }

    public void startGame() {
        view.navigateToGame(id);
    }
}
