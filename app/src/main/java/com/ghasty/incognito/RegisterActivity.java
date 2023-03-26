package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText username, password;
    private MaterialButton register;
    private ProgressBar progressBar;
    private RequestQueue requestQueue;
    Utils utils = new Utils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progress_bar);

        requestQueue = Volley.newRequestQueue(this);

        register.setOnClickListener(v -> {
            try {
                registerUser();
            } catch (JSONException e) {
                Log.d("Ghastyy", e.getLocalizedMessage());
            }
        });
    }

    private void registerUser() throws JSONException {
        boolean isValidated = validateUserInput();

        if (!isValidated) {
            return;
        }

        register.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username.getText().toString());
        jsonObject.put("password", password.getText().toString());

        Log.d("Ghastyy", jsonObject.toString());

        String url = "https://incognito-j4hs.onrender.com/auth/signup";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d("Ghastyy", "Res: " + response.toString());
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();

            try {
                utils.saveUserData(response.toString());
            } catch (IOException e) {
                Toast.makeText(utils, "Error saving user data: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, error -> {
            Log.d("Ghastyy", "Auth error: " + error.getLocalizedMessage());
            register.setVisibility(View.VISIBLE);
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


    private boolean validateUserInput() {
        String userName = username.getText().toString();
        String passWord = password.getText().toString();

        if (userName.length() < 2) {
            username.setError("Username must be at least 2 characters");
            return false;
        }

        if (passWord.length() < 6) {
            password.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }
}