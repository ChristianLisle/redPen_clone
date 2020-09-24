package com.example.redpen.utils;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.redpen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    //Variables
    private TextView resultsTextView;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultsTextView = findViewById(R.id.text_view_result);
        Button buttonRequest = (Button) findViewById(R.id.button_request);
        queue = Volley.newRequestQueue(this);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParseUsers();
            }
        });
    }

    public void jsonParseUsers(){
        String url = "http://coms-309-ug-05.cs.iastate.edu:8080/assignments.json";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONArray users = response;

                for(int i = 0; i < users.length(); i++){
                    try {
                        JSONObject user = users.getJSONObject(i);

                        int id = user.getInt("id");
                        String name = user.getString("user");
                        String role = user.getString("role");

                        resultsTextView.append("ID: " + id + ", NAME: " + name + ", ROLE: " + role);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }
}