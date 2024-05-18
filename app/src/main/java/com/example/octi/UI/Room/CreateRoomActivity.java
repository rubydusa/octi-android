package com.example.octi.UI.Room;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.octi.UI.Game.OnlineGameActivity;
import com.example.octi.Models.User;
import com.example.octi.R;

public class CreateRoomActivity extends AppCompatActivity {
    CreateRoomPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        String roomId;
        Intent intent = getIntent();
        Uri data = intent.getData();

        if (data != null && intent.getAction().equals(Intent.ACTION_VIEW)) {
            roomId = data.getQueryParameter("id");
        } else {
            roomId = intent.getExtras().getString(getString(R.string.game_id));
        }

        presenter = new CreateRoomPresenter(
                this,
                roomId
        );
    }

    public void navigateToGame(String gameId) {
        Intent intent = new Intent(this, OnlineGameActivity.class);
        intent.putExtra(getString(R.string.game_id), gameId);
        startActivity(intent);
        finish();
    }

    public void setUI1(User user) {
        setButton(true);
        TextView tvPlayer1 = findViewById(R.id.tv_user_id_1_create_room);
        tvPlayer1.setText(user.getName());
        presenter.updateStartButton();
    }

    public void setUI2(User user) {
        TextView tvPlayer2 = findViewById(R.id.tv_user_id_2_create_room);
        tvPlayer2.setText(user.getName());
        presenter.updateStartButton();
    }

    public void setButton (boolean state) {
        Button btn = findViewById(R.id.btn_start_room_create_room);
        btn.setEnabled(state);
    }

    public void setSwitchTeamsButton (boolean enabled, boolean isRed) {
        Button switchTeamsBtn = findViewById(R.id.btn_switch_colors_create_room);
        switchTeamsBtn.setEnabled(enabled);

        if (enabled) {
            switchTeamsBtn.getBackground().setAlpha(255);
        } else {
            switchTeamsBtn.getBackground().setAlpha(255);
        }

        if (isRed) {
            switchTeamsBtn.setBackgroundColor(getResources().getColor(R.color.team_red, getTheme()));
            switchTeamsBtn.setText("You Are Red");
        } else {
            switchTeamsBtn.setBackgroundColor(getResources().getColor(R.color.team_green, getTheme()));
            switchTeamsBtn.setText("You Are Green");
        }
    }

    public void setRoomMessage(String message) {
        TextView tvRoomMessage = findViewById(R.id.tv_room_message_create_room);
        tvRoomMessage.setText(message);
    }

    public void setRoomCode(String code) {
        TextView tvRoomCode = findViewById(R.id.tv_room_code_create_room);
        tvRoomCode.setText("Room Code: " + code);
    }

    public void onClickNavigateToGame(View view) {
        presenter.startGame();
    }

    public void onClickShare(View view) {
        presenter.shareLink();
    }

    public void startShareLink(Intent shareIntent) {
        startActivity(Intent.createChooser(shareIntent, "Share link via"));
    }

    public void onClickSwitchTeam(View view) {
        presenter.switchTeams();
    }
}