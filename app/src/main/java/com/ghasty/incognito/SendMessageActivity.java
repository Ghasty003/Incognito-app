package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class SendMessageActivity extends AppCompatActivity {
    private TextView userView;
    private MaterialButton sendMessage;
    private EditText message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        userView = findViewById(R.id.username);
        sendMessage = findViewById(R.id.mb_send);
        message = findViewById(R.id.et_message);

        String username = getIntent().getStringExtra("username");
        String userText = "Send a secret message to " + username;

        userView.setText(userText);
    }
}