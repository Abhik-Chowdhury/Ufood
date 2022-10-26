package com.example.ufood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class Loading_Screen extends AppCompatActivity {
    View nContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        nContext = findViewById(R.id.backgroundLayout);
        nContext.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
        | View.SYSTEM_UI_FLAG_FULLSCREEN
        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Loading_Screen.this,Login_page.class);
            startActivity(intent);
            finish();
        },4000);
    }
}