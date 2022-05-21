package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.fyp.sporteaze.Admin.AcademyHelperAdapter;
import com.fyp.sporteaze.Admin.DataHolder;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserShowAcademies extends AppCompatActivity {

    List<DataHolder> academy_list;
    RecyclerView recyclerView;
    UserAcademyAdapter userAcademyAdapter;
    DatabaseReference databaseReference;
    String user_id, team_id , individual_academy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show_academies);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
//        user_name = extras.getString("user_name");
        user_id = extras.getString("user_id");
        team_id = extras.getString("team_id");
        individual_academy = extras.getString("individual_academy");

        recyclerView = findViewById(R.id.users_academies_recycler_view);
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
                if(individual_academy!=null && !individual_academy.matches("")){
                    userAcademyAdapter = new UserAcademyAdapter(academy_list, user_id,team_id , individual_academy);

                }
                else{
                    userAcademyAdapter = new UserAcademyAdapter(academy_list, user_id,"" , "");
                }


//                if(individual_academy!=null && !individual_academy.matches("")){
//                    userAcademyAdapter = new UserAcademyAdapter(academy_list , user_id , individual_academy);
//                }
//                if(team_id!=null && team_id != "" && individual_academy != null && !individual_academy.matches("")){
//                }
//                else{
//                    userAcademyAdapter = new UserAcademyAdapter(academy_list, user_id,"" , "");
//                }

                recyclerView.setAdapter(userAcademyAdapter);
                //academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}