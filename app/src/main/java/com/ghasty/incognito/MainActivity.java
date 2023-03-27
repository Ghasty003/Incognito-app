package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);

        String userData = getIntent().getStringExtra("userData");
        Log.d("Ghastyy", userData);


        logout.setOnClickListener(v -> {
            clearUserData();
            startActivity(new Intent(this, SplashScreenActivity.class));
        });
    }

    private void clearUserData() {
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = openFileOutput("user.txt", MODE_PRIVATE);
            String text = "";
            fileOutputStream.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}