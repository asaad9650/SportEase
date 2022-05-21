package com.fyp.sporteaze.Academy.RentRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Academy.Coach.ViewCoachAdapter;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CoachRentRequest extends AppCompatActivity {
    String academy_email , academy_id, academy_name;
    List<CoachBooking> coachRentList;
    RecyclerView recyclerView;
    CoachRentAdapter coachRentAdapter;
    DatabaseReference databaseReference2;
    TextView text_no_coach_request ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_rent_request);

        recyclerView = findViewById(R.id.coach_rent_request_recycler_view);
        text_no_coach_request = findViewById(R.id.text_no_coach_request);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coachRentList = new ArrayList<>();
        recyclerView.setVisibility(View.GONE);
        text_no_coach_request.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");
        databaseReference2 = FirebaseDatabase.getInstance().getReference("coach_bookings");
        databaseReference2.orderByChild("academy_id").equalTo(academy_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {

                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
//                    String status = (String) dataSnapshot.child("status").getValue();

//                    if(status.matches("pending")) {
                        CoachBooking us = dataSnapshot.getValue(CoachBooking.class);
                        if(us.getCoach_request_status().matches("pending")) {
                            coachRentList.add(us);
                            recyclerView.setVisibility(View.VISIBLE);
                            text_no_coach_request.setVisibility(View.GONE);
                            coachRentAdapter = new CoachRentAdapter(coachRentList);
                            recyclerView.setAdapter(coachRentAdapter);
                            if (coachRentList.size() == 0) {
                                text_no_coach_request.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
//                        }
                    }


//                    String key = (String) dataSnapshot.child("coach_booking_id").getValue();
//                    if (key != null) {
//                        Toast.makeText(CoachRentRequest.this, key.toString(), Toast.LENGTH_LONG).show();
//                    }
                }
//                databaseReference2.orderByChild("coach_request_status").equalTo("pending").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot ds: snapshot.getChildren()){
//                            CoachBooking us = ds.getValue(CoachBooking.class);
//                            coachRentList.add(us);
//                        }
//                        recyclerView.setVisibility(View.VISIBLE);
//                        text_no_coach_request.setVisibility(View.GONE);
//                        coachRentAdapter = new CoachRentAdapter(coachRentList);
//                        recyclerView.setAdapter(coachRentAdapter);
//                        if(coachRentList.size()== 0){
//                            text_no_coach_request.setVisibility(View.VISIBLE);
//                            recyclerView.setVisibility(View.GONE);
//                        }
//
//                        //academyHelperAdapter.notifyDataSetChanged();
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}