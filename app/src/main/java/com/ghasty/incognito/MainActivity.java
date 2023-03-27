package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private Button logout;

    String userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);


        try {
            userData = getUserData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Log.d("Ghastyy", "here: " + userData);


        logout.setOnClickListener(v -> {
            clearUserData();
            startActivity(new Intent(this, SplashScreenActivity.class));
        });
    }

    private void clearUserData() {
        FileOutputStream fileOutputStream;

        try {
            fileOutputStream = openFileOutput("user.txt", MODE_PRIVATE);
            String text = "logout";
            fileOutputStream.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getUserData() throws IOException {
        FileInputStream fileInputStream = openFileInput("user.txt");
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder sb = new StringBuilder();
        String text = null;

        do {
            sb.append(text).append("\n");
        } while ((text = bufferedReader.readLine()) != null);

        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}