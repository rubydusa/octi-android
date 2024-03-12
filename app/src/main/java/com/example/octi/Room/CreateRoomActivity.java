package com.example.octi.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.octi.Models.Game;
import com.example.octi.Models.User;
import com.example.octi.R;

public class CreateRoomActivity extends AppCompatActivity {
    CreateRoomPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        presenter = new CreateRoomPresenter(
                this,
                getIntent().getExtras().getString(getString(R.string.game_id))
        );
    }

    public void navigateToGame(String gameId) {
        Log.d("fuck", "navigateToGame: Done!");
    }

    public void setUI1(User user) {
        TextView tvPlayer1 = findViewById(R.id.tv_user_id_1_create_room);
        tvPlayer1.setText(user.getName());
    }

    public void setUI2(User user) {
        TextView tvPlayer2 = findViewById(R.id.tv_user_id_2_create_room);
        tvPlayer2.setText(user.getName());
    }
}