package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SendMessageActivity extends AppCompatActivity {
    private TextView userView, errorMessage;
    private MaterialButton sendMessage;
    private EditText message;

    private String username;

    private View messageView, view404;
    private ProgressBar progressBar, sendMessageProgress;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        userView = findViewById(R.id.username);
        errorMessage = findViewById(R.id.error_message);
        sendMessage = findViewById(R.id.mb_send);
        message = findViewById(R.id.et_message);

        progressBar = findViewById(R.id.progress_bar);
        sendMessageProgress = findViewById(R.id.send_progress_bar);

        messageView = findViewById(R.id.message_layout);
        view404 = findViewById(R.id.layout_404);


        username = getIntent().getStringExtra("username");
        String userText = "Send a secret message to " + username;

        userView.setText(userText);

        String error_message = "No such user with the username " + username;
        errorMessage.setText(error_message);

        requestQueue = Volley.newRequestQueue(this);

        getUser();


        sendMessage.setOnClickListener(v -> {
            try {
                sendMessageToUser();
            } catch (JSONException e) {
                Log.d("Ghastyy", "Json error" + e.getLocalizedMessage());
            }
        });
    }


    private void sendMessageToUser() throws JSONException {
        String url = "https://incognito-j4hs.onrender.com/message";

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("message", message.getText().toString());
        jsonObject.put("receiver", username);

        message.getText().clear();
        sendMessage.setVisibility(View.GONE);
        sendMessageProgress.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, response -> {
            Log.d("Ghastyy", response.toString());
            sendMessage.setVisibility(View.VISIBLE);
            sendMessageProgress.setVisibility(View.GONE);
            Toast.makeText(this, "Message sent successfully", Toast.LENGTH_SHORT).show();
        }, error -> {
            Log.d("Ghastyy", "Except error: " + error.getLocalizedMessage());

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();

                header.put("Content-Type", "application/json");

                return header;
            }
        };


        requestQueue.add(jsonObjectRequest);
    }

    private void getUser() {
        String url = "https://incognito-j4hs.onrender.com/auth?user=" + username;

        Log.d("Ghastyy", "starting..");

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            progressBar.setVisibility(View.GONE);

            if (response.equals("true")) {
                messageView.setVisibility(View.VISIBLE);
                return;
            }

            view404.setVisibility(View.VISIBLE);

        }, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Ghastyy", "Except: " + error.getLocalizedMessage());
        });


        requestQueue.add(request);

    }
}