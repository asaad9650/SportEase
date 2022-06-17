package com.fyp.sporteaze.Academy.RentRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.Model.GroundBooking;
import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.TeamGroundBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GroundRentRequest extends AppCompatActivity {
    List<TeamGroundBooking> groundRentList;
    RecyclerView recyclerView;
    GroundRentAdapter groundRentAdapter;
    DatabaseReference databaseReference;
    String academy_email , academy_id, academy_name;

    TextView text_no_ground_request ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_rent_request);

        recyclerView = findViewById(R.id.ground_rent_request_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        text_no_ground_request = findViewById(R.id.text_no_ground_request);
        groundRentList = new ArrayList<>();
        recyclerView.setVisibility(View.GONE);
        text_no_ground_request.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");


        databaseReference = FirebaseDatabase.getInstance().getReference("team_ground_bookings");





        databaseReference.orderByChild("academy_id").equalTo(academy_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {

                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
                    TeamGroundBooking us = dataSnapshot.getValue(TeamGroundBooking.class);
                    if(us.getRequest_status().matches("pending")) {
                        groundRentList.add(us);
                        recyclerView.setVisibility(View.VISIBLE);
                        text_no_ground_request.setVisibility(View.GONE);
                        groundRentAdapter = new GroundRentAdapter(groundRentList);
                        recyclerView.setAdapter(groundRentAdapter);
                        if (groundRentList.size() == 0) {
                            text_no_ground_request.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//        databaseReference.orderByChild("ground_request_status").equalTo("pending").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds: snapshot.getChildren()){
//                    GroundBooking us = ds.getValue(GroundBooking.class);
//                    groundRentList.add(us);
//                }
//
//                text_no_ground_request.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.VISIBLE);
//                groundRentAdapter = new GroundRentAdapter(groundRentList);
//                recyclerView.setAdapter(groundRentAdapter);
//                //academyHelperAdapter.notifyDataSetChanged();
//                if(groundRentList.size()== 0){
//                    text_no_ground_request.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
}