package com.fyp.sporteaze.Admin;

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

import com.fyp.sporteaze.BackPressDialog;
import com.fyp.sporteaze.Model.Admin;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {
    private AppCompatButton btn_login_as_admin;
    private EditText et_email;
    private EditText et_password;
    DatabaseReference databaseReference;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences("SporteazePref", MODE_PRIVATE);


        et_email=  findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_login_as_admin = findViewById(R.id.login_as_admin);


        btn_login_as_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_email.getText().toString().trim() != "" && et_password.getText().toString().trim()!="")
                {
                        databaseReference.child("admin").orderByChild("email").equalTo(et_email.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                //Toast.makeText(getApplicationContext()  , snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                if (snapshot.exists()) {
                                for (DataSnapshot user : snapshot.getChildren()) {
                                    Admin admin = user.getValue(Admin.class);

                                    if (admin.password.equals(et_password.getText().toString().trim())) {
                                        editor = sharedPreferences.edit();
                                        editor.putBoolean("login", true);
                                        editor.commit();
                                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(AdminLogin.this, AdminHome.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Incorrect credentials.", Toast.LENGTH_SHORT).show();
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