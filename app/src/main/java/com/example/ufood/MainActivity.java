package com.example.ufood;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView logo_unesco;
    public static final String ProfileType = "com.example.UFood";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button offer_food = findViewById(R.id.Register);
        logo_unesco = findViewById(R.id.logo_unesco);
        offer_food.setOnClickListener(view -> {
            Intent intent = new Intent(this,Login_page.class);
            startActivity(intent);
        });
        Intent intent = getIntent();
        String  ProfileType0 = intent.getStringExtra(Login_page.UserType);

        logo_unesco.setOnClickListener(view -> {

            Intent intent2 = new Intent(MainActivity.this,user_profile.class);
            intent2.putExtra(ProfileType,ProfileType0);
            startActivity(intent2);

        });

    }
}