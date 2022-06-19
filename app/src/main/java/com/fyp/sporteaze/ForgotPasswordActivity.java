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

import com.fyp.sporteaze.Support.JavaMailAPI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    String keyMain;
    AppCompatButton submit;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//https://api.openweathermap.org/data/2.5/weather?q=karachi&appid=f23057d8b3333b8dabc35e7f5381e241

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.forgot_password_email);
        submit = findViewById(R.id.forgot_password_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim() != "" && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    databaseReference.child("Users").orderByChild("email").equalTo(email.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                                    keyMain = snapshot1.getKey();
                                    try {
                                        Toast.makeText(ForgotPasswordActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                        int code = Integer.parseInt(String.format("%04d", new Random().nextInt(10000)));
                                        JavaMailAPI javaMailAPI = new JavaMailAPI(ForgotPasswordActivity.this,
                                                email.getText().toString().trim(),
                                                "Password Reset For Sporteaze",
                                                "Your password reset verification code for Sporteaze is " + code + ".\nPlease ignore this email if you have not requested any password reset code" );
                                        javaMailAPI.execute();
                                        Intent intent = new Intent(ForgotPasswordActivity.this, CodeActivity.class);
                                        intent.putExtra("code", code);
                                        intent.putExtra("key", keyMain);
                                        startActivity(intent);
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Email does not exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter proper credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}