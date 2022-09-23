package com.example.ufood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Login_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        ImageView personImage = findViewById(R.id.personImage);

        personImage.setOnClickListener(view -> {
            Intent intent = new Intent(Login_page.this,MainActivity.class);
            startActivity(intent);

        });
    }
}