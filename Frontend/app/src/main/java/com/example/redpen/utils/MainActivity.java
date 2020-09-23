package com.example.redpen.utils;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.redpen.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnJsonObject = (Button) findViewById(R.id.button_string_array);
        Button btnJsonArray = (Button) findViewById(R.id.button_string_object);





    }
}