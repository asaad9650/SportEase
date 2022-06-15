package com.fyp.sporteaze.Event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AcceptedMatchRequest extends AppCompatActivity {
    RecyclerView accepted_match_recycler_view;
    List<CreateMatchBetweenTeams> createMatchBetweenTeamsList;
    ShowCreatedPostAdapter showCreatedPostAdapter;
    DatabaseReference databaseReference;
    DatabaseReference root ;
    TextView no_new_accepted_requests;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob;
    AcceptedMatchRequestAdapter acceptedMatchRequestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_match_request);

        accepted_match_recycler_view = findViewById(R.id.accepted_match_recycler_view);
        no_new_accepted_requests = findViewById(R.id.no_new_accepted_requests);
        no_new_accepted_requests.setVisibility(View.VISIBLE);
        accepted_match_recycler_view.setVisibility(View.GONE);
        Intent intent = getIntent();



        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");
        user_phone = extras.getString("user_phone");
        user_address = extras.getString("user_address");
        user_dob = extras.getString("user_dob");
        accepted_match_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        createMatchBetweenTeamsList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("create_match_between_teams").orderByChild("request_initiated_by").equalTo(team_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Toast.makeText(AcceptedMatchRequest.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
//                if (snapshot.exists()){
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    CreateMatchBetweenTeams createMatchBetweenTeams = snapshot1.getValue(CreateMatchBetweenTeams.class);
                    if(createMatchBetweenTeams.getStatus().matches("pending")) {
                        createMatchBetweenTeamsList.add(createMatchBetweenTeams);
//                    Toast.makeText(AcceptedMatchRequest.this, snapshot1.getKey().toString(), Toast.LENGTH_SHORT).show();
                        acceptedMatchRequestAdapter = new AcceptedMatchRequestAdapter(createMatchBetweenTeamsList, team_id, snapshot1.getKey().toString());
                        accepted_match_recycler_view.setAdapter(acceptedMatchRequestAdapter);
                        no_new_accepted_requests.setVisibility(View.GONE);
                        accepted_match_recycler_view.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(AcceptedMatchRequest.this, "No new request to show!", Toast.LENGTH_SHORT).show();
                    }
                }

//                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}