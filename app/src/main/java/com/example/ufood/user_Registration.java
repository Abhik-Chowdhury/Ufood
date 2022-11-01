package com.example.ufood;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import com.example.ufood.Model.CircleTransform;
import com.example.ufood.Model.UserHolder;


public class user_Registration extends AppCompatActivity {
    ImageView profile_pic;
    ImageView add_image;
    Spinner spinner;
    ArrayList<String> arrayList = new ArrayList<>();
    ProgressBar progressBar;
    EditText Full_name,Email,Phone,Password,Confirm_password;
    Uri uri;
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        // Getting attachment of the filed layout
        profile_pic = findViewById(R.id.profile_pic);
        add_image = findViewById(R.id.add_image);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        Full_name = findViewById(R.id.Full_name);
        Email = findViewById(R.id.email);
        Phone = findViewById(R.id.Phone);
        Password = findViewById(R.id.password);
        Confirm_password = findViewById(R.id.confirm_password);

        spinner = findViewById(R.id.spinner);
        arrayList.add("User");
        arrayList.add("Food-Offer");
        ArrayAdapter<String> userType = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, arrayList);
        spinner.setAdapter(userType);

        Button Register = findViewById(R.id.register);
        Register.setOnClickListener(view -> {

              if(uri !=null){

                  registerFirebase(uri);
              }

        });




        add_image.setOnClickListener(view -> ImagePicker.Companion.with(user_Registration.this)
                .cropSquare()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start(10));




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10) {
            assert data != null;
            uri =  data.getData();
            assert uri != null;
            Toast.makeText(this, ""+uri, Toast.LENGTH_SHORT).show();
            Picasso.get()
                    .load(uri)
                    .centerCrop()
                    .transform(new CircleTransform())
                    .resize(200,200)
                    .into(profile_pic);

            profile_pic.setImageURI(uri);


        }

    }

    public void registerFirebase(Uri uri){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(user_Registration.this, task -> {
                 if(task.isSuccessful()){
                     StorageReference image_upload = FirebaseStorage.getInstance().getReference("Image");
                     StorageReference FileRef = image_upload.child(System.currentTimeMillis()+"."+getFile(uri));
                     FileRef.putFile(uri)
                             .addOnSuccessListener(taskSnapshot -> FileRef.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                 UserHolder userHolder = new UserHolder(Full_name.getText().toString(),Email.getText().toString(),Phone.getText().toString(),Password.getText().toString(),uri1.toString(),spinner.getSelectedItem().toString());
                                 String type = spinner.getSelectedItem().toString();
                                 if(type.equals("User")){
                                     FirebaseDatabase.getInstance().getReference("user")
                                             .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                             .setValue(userHolder).addOnCompleteListener(task1 -> Toast.makeText(user_Registration.this, "User Created", Toast.LENGTH_SHORT).show());
                                     progressBar.setVisibility(View.INVISIBLE);
                                 }
                                 else if(type.equals("Food-Offer")){
                                     FirebaseDatabase.getInstance().getReference("offer-food")
                                             .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                             .setValue(userHolder).addOnCompleteListener(task12 -> Toast.makeText(user_Registration.this, "Food offer Created", Toast.LENGTH_SHORT).show());
                                     progressBar.setVisibility(View.INVISIBLE);
                                 }
                                 else {
                                     Toast.makeText(user_Registration.this, "Food Offer not made", Toast.LENGTH_SHORT).show();
                                 }
                             }))
                             .addOnProgressListener(snapshot -> progressBar.setVisibility(View.VISIBLE))
                             .addOnFailureListener(e -> {
                                 progressBar.setVisibility(View.INVISIBLE);
                                 Toast.makeText(user_Registration.this, "Failed", Toast.LENGTH_SHORT).show();
                             });

                 }
                 else {
                     Toast.makeText(user_Registration.this,"Problem"+task.getException(),Toast.LENGTH_SHORT).show();
                 }
                });

     }


    private String getFile(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(mUri));
    }




}