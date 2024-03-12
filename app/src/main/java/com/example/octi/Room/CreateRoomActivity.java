package com.example.octi.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

    }

    public void setUI1(User user) {
        setButton(true);
        TextView tvPlayer1 = findViewById(R.id.tv_user_id_1_create_room);
        tvPlayer1.setText(user.getName());
    }

    public void setUI2(User user) {
        TextView tvPlayer2 = findViewById(R.id.tv_user_id_2_create_room);
        tvPlayer2.setText(user.getName());
    }

    public void setButton (boolean state) {
        Button btn = findViewById(R.id.btn_start_room_create_room);
        btn.setEnabled(state);
    }
}