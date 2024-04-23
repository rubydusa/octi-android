package com.example.octi.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.octi.Models.User;
import com.example.octi.R;

public class AccountActivity extends AppCompatActivity {
    AccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        presenter = new AccountPresenter(this);
    }

    public void onLogOutClick(View view) {
        presenter.logOut();
    }

    public void showUser(User user) {
        TextView username = findViewById(R.id.tv_username_account_activity);
        username.setText(user.getName());
    }
}