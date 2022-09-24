package com.example.ufood;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ImageView personImage = findViewById(R.id.personImage);
        Button Login = findViewById(R.id.login);
        personImage.setOnClickListener(view -> {
            Intent intent = new Intent(Login_page.this,MainActivity.class);
            startActivity(intent);

        });

        Login.setOnClickListener(view -> {
            Intent intent = new Intent(Login_page.this,user_Registration.class);
            startActivity(intent);
        });
    }
}