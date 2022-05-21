package com.fyp.sporteaze.Academy.Ground;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.fyp.sporteaze.Academy.Coach.ViewCoachAdapter;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.Ground;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcademyViewGround extends AppCompatActivity {
    List<Ground> groundList;
    RecyclerView recyclerView;
    ViewGroundAdapter viewGroundAdapter;
    DatabaseReference databaseReference;
    String academy_email , academy_id, academy_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_view_ground);
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");


        recyclerView = findViewById(R.id.grounds_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        groundList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("grounds");

        databaseReference.orderByChild("academy_id").equalTo(academy_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Ground us = ds.getValue(Ground.class);
                    groundList.add(us);
                }
                viewGroundAdapter = new ViewGroundAdapter(groundList , academy_id,academy_email);
                recyclerView.setAdapter(viewGroundAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}