package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        try {
            String userData = getUserData();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("userData", userData);
            new Handler().postDelayed(() -> {
                startActivity(intent);
            }, 2000);
        } catch (IOException e) {
            new Handler().postDelayed(() -> {
                startActivity(new Intent(this, SplashScreenActivity.class));
            }, 2000);
            Log.d("Ghastyy", "No exist" + e.getLocalizedMessage());
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