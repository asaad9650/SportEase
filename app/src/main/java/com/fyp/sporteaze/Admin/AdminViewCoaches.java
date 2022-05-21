package com.fyp.sporteaze.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fyp.sporteaze.Academy.Coach.ViewCoachAdapter;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminViewCoaches extends AppCompatActivity {

    List<Coach> coachList;

    RecyclerView view_coach_recyclerview;
    AdminViewCoachesAdapter viewCoachesAdapter;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_coaches);

        view_coach_recyclerview = findViewById(R.id.view_coach_recyclerview);
        view_coach_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        coachList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();



        databaseReference.child("coaches").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Coach us = ds.getValue(Coach.class);
                    coachList.add(us);
                }
                viewCoachesAdapter = new AdminViewCoachesAdapter(coachList);
                view_coach_recyclerview.setAdapter(viewCoachesAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}