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

    public CreateRoomPresenter(CreateRoomActivity view, String id) {
        this.view = view;
        this.id = id;

        view.setRoomCode(id);
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
        boolean isGameReady = game.getUser1() != null && game.getUser2() != null;
        boolean isPlayer1 = game.getUser1().getId().equals(FirebaseAuth.getInstance().getUid());

        if (isGameReady && isPlayer1) {
            view.setRoomMessage("Room ready! You can start the game");
        } else if (isGameReady && !isPlayer1) {
            view.setRoomMessage("Room ready! Wait for host to begin");
        } else {
            view.setRoomMessage("Waiting...");
        }

        view.setButton(isGameReady && isPlayer1);
    }

    public void startGame() {
        game.setStatus(Game.Status.ACTIVE);
        Repository.getInstance().updateGame(game);
    }
}
