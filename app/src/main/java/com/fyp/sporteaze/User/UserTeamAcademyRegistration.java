package com.fyp.sporteaze.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;

public class UserTeamAcademyRegistration extends AppCompatActivity {

    DatabaseReference databaseReference;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_team_academy_registration);
        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");

    }
}