package com.example.ufood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class user_profile extends AppCompatActivity {
    EditText username,userEmail,userPhone,userPassword,userType;
    ImageView userPhoto;
    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        username = findViewById(R.id.username);
        userEmail =findViewById(R.id.userEmail);
        userPhone =findViewById(R.id.userPhone);
        userPassword =findViewById(R.id.userPassword);
        userType = findViewById(R.id.userType);
        userPhoto = findViewById(R.id.userPhoto);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null){
            Intent intent = new Intent(user_profile.this,Login_page.class);
            startActivity(intent);
            finish();
        }
        else{
            Intent intent1 = getIntent();
            String Type = intent1.getStringExtra(MainActivity.ProfileType);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            if(Type.equals("User")){
                DatabaseReference reference = database.getReference("user").child(currentUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserHolder userHolder = snapshot.getValue(UserHolder.class);
                        if(userHolder != null){
                            username.setText(userHolder.getUser_name());
                            userEmail.setText(userHolder.getEmail());
                            userPhone.setText(userHolder.getPhone());
                            userPassword.setText(userHolder.getPassword());
                            userType.setText(userHolder.getUser_type());
                            String profile = userHolder.getProfile_pic();
                            Picasso.get()
                                    .load(profile)
                                    .transform(new CircleTransform())
                                    .resize(200,200)
                                    .into(userPhoto);
                            Toast.makeText(user_profile.this, ""+profile, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }else  if(Type.equals("Food-Offer")){
                DatabaseReference reference = database.getReference("offer-food").child(currentUser.getUid());
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        UserHolder userHolder = snapshot.getValue(UserHolder.class);
                        if(userHolder != null){
                            username.setText(userHolder.getUser_name());
                            userEmail.setText(userHolder.getEmail());
                            userPhone.setText(userHolder.getPhone());
                            userPassword.setText(userHolder.getPassword());
                            userType.setText(userHolder.getUser_type());
                            String profile = userHolder.getProfile_pic();
                            Picasso.get()
                                    .load(profile)
                                    .transform(new CircleTransform())
                                    .resize(200,200)
                                    .into(userPhoto);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }


    }
}