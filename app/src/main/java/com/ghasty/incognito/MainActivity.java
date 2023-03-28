package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button logout;
    private TextView tv_username;
    private RecyclerView recyclerView;

    String userData;
    String username;
    String token;

    private RequestQueue requestQueue;

    List<MessageModel> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        tv_username = findViewById(R.id.user_name);
        recyclerView = findViewById(R.id.rv_messages);

        requestQueue = Volley.newRequestQueue(this);

        messages = new ArrayList<>();

        messages.add(new MessageModel("Hi bro", "jan 2"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MessageAdapter(this, messages));

        try {
            userData = getUserData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            username = convertDataToJson().getString("username");
            token = convertDataToJson().getString("token");
            Log.d("Ghastyy", "token: " + token);
            String st_username = username + "'s";
            tv_username.setText(st_username);
        } catch (JSONException e) {
            Log.d("Ghastyy", e.getLocalizedMessage());
        }

        getMessages();

        logout.setOnClickListener(v -> {
            clearUserData();
            startActivity(new Intent(this, SplashScreenActivity.class));
        });
    }


    private void getMessages() {
        String url = "https://incognito-j4hs.onrender.com/message?user=" + username;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.d("Ghastyy", response.toString());
        }, error -> {
            Log.d("Ghastyy", "Except: " + error.getLocalizedMessage());
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                String bearerToken = "Bearer " + token;
                headers.put("Authorization", bearerToken);


                return headers;
            }
        };

        requestQueue.add(request);
    }

    private JSONObject convertDataToJson() throws JSONException {
        JSONObject jsonObject = new JSONObject(userData);

        return jsonObject;
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
        String text;

        while ((text = bufferedReader.readLine()) != null) {
            sb.append(text);
        }

        if (fileInputStream != null) {
            fileInputStream.close();
        }

        return sb.toString();
    }
}