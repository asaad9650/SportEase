package com.fyp.sporteaze.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminShowAcademies extends AppCompatActivity {
    List<DataHolder> academy_list;
    RecyclerView recyclerView;
    AcademyHelperAdapter academyHelperAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_academies);

        recyclerView = findViewById(R.id.acadmies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        academy_list = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference("academies");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    DataHolder us = ds.getValue(DataHolder.class);
                    academy_list.add(us);
                }
                academyHelperAdapter = new AcademyHelperAdapter(academy_list);
                recyclerView.setAdapter(academyHelperAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}