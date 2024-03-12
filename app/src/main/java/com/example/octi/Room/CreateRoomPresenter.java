package com.example.octi.Room;

import android.util.Log;

import com.example.octi.Firebase.Repository;
import com.example.octi.Models.Game;
import com.example.octi.Models.User;

public class CreateRoomPresenter implements Repository.LoadGameListener, Repository.LoadUserListener {
    CreateRoomActivity view;
    String id;

    Game game;

    User player1;
    User player2;

    public CreateRoomPresenter(CreateRoomActivity view, String id) {
        this.view = view;
        this.id = id;

        Repository.getInstance().readGame(id, this);
    }

    @Override
    public void updateGame(Game game) {
        Log.d("fuck", "updateGame");
        this.game = game;
        if(game.getStatus()==Game.Status.ACTIVE){
            view.navigateToGame(game.getGameId());
            return;
        }
        if(game.getUser2Id() == null){
            Repository.getInstance().readUser(game.getUser1Id(), this);
        }
        else{
            Repository.getInstance().readUser(game.getUser1Id(), this);
            Repository.getInstance().readUser(game.getUser2Id(), this);
        }

    }

    @Override
    public void updateUser(User user) {
        if(user.getId().equals(game.getUser1Id())){
            this.player1 = user;
            view.setUI1(user);
        }
        else{
            this.player2 = user;
            view.setUI2(user);
        }
    }
}
