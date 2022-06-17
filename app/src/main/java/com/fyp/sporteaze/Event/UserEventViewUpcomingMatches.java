package com.fyp.sporteaze.Event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserEventViewUpcomingMatches extends AppCompatActivity {
    UpcomingMatchesAdapter upcomingMatchesAdapter;
    List<CreateMatchBetweenTeams> createMatchBetweenTeamsList;
    TextView no_new_post;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob;
    RecyclerView recycler_upcoming_matches;
    DatabaseReference createdMatchReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_view_upcoming_matches);
        recycler_upcoming_matches = findViewById(R.id.recycler_upcoming_matches);

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
        recycler_upcoming_matches.setLayoutManager(new LinearLayoutManager(this));

        createMatchBetweenTeamsList = new ArrayList<>();
        createdMatchReference = FirebaseDatabase.getInstance().getReference("create_match_between_teams");

        createdMatchReference.orderByChild("status").equalTo("accepted").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
//                        if(ds.child("request_initiated_by").getValue(String.class).matches(team_id)) {
//                        Toast.makeText(UserShowPost.this, "Team ID " + team_id, Toast.LENGTH_SHORT).show();
                    CreateMatchBetweenTeams createMatchBetweenTeams = ds.getValue(CreateMatchBetweenTeams.class);
                    if(createMatchBetweenTeams.getTeam_1().getTeam_id().matches(team_id) || createMatchBetweenTeams.getTeam_2().getTeam_id().matches(team_id)) {
                        createMatchBetweenTeamsList.add(createMatchBetweenTeams);
                    }
//                        }
                    upcomingMatchesAdapter = new UpcomingMatchesAdapter(createMatchBetweenTeamsList  , user_id, user_name , user_email, user_phone , user_dob,user_address);
                    recycler_upcoming_matches.setAdapter(upcomingMatchesAdapter);
                    recycler_upcoming_matches.setVisibility(View.VISIBLE);
//                    no_new_post.setVisibility(View.GONE);
                }

//                if (invoiceList.size()==0){
//                    no_invoices.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//                else{
//                    no_invoices.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                academyHelperAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}