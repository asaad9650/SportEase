package com.fyp.sporteaze.Academy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.sporteaze.Academy.RentRequest.AcademyHome;
import com.fyp.sporteaze.Admin.DataHolder;
import com.fyp.sporteaze.BackPressDialog;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AcademyLogin extends AppCompatActivity {
AppCompatButton btn_login_as_academy;
EditText et_academy_name_or_email;
EditText et_academy_password;
    DatabaseReference databaseReference;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_login);

        btn_login_as_academy = findViewById(R.id.btn_login_as_academy);
        et_academy_name_or_email = findViewById(R.id.et_academy_name_or_email);
        et_academy_password = findViewById(R.id.et_academy_password_2);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences("SporteazePref", MODE_PRIVATE);



        btn_login_as_academy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_academy_name_or_email.getText().toString().matches("") || et_academy_password.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Please enter email and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("academies").orderByChild("academy_email").equalTo(et_academy_name_or_email.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                for (DataSnapshot user : snapshot.getChildren()) {
                                    DataHolder dataHolder = user.getValue(DataHolder.class);

                                    if (dataHolder.getAcademy_password().matches(et_academy_password.getText().toString())){
                                        editor = sharedPreferences.edit();
                                        editor.putBoolean("login", true);
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AcademyLogin.this, AcademyDashboard.class);
                                        intent.putExtra("academy_email", et_academy_name_or_email.getText().toString());
                                        intent.putExtra("academy_id" , dataHolder.getAcademy_id());
                                        intent.putExtra("academy_name", dataHolder.getAcademy_name());
                                        Toast.makeText(AcademyLogin.this, dataHolder.getAcademy_id(), Toast.LENGTH_SHORT).show();
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Incorrect password or username.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });




    }
}