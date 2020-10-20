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
        Button buttonRequestTeachers = (Button) findViewById(R.id.button_request_teachers);
        Button buttonClear = (Button) findViewById(R.id.button_clear);
        queue = Volley.newRequestQueue(this);

        buttonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jsonParseUsers();
            }
        });

        buttonRequestTeachers.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                jsonParseTeachers();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                resultsTextView.setText("");
            }
        });
    }

    public void jsonParseUsers(){
        String url = "http://coms-309-ug-05.cs.iastate.edu:8080/users";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject userObject = response.getJSONObject(i);

                        int id = userObject.getInt("id");
                        String name = userObject.getString("user");
                        String role = userObject.getString("role");

                        resultsTextView.append("ID: " + id + ", NAME: " + name + ", ROLE: " + role + "\n");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonArrayRequest);
    }

    public void jsonParseTeachers(){
        String url = "http://coms-309-ug-05.cs.iastate.edu:8080/teachers";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for(int i = 0; i < response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");

                        resultsTextView.append("ID: " + id + ", NAME: " + name + "\n");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resultsTextView.append("Error: " + error.toString());
            }
        });

        queue.add(request);
    }


}