package com.example.octi.Game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.Models.Game;
import com.example.octi.R;

public class OnlineGameActivity extends AppCompatActivity {
    OnlineGamePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);

        BoardFragment board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_fragment);
        presenter = new OnlineGamePresenter(this, board, getIntent().getExtras().getString(getString(R.string.game_id)));
    }

    public void middleRightProngOnClick(View view) {
        prongPlaceMove(0);
    }

    public void topRightProngOnClick(View view) {
        prongPlaceMove(1);
    }

    public void topCenterProngOnClick(View view) {
        prongPlaceMove(2);
    }

    public void topLeftProngOnClick(View view) {
        prongPlaceMove(3);
    }

    public void middleLeftProngOnClick(View view) {
        prongPlaceMove(4);
    }

    public void bottomLeftProngOnClick(View view) {
        prongPlaceMove(5);
    }

    public void bottomCenterProngOnClick(View view) {
        prongPlaceMove(6);
    }

    public void bottomRightProngOnClick(View view) {
        prongPlaceMove(7);
    }

    private void prongPlaceMove(int prong) {
        presenter.prongPlaceMove(prong);
    }
}