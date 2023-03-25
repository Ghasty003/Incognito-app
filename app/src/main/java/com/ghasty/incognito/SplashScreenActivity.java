package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.button.MaterialButton;

public class SplashScreenActivity extends AppCompatActivity {

    private MaterialButton login, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}