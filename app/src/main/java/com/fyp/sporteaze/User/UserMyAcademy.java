package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Academy_registration_individual;
import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserMyAcademy extends AppCompatActivity {
    String user_id,user_name,user_email;
    LinearLayout layout_my_coach , layout_no_coach;
    TextView my_coach_name, my_coach_age, my_coach_charges, my_coach_phone, my_coach_address, my_coach_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_academy);
        my_coach_name = findViewById(R.id.my_coach_name);
        my_coach_age = findViewById(R.id.my_coach_age);
        my_coach_charges = findViewById(R.id.my_coach_charges);

        my_coach_address = findViewById(R.id.my_coach_address);
        layout_no_coach = findViewById(R.id.layout_no_coach);
        layout_my_coach = findViewById(R.id.layout_my_coach);
        layout_my_coach.setVisibility(View.GONE);
        layout_no_coach.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        DatabaseReference refer = FirebaseDatabase.getInstance().getReference();
        refer.child("academy_individual_bookings").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Academy_registration_individual academy_registration_individual = snapshot.getValue(Academy_registration_individual.class);
                    Toast.makeText(UserMyAcademy.this, academy_registration_individual.getAcademy_name(), Toast.LENGTH_SHORT).show();

//                    String key = (String) snapshot.getValue();

//                    Toast.makeText(UserMyCoach.this, key, Toast.LENGTH_SHORT).show();
//                    DatabaseReference data_ref = FirebaseDatabase.getInstance().getReference();
//                    data_ref.child("coach_bookings").child(key).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            CoachBooking coachBooking = snapshot.getValue(CoachBooking.class);
                            layout_my_coach.setVisibility(View.VISIBLE);
                            layout_no_coach.setVisibility(View.GONE);
                            my_coach_name.setText("Academy Name: " + academy_registration_individual.getAcademy_name());
                            my_coach_age.setText("Academy ID: " + academy_registration_individual.getAcademy_id());
                            my_coach_address.setText("Status: " + academy_registration_individual.getRequest_status());
                            my_coach_charges.setText("Request ID: " + academy_registration_individual.getId());

//                            my_coach_phone.setText("Coach Phone: " + coachBooking.getCoach_phone());
//                            my_coach_status.setText("Coach Booking Status: " + coachBooking.getCoach_request_status());
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}