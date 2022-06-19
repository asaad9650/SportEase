package com.fyp.sporteaze.Event;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.contentcapture.DataShareWriteAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Match;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowCreatedPosts extends AppCompatActivity {

    List<Match> matchList ;
    RecyclerView post_invitations_recycler_view;
    ShowCreatedPostAdapter showCreatedPostAdapter;
    DatabaseReference databaseReference;
    DatabaseReference root ;
    TextView no_new_post;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob, posts;
    TextView heading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_created_posts);
        post_invitations_recycler_view = findViewById(R.id.post_invitations_recycler_view);
        heading = findViewById(R.id.heading);
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
        posts = extras.getString("posts");
        post_invitations_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        matchList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        if (posts.matches("po")){
            heading.setText("Matches");
        }
        else {
            heading.setText("Post Invitations");
        }
        if (posts.matches("po")){
            databaseReference.child("events").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Match match = dataSnapshot.getValue(Match.class);
                            if (!team_id.matches(match.getTeam_id())) {
                                matchList.add(match);
                                showCreatedPostAdapter = new ShowCreatedPostAdapter(matchList, dataSnapshot.getKey(), team_id);
                                post_invitations_recycler_view.setAdapter(showCreatedPostAdapter);
                            }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        if (posts.matches("posts")){
            databaseReference.child("events").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Match match = dataSnapshot.getValue(Match.class);
                        if (team_id.matches(match.getTeam_id())) {
                            matchList.add(match);
                            showCreatedPostAdapter = new ShowCreatedPostAdapter(matchList, dataSnapshot.getKey(), team_id);
                            post_invitations_recycler_view.setAdapter(showCreatedPostAdapter);
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

        }

    }
}