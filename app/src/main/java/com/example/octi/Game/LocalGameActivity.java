package com.example.octi.Game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.octi.Fragments.BoardFragment;
import com.example.octi.R;
import com.example.octi.models.Game;
import com.example.octi.models.Vector2D;

import java.util.List;

import kotlin.Pair;

public class LocalGameActivity extends AppCompatActivity {
    LocalGamePresenter presenter;

    BoardFragment board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_game);

        board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board_fragment);
        presenter = new LocalGamePresenter(this);
    }

    public void drawBoard(Game game) {
        board.drawBoard(game);
    }
}