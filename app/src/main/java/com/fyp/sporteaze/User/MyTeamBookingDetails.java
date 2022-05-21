package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.Model.GroundBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyTeamBookingDetails extends AppCompatActivity {
    ConstraintLayout coach_booking_box, ground_booking_box;
    String user_id , user_name, user_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team_booking_details);

        coach_booking_box = findViewById(R.id.coach_booking_box_team);
        ground_booking_box = findViewById(R.id.ground_booking_box_team);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_email = extras.getString("user_email");
        user_name = extras.getString("user_name");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


        coach_booking_box.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                DatabaseReference ref_ = FirebaseDatabase.getInstance().getReference();
                if(!user_name.matches("")){
                    ref_.child("coach_bookings").orderByChild("coach_booked_by").equalTo(user_name).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                            for(DataSnapshot snapshot : snapshot1.getChildren())
                            {

                                CoachBooking coachBooking = snapshot.getValue(CoachBooking.class);
                                if(!coachBooking.getCoach_booking_purpose().matches("individual")) {
                                    Intent intent1 = new Intent(MyTeamBookingDetails.this, TeamCoachBookingDetails.class);
                                    intent1.putExtra("user_id", user_id);
                                    intent1.putExtra("user_email", user_email);
                                    intent1.putExtra("user_name", user_name);
                                    intent1.putExtra("coach_name",coachBooking.getCoach_name() );
                                    intent1.putExtra("coach_charges",coachBooking.getCoach_charges() );
                                    intent1.putExtra("coach_booking_status", coachBooking.getCoach_request_status());
                                    intent1.putExtra("coach_expertise", coachBooking.getCoach_expertise());
                                    intent1.putExtra("coach_age", coachBooking.getCoach_age());
                                    startActivity(intent1);
                                }
                                else{
                                    Toast.makeText(MyTeamBookingDetails.this, "You don't have any bookings yet.", Toast.LENGTH_SHORT).show();
                                }

//                                if(!coachBooking.getCoach_booking_purpose().matches("individual")){
//                                    Toast.makeText(MyTeamBookingDetails.this, coachBooking.getCoach_name().toString(), Toast.LENGTH_SHORT).show();
//                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    ref_.child("coach_bookings").orderByChild("coach_booked_by").equalTo(user_email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Toast.makeText(MyTeamBookingDetails.this, snapshot.toString(), Toast.LENGTH_SHORT).show();

                            for(DataSnapshot snapshot1 : snapshot.getChildren()){
                                CoachBooking coachBooking = snapshot.getValue(CoachBooking.class);
                                if(!coachBooking.getCoach_booking_purpose().matches("individual")) {
                                    Intent intent1 = new Intent(MyTeamBookingDetails.this, TeamCoachBookingDetails.class);
                                    intent1.putExtra("user_id", user_id);
                                    intent1.putExtra("user_email", user_email);
                                    intent1.putExtra("user_name", user_name);
                                    intent1.putExtra("coach_name",coachBooking.getCoach_name() );
                                    intent1.putExtra("coach_charges",coachBooking.getCoach_charges() );
                                    intent1.putExtra("coach_booking_status", coachBooking.getCoach_request_status());
                                    intent1.putExtra("coach_expertise", coachBooking.getCoach_expertise());
                                    startActivity(intent1);
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

        ground_booking_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref_ = FirebaseDatabase.getInstance().getReference();
                ref_.child("ground_bookings").child(user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            GroundBooking groundBooking = snapshot.getValue(GroundBooking.class);
                            Toast.makeText(MyTeamBookingDetails.this, groundBooking.ground_name, Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(MyTeamBookingDetails.this , TeamGroundBookingDetails.class);
                            intent1.putExtra("user_id", user_id);
                            intent1.putExtra("user_email", user_email);
                            intent1.putExtra("user_name", user_name);
                            intent1.putExtra("ground_name",groundBooking.getGround_name() );
                            intent1.putExtra("ground_charges",groundBooking.getGround_charges() );
                            intent1.putExtra("ground_booking_status", groundBooking.getGround_request_status());
                            intent1.putExtra("ground_location", groundBooking.getGround_location());
                            intent1.putExtra("ground_size" , groundBooking.getGround_size());
                            startActivity(intent1);

                        }
                        else{
                            Toast.makeText(MyTeamBookingDetails.this, "You do not have any bookings yet.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}