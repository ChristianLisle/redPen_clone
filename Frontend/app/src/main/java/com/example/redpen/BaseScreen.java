package com.example.redpen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

public class BaseScreen extends AppCompatActivity {

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basescreen);

        Button tLogin = (Button) findViewById(R.id.TeacherLog);
        tLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent goTeacher = new Intent(getApplicationContext(), TeacherLogin.class);
                startActivity(goTeacher);
            }
        });

        Button pLogin = (Button) findViewById(R.id.ParentLog);
        pLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent goTeacher = new Intent(getApplicationContext(), ParentLogin.class);
                startActivity(goTeacher);
            }
        });

        Button sLogin = (Button) findViewById(R.id.StudentLog);
        sLogin.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent goTeacher = new Intent(getApplicationContext(), StudentLogin.class);
                startActivity(goTeacher);
            }
        });
    }

}