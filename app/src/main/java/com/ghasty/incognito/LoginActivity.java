package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private TextView register;
    private MaterialButton login;
    private ProgressBar progressBar;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register_text);
        progressBar = findViewById(R.id.progress_bar);
        login = findViewById(R.id.login);

        register.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        login.setOnClickListener(v -> {
            try {
                loginUser();
            } catch (JSONException e) {
                Log.d("Ghastyy", e.getLocalizedMessage());
            }
        });


        requestQueue = Volley.newRequestQueue(this);
    }


    private void loginUser() throws JSONException {

        login.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username.getText().toString());
        jsonObject.put("password", password.getText().toString());

        Log.d("Ghastyy", jsonObject.toString());

        String url = "https://incognito-j4hs.onrender.com/auth/login";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d("Ghastyy", "Res: " + response.toString());
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            saveUserData(response.toString());
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, error -> {
            Log.d("Ghastyy", "Auth error: " + error.getLocalizedMessage());
            Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show();
            login.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };


        requestQueue.add(request);
    }


    private void saveUserData(String userData) {
        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = openFileOutput("user.txt", MODE_PRIVATE);
            fileOutputStream.write(userData.getBytes());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}