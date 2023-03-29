package com.ghasty.incognito;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class SplashScreenActivity extends AppCompatActivity {

    private MaterialButton login, register, send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        send = findViewById(R.id.send);

        login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        register.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        send.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Username of the recipient?");

            EditText editText = new EditText(this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);

            dialog.setView(editText);

            dialog.setPositiveButton("Done", ((dialogInterface, i) -> {
                String username = editText.getText().toString();

                if (username.equals("")) {
                    editText.setError("Username can't be empty");
                    return;
                }

                Intent intent = new Intent(this, SendMessageActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }));

            dialog.setNegativeButton("Cancel", ((dialogInterface, i) -> {
                dialogInterface.cancel();
            }));

            dialog.show();
        });
    }
}