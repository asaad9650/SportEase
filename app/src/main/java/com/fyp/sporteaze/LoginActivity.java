package com.fyp.sporteaze;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.fyp.sporteaze.Academy.AcademyLogin;
import com.fyp.sporteaze.Admin.AdminLogin;
import com.fyp.sporteaze.Event.UserShowPost;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.User.DashboardActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private AppCompatButton login;
    private AppCompatButton login_as_admin;
    private AppCompatButton login_as_academy;
    private EditText email, password;
    DatabaseReference databaseReference;
    private TextView register, forgotPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    BackPressDialog backPressDialog = new BackPressDialog();
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            backPressDialog.exit_application(this);

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("SporteazePref", MODE_PRIVATE);

//        if(sharedPreferences.getBoolean("login", false)){
//            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
//            startActivity(intent);
//        }

        databaseReference = FirebaseDatabase.getInstance().getReference();

        email = findViewById(R.id.email);
        password = findViewById(R.id.txtPassword);
        login_as_admin = findViewById(R.id.login_as_admin);
        forgotPassword = findViewById(R.id.forgot_password);
        login_as_academy = findViewById(R.id.login_as_academyy);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
//                finish();
            }
        });
        register = findViewById(R.id.register);

        login = findViewById(R.id.login);

//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
////                intent.putExtra("user_email" , email.getText().toString());
////                intent.putExtra("user_name", "SaadAmir");
////                startActivity(intent);
//            }
//        });

        login.setOnClickListener(v -> {
            if(email.getText().toString().trim() != "" && password.getText().toString().trim() != "" && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                databaseReference.child("Users").orderByChild("email").equalTo(email.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot user: snapshot.getChildren()){
                                User userMod = user.getValue(User.class);
                                String team_id = user.child("team_id").getValue(String.class);
//                                Toast.makeText(LoginActivity.this, team_id, Toast.LENGTH_SHORT).show();
                                if(userMod.password.equals(password.getText().toString().trim())){
                                  //  editor = sharedPreferences.edit();
                                  //  editor.putBoolean("login", true);
                                  //  editor.commit();
                                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
//                                    if(userMod.captain.matches("yes")){
                                        Intent intent = new Intent(LoginActivity.this, UserShowPost.class);
                                        intent.putExtra("user_email" , userMod.email);
                                        intent.putExtra("user_name", userMod.name);
                                        intent.putExtra("captain" , userMod.captain);
                                        intent.putExtra("user_id", userMod.user_id);
                                        intent.putExtra("user_dob", userMod.dob);
                                        intent.putExtra("user_phone" , userMod.phone);
                                        intent.putExtra("user_address" , userMod.address);
                                        intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                                        startActivity(intent);
                                        finish();
//                                    }
//                                    else{
//                                        Intent intent = new Intent(LoginActivity.this, UserShowPost.class);
//                                        intent.putExtra("user_email" , userMod.email);
//                                        intent.putExtra("user_name", userMod.name);
//                                        intent.putExtra("captain" , userMod.captain);
//                                        intent.putExtra("user_id", userMod.user_id);
//                                        intent.putExtra("user_dob", userMod.dob);
//                                        intent.putExtra("user_phone" , userMod.phone);
//                                        intent.putExtra("user_address" , userMod.address);
//                                        intent.putExtra("team_id" , team_id);
//
////                                    intent.putExtra("user_id",userMod.user_id);
//                                        startActivity(intent);
//                                        finish();
//                                    }

                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Incorrect credentials.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Incorrect credentials.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "Please enter correct credentials.", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
//                finish();
            }
        });

        login_as_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , AdminLogin.class);
//                Intent intent = new Intent(LoginActivity.this , AdminHome.class);
                startActivity(intent);
//                finish();
            }
        });

        login_as_academy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this , AcademyLogin.class);
//                Intent intent = new Intent(LoginActivity.this , AcademyHome.class);

                startActivity(intent);
//                finish();
            }
        });
    }

    private void updateUI(FirebaseUser user) {

    }


}