package com.fyp.sporteaze.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserEventViewSchedule extends AppCompatActivity {
    ConstraintLayout view_past_matches, view_upcoming_matches;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    String user_name, user_email, user_id , captain , team_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_view_schedule);
        view_past_matches = findViewById(R.id.view_past_matches_box);
        view_upcoming_matches = findViewById(R.id.view_upcoming_matches_box);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");



        view_past_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEventViewSchedule.this , UserEventViewPastMatches.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("user_email" , user_email);
                intent.putExtra("team_id" , team_id);
                startActivity(intent);

            }
        });

        view_upcoming_matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}