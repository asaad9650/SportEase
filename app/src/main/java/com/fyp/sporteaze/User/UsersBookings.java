package com.fyp.sporteaze.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.Model.GroundBooking;
import com.fyp.sporteaze.R;
import com.fyp.sporteaze.Teams.TeamRentCoach;
import com.fyp.sporteaze.Teams.TeamRentGround;

public class UsersBookings extends AppCompatActivity {
    ConstraintLayout coach_booking_box, ground_booking_box , academy_booking_box;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_bookings);
        coach_booking_box = findViewById(R.id.coach_booking_box);
        ground_booking_box = findViewById(R.id.ground_booking_box);
        academy_booking_box = findViewById(R.id.academy_booking_box);
        academy_booking_box.setVisibility(View.GONE);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");
        user_phone = extras.getString("user_phone");
        user_address = extras.getString("user_address");
        user_dob = extras.getString("user_dob");

        coach_booking_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UsersBookings.this, UserViewCoach.class);
                intent.putExtra("user_email",user_email);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("captain", captain);
                intent.putExtra("team_id" , team_id);
                startActivity(intent);
            }
        });

        ground_booking_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UsersBookings.this, UserViewGrounds.class);
                intent.putExtra("user_email",user_email);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("captain", captain);
                intent.putExtra("team_id" , team_id);
                startActivity(intent);
            }
        });
    }
}