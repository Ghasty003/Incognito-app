package com.ghasty.incognito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

public class SendMessageActivity extends AppCompatActivity {
    private TextView userView;
    private MaterialButton sendMessage;
    private EditText message;

    private String username;

    private View messageView, view404;
    private ProgressBar progressBar;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        userView = findViewById(R.id.username);
        sendMessage = findViewById(R.id.mb_send);
        message = findViewById(R.id.et_message);

        progressBar = findViewById(R.id.progress_bar);
        messageView = findViewById(R.id.message_layout);
        view404 = findViewById(R.id.layout_404);


        username = getIntent().getStringExtra("username");
        String userText = "Send a secret message to " + username;

        userView.setText(userText);

        requestQueue = Volley.newRequestQueue(this);

        getUser();
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