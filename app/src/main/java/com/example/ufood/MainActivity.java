package com.example.ufood;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button offer_food = findViewById(R.id.Register);
        offer_food.setOnClickListener(view -> {
            Intent intent = new Intent(this,Login_page.class);
            startActivity(intent);
        });
    }
}