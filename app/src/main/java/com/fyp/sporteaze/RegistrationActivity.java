package com.fyp.sporteaze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.fyp.sporteaze.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    AppCompatButton register;
    EditText name,email,password,cnic,phone,dob,address,gender;
    private DatabaseReference mDatabase;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.txtPassword);
        cnic = findViewById(R.id.cnic_number);
        phone = findViewById(R.id.phone_number);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);

        register = findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){

                    if(name.getText().toString().trim().matches("")|| email.getText().toString().trim().matches("") || password.getText().toString().trim().matches("") ||phone.getText().toString().trim().matches("") || dob.getText().toString().trim().matches("") || cnic.getText().toString().trim().matches("") || address.getText().toString().trim().matches("")|| gender.getText().toString().trim().matches("") ){
                        Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                            String key = mDatabase.push().getKey();
                            User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString(), cnic.getText().toString(), phone.getText().toString(), dob.getText().toString(), address.getText().toString(), gender.getText().toString() , key,"","no", "no");
                            mDatabase.child("Users").child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    ref.child("Users").child(key).child("team_id").setValue("");
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(), "Registered successfully.", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "Email address is not formatted correctly", Toast.LENGTH_SHORT).show();
                        }
                    }
            }
        });
    }
}