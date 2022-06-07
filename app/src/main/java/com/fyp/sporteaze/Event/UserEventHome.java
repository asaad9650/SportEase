package com.fyp.sporteaze.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fyp.sporteaze.R;

public class UserEventHome extends AppCompatActivity {
    ConstraintLayout create_match_box, post_invitations_box, invite_players_box, view_schedule_box;

    String user_name, user_email, user_id , captain , team_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_event_home);

        view_schedule_box = findViewById(R.id.view_schedule_box);
        invite_players_box = findViewById(R.id.invite_players_box);
        post_invitations_box = findViewById(R.id.post_invitations_box);
        create_match_box = findViewById(R.id.create_match_box);


        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");

        create_match_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEventHome.this , UserCreateEvent.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("user_email" , user_email);
                intent.putExtra("team_id" , team_id);
                startActivity(intent);

            }
        });

        invite_players_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEventHome.this , UserInvitePlayers.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("user_email" , user_email);
                intent.putExtra("team_id" , team_id);
                startActivity(intent);
            }
        });

        view_schedule_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserEventHome.this , UserEventViewSchedule.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("user_email" , user_email);
                intent.putExtra("team_id" , team_id);
                startActivity(intent);
            }
        });





    }
}