package com.fyp.sporteaze;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.fyp.sporteaze.Model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    AppCompatButton register;
    EditText name,email,password,cnic,phone,dob,address,gender;
    private DatabaseReference mDatabase;
    private DatabaseReference ref;
    RadioButton checkbox_male ,checkbox_female;

    DatabaseReference user_check_ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref = FirebaseDatabase.getInstance().getReference();
        user_check_ref = FirebaseDatabase.getInstance().getReference();

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.txtPassword);
        cnic = findViewById(R.id.cnic_number);
        phone = findViewById(R.id.phone_number);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        checkbox_female = findViewById(R.id.checkbox_female);
        checkbox_male = findViewById(R.id.checkbox_male);
        register = findViewById(R.id.register);

        checkbox_male.setChecked(true);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){

                    if(name.getText().toString().trim().matches("")|| email.getText().toString().trim().matches("") || password.getText().toString().trim().matches("") ||phone.getText().toString().trim().matches("") || dob.getText().toString().trim().matches("") || cnic.getText().toString().trim().matches("") || address.getText().toString().trim().matches("") ){
                        Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        if(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                            user_check_ref.child("Users").orderByChild("email").equalTo(email.getText().toString().trim()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){
                                        Toast.makeText(RegistrationActivity.this, "This email is already registered, please try with different email or try resetting your password.", Toast.LENGTH_SHORT).show();
                                    }
                                    else{

                                        if(checkbox_male.isChecked()){
                                            String key = mDatabase.push().getKey();
                                            User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString(), cnic.getText().toString(), phone.getText().toString(), dob.getText().toString(), address.getText().toString(), checkbox_male.getText().toString().trim() , key,"","no", "no" , "");
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
                                        else if (checkbox_female.isChecked()){
                                            String key = mDatabase.push().getKey();
                                            User user = new User(name.getText().toString(), email.getText().toString(), password.getText().toString(), cnic.getText().toString(), phone.getText().toString(), dob.getText().toString(), address.getText().toString(),checkbox_female.getText().toString().trim() , key,"","no", "no" , "");
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
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

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