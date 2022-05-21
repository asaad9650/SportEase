package com.fyp.sporteaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.sporteaze.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    AppCompatButton register;
    EditText name,email,password,cnic,phone,dob,address,gender;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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
                if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() && !name.getText().toString().trim().matches("") && !email.getText().toString().trim().matches("") && !password.getText().toString().trim().matches("") ){

                    String key = mDatabase.push().getKey();

                    User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString(), cnic.getText().toString(), phone.getText().toString(), dob.getText().toString(), address.getText().toString(), gender.getText().toString() , key,"","no", "no");

                    mDatabase.child("Users").child(key).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
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
                    Toast.makeText(getApplicationContext(), "Please fill at least name, email and password .", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}