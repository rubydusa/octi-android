package com.example.octi.UI.Entry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.octi.UI.Home.HomeActivity;
import com.example.octi.UI.Login.LoginActivity;
import com.example.octi.R;
import com.example.octi.UI.Register.RegisterActivity;

public class EntryActivity extends AppCompatActivity {

    private EntryPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        presenter = new EntryPresenter(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        presenter.checkSignedInOnStart();
    }

    public void onLoginClick(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onRegisterClick(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}