package com.example.ufood;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ufood.Model.food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OfferFoodActivity extends AppCompatActivity {
    ImageButton AddItem;
    LinearLayout layout;
    RadioButton vegItem,nonVegItem;
    EditText noPeople,description,country,city,street,houseNo,landmark,pin;
    Button post;
    String foodType = "";
    List<String> item = new ArrayList<>();

    private final FirebaseUser OfferAuth = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_food);
        //Getting the element attach with java variable
        AddItem = findViewById(R.id.addItem);
        layout = findViewById(R.id.item);
        post = findViewById(R.id.post);

        vegItem = findViewById(R.id.vegItem);
        nonVegItem = findViewById(R.id.nonVegItem);

        noPeople = findViewById(R.id.noPeople);
        description = findViewById(R.id.description);
        country = findViewById(R.id.country);
        city = findViewById(R.id.city);
        street = findViewById(R.id.street);
        houseNo = findViewById(R.id.houseNo);
        landmark = findViewById(R.id.landmark);
        pin = findViewById(R.id.pin);
        AddItem.setOnClickListener(view -> buildDialog());
        post.setOnClickListener(view -> {
            if (vegItem.isChecked()) {
                foodType = "veg";
            } else if (nonVegItem.isChecked()) {
                foodType = "Non-veg";
            }
            try {

                postToFirebase();
            }catch (Exception exception){
                exception.printStackTrace();
                Toast.makeText(this, ""+exception, Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void buildDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(OfferFoodActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_input,null);
        EditText addFood = view.findViewById(R.id.addFood);
        builder.setView(view);
        builder.setTitle("Enter Food")
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    addCard(addFood.getText().toString());
                    item.add(addFood.getText().toString());
                    Toast.makeText(OfferFoodActivity.this, ""+item, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {

                });
       final AlertDialog dialog =  builder.create();
       dialog.show();
       dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.Blue));
       dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.Blue));
    }
    public void addCard(String addFood){
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.item,null);
        TextView foodTxt = view.findViewById(R.id.foodTxt);
        ImageButton delete = view.findViewById(R.id.delete);
        foodTxt.setText(addFood);

        delete.setOnClickListener(view1 -> layout.removeView(view));
        layout.addView(view);
    }
    public void postToFirebase() {



            String servePeople = noPeople.getText().toString();
            String Desc = description.getText().toString();
            String Country = country.getText().toString();
            String City = city.getText().toString();
            String Street = street.getText().toString();
            String HouseNo = houseNo.getText().toString();
            String Landmark = landmark.getText().toString();
            String Pin = pin.getText().toString();

        FirebaseDatabase postDatabase = FirebaseDatabase.getInstance();
        DatabaseReference postReference =postDatabase.getReference().child("offer-food").child(OfferAuth.getUid());
        food Food = new food(foodType,servePeople,Desc,Country,City,Street,HouseNo,Landmark,Pin, (ArrayList<String>) item);
        postReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        postReference.child("post").setValue(Food);
                        Toast.makeText(OfferFoodActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(OfferFoodActivity.this, "Failed", Toast.LENGTH_SHORT).show();

                }
            });

        }

}