package com.example.ufood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Login_page extends AppCompatActivity {
    EditText password_login;
    EditText email_login;
    String password1;
    String email1;
    FirebaseAuth mAuth;
    Spinner spinner2;
    ArrayList<String> arrayList = new ArrayList<>();
    public static final String UserType = "com.example.UFood";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        ImageView personImage = findViewById(R.id.personImage);
        Button Login = findViewById(R.id.login);

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);

        mAuth = FirebaseAuth.getInstance();
       


        spinner2 = findViewById(R.id.spinner2);
        arrayList.add("User");
        arrayList.add("Food-Offer");
        ArrayAdapter<String> userType = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        spinner2.setAdapter(userType);

        final boolean[] pwdVisible= {true};

        password_login.setOnTouchListener((view, motionEvent) -> {
            final int Right = 2;
            if(motionEvent.getAction()== MotionEvent.ACTION_UP){
                if(motionEvent.getRawX()>=password_login.getRight()-password_login.getCompoundDrawables()[Right].getBounds().width()){
                    if(pwdVisible[0]){
                        //set The Drawable img
                        password_login.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_key,0,R.drawable.ic_visibility_of,0);
                        //Hide the Password
                        password_login.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        password_login.setCompoundDrawablePadding(50);
                        password_login.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        pwdVisible[0] = false;
                    } else{
                        //Set the drawable Img
                        password_login.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_key,0,R.drawable.ic_visibility,0);
                        //Unhidden the Password
                        password_login.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        password_login.setCompoundDrawablePadding(50);
                        password_login.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        pwdVisible[0] = true;
                    }
                    password_login.setSelection(password_login.length());
                }
            }

            return false;
        });
        personImage.setOnClickListener(view -> {
            Intent intent = new Intent(Login_page.this,user_Registration.class);
            startActivity(intent);

        });

        Login.setOnClickListener(view -> {
            password1 = password_login.getText().toString();
            email1 = email_login.getText().toString();
            if(password1.isEmpty()){
                password_login.setError("Please give your password");
                Toast.makeText(this, "password"+password_login.length(), Toast.LENGTH_SHORT).show();
            }
            if(email1.isEmpty()){
                email_login.setError("Please give your email");
                Toast.makeText(this, "email"+email_login.length(), Toast.LENGTH_SHORT).show();
            }
            else{
//                FirebaseUser user = ne

                Login();
            }

        });
    }
   private void Login(){
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(this,task ->{
            if(task.isSuccessful()){
                HomePage();
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Login_page.this,""+task.getException(),Toast.LENGTH_LONG).show();
            }
        } );

    }
private void HomePage(){
        Intent intent = new Intent(Login_page.this,MainActivity.class);
        intent.putExtra(UserType,spinner2.getSelectedItem().toString());
        startActivity(intent);
        finish();
}



}