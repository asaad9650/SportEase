package com.fyp.sporteaze.Academy.Coach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fyp.sporteaze.Admin.AcademyHelperAdapter;
import com.fyp.sporteaze.Admin.DataHolder;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcademyViewCoaches extends AppCompatActivity {
    List<Coach> coachList;
    RecyclerView recyclerView;
    ViewCoachAdapter viewCoachAdapter;
    DatabaseReference databaseReference;
    String academy_email , academy_id, academy_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_view_coaches);

        recyclerView = findViewById(R.id.coaches_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        coachList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");



        databaseReference = FirebaseDatabase.getInstance().getReference("coaches");

        databaseReference.orderByChild("academy_id").equalTo(academy_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Coach us = ds.getValue(Coach.class);
                    coachList.add(us);
                }
                viewCoachAdapter = new ViewCoachAdapter(coachList , academy_id , academy_email);
                recyclerView.setAdapter(viewCoachAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}