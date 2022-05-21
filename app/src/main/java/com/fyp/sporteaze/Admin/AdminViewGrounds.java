package com.fyp.sporteaze.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class AdminViewGrounds extends AppCompatActivity {

    List<Ground> groundList;

    RecyclerView view_ground_recyclerview;
    AdminGroundAdapter adminGroundAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_grounds);


        view_ground_recyclerview = findViewById(R.id.view_ground_recyclerview);
        view_ground_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        groundList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();



        databaseReference.child("grounds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Ground us = ds.getValue(Ground.class);
                    groundList.add(us);
                }
                adminGroundAdapter = new AdminGroundAdapter(groundList);
                view_ground_recyclerview.setAdapter(adminGroundAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}