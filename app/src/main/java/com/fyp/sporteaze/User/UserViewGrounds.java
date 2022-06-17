package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fyp.sporteaze.Academy.Ground.ViewGroundAdapter;
import com.fyp.sporteaze.Model.Ground;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewGrounds extends AppCompatActivity {

    List<Ground> groundList;
    String user_id, user_name, user_email , captain , team_id;
    RecyclerView grounds_recycler_view;
    UserGroundAdapter viewGroundAdapter;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_grounds);

        grounds_recycler_view = findViewById(R.id.grounds_recycler_view);
        grounds_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        groundList = new ArrayList<>();

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");


        databaseReference = FirebaseDatabase.getInstance().getReference("grounds");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    Ground us = ds.getValue(Ground.class);
                    groundList.add(us);
                }
                viewGroundAdapter = new UserGroundAdapter(groundList , user_id , user_name, user_email , captain , team_id);
                grounds_recycler_view.setAdapter(viewGroundAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}