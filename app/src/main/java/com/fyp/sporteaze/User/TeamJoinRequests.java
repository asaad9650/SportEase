package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Model.JoinTeam;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamJoinRequests extends AppCompatActivity {
    List<JoinTeam> joinTeamList;
    TeamJoinRequestAdapter teamJoinRequestAdapter;
    DatabaseReference databaseReference;
    DatabaseReference root ;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob;
    RecyclerView join_request_recycler_view;
    String team_name;
    TextView no_join_requests;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_join_requests);
        Intent intent = getIntent();
        no_join_requests = findViewById(R.id.no_join_requests);

        join_request_recycler_view = findViewById(R.id.team_joinn_recycler);

        join_request_recycler_view.setVisibility(View.GONE);
        no_join_requests.setVisibility(View.VISIBLE);


        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");
        user_phone = extras.getString("user_phone");
        user_address = extras.getString("user_address");
        user_dob = extras.getString("user_dob");

        join_request_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        joinTeamList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("team_join_request");
        root = FirebaseDatabase.getInstance().getReference();

        root.child("teams").child(team_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team_name = snapshot.child("team_name").getValue(String.class);
                team_id = snapshot.child("team_id").getValue(String.class);
                Toast.makeText(TeamJoinRequests.this, team_id, Toast.LENGTH_SHORT).show();
                databaseReference.orderByChild("team_name").equalTo(team_name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                        Toast.makeText(TeamJoinRequests.this, "This is data: " + snapshot1, Toast.LENGTH_SHORT).show();
                        for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                            JoinTeam joinTeam =  snapshot2.getValue(JoinTeam.class);
                            joinTeamList.add(joinTeam);
                            String key = snapshot2.getKey();
//                            Toast.makeText(TeamJoinRequests.this, snapshot2.getKey(), Toast.LENGTH_LONG).show();
                            teamJoinRequestAdapter = new TeamJoinRequestAdapter(joinTeamList , key , team_id , user_id);
                            join_request_recycler_view.setAdapter(teamJoinRequestAdapter);
                            join_request_recycler_view.setVisibility(View.VISIBLE);
                            no_join_requests.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                Toast.makeText(TeamJoinRequests.this, team_name, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}