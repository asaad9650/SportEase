package com.fyp.sporteaze.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fyp.sporteaze.R;
import com.fyp.sporteaze.Teams.TeamRentCoach;
import com.fyp.sporteaze.Teams.TeamRentGround;

public class UsersBookings extends AppCompatActivity {

    ConstraintLayout coach_booking_box, ground_booking_box , academy_booking_box;
    String user_id , user_name, user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_bookings);

        coach_booking_box = findViewById(R.id.coach_booking_box);
        ground_booking_box = findViewById(R.id.ground_booking_box);
        academy_booking_box = findViewById(R.id.academy_booking_box);
        academy_booking_box.setVisibility(View.GONE);

//        academy_booking_box.setVisibility(View.GONE);
        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_email = extras.getString("user_email");
        user_name = extras.getString("user_name");



        coach_booking_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UsersBookings.this, TeamRentCoach.class);
                intent.putExtra("user_id" , user_id);
                intent.putExtra("user_email" , user_email);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });

        ground_booking_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(UsersBookings.this, TeamRentGround.class);
                intent.putExtra("user_id" , user_id);
                intent.putExtra("user_email" , user_email);
                intent.putExtra("user_name", user_name);
                startActivity(intent);
            }
        });


//        academy_booking_box.setOnClickListener(new View.OnClickListener() {
//
//
//
//            @Override
//            public void onClick(View view) {
//
////                Toast.makeText(UsersBookings.this, snapshot.getValue(String.class), Toast.LENGTH_SHORT).show();
//
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id).child("team_id");
//
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        Intent intent= new Intent(UsersBookings.this, MyTeamBookingDetails.class);
//                        intent.putExtra("user_id" , user_id);
//                        intent.putExtra("user_email" , user_email);
//                        intent.putExtra("user_name", user_name);
//                        intent.putExtra("team_id", snapshot.getValue(String.class));
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
////
//            }
//        });
    }
}