package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IndividualCoachBooking extends AppCompatActivity {
    String user_id , user_name , user_email;

    List<Coach> coachList;
    RecyclerView coaches_recycler_view;
    IndividualCoachAdapter individualCoachAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_coach_booking);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");


        coaches_recycler_view = findViewById(R.id.view_coaches_recycler_view);
        coaches_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        coachList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("coaches");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Coach us = ds.getValue(Coach.class);
                    coachList.add(us);
                }
                individualCoachAdapter = new IndividualCoachAdapter(coachList , user_id , user_name, user_email);
                coaches_recycler_view.setAdapter(individualCoachAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}