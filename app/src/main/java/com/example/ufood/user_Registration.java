package com.example.ufood;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Objects;


public class user_Registration extends AppCompatActivity {
    ImageView profile_pic;
    ImageView add_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
         profile_pic = findViewById(R.id.profile_pic);
         add_image = findViewById(R.id.add_image);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter <CharSequence> userType = ArrayAdapter.createFromResource(user_Registration.this,R.array.usertype, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(userType);

        add_image.setOnClickListener(view -> {
            ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(512,512)
                    .start();
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = Objects.requireNonNull(data).getData();
        profile_pic.setImageURI(uri);
    }
}