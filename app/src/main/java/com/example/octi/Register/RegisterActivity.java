package com.example.octi.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.octi.Home.HomeActivity;
import com.example.octi.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onRegisterClick(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}