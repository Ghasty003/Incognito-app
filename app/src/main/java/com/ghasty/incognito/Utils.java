package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Utils extends AppCompatActivity {
    public void saveUserData(String userData) throws IOException {
        FileOutputStream fileOutputStream = openFileOutput("user.txt", MODE_PRIVATE);
        fileOutputStream.write(userData.getBytes());

        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
