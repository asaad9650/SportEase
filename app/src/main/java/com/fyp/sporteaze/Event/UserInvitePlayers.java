package com.fyp.sporteaze.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fyp.sporteaze.Model.Invitation;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInvitePlayers extends AppCompatActivity {
    EditText et_invite_captain_name, et_invite_team_name,et_invite_remaining_space, et_invite_looking_for,et_invite_preferred_age , et_invite_message;
    Button btn_invite_submit ;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    String user_name, user_email, user_id , captain , team_id;
    String team_name;
    String team_captain_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invite_players);
        et_invite_captain_name = findViewById(R.id.et_invite_captain_name);
        et_invite_team_name = findViewById(R.id.et_invite_team_name);
        et_invite_remaining_space = findViewById(R.id.et_invite_remaining_space);
        et_invite_looking_for = findViewById(R.id.et_invite_looking_for);
        et_invite_preferred_age = findViewById(R.id.et_invite_preferred_age);
        et_invite_message = findViewById(R.id.et_invite_message);
        btn_invite_submit = findViewById(R.id.btn_invite_submit);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");


        reference.child("teams").child(team_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team_name= snapshot.child("team_name").getValue(String.class);
                team_captain_name = snapshot.child("email_1_captain").getValue(String.class);
                et_invite_captain_name.setText(team_captain_name);
                et_invite_team_name.setText(team_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_invite_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remaining_space = et_invite_remaining_space.getText().toString().trim();
                String looking_for = et_invite_looking_for.getText().toString().trim();
                String preferred_age = et_invite_preferred_age.getText().toString().trim();
                String message = et_invite_message.getText().toString().trim();
                if(remaining_space.matches("") || looking_for.matches("") || preferred_age.matches("") || message.matches("")){
                    Toast.makeText(UserInvitePlayers.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    String pushKey = reference.push().getKey();
                    Invitation invitation = new Invitation(team_captain_name , team_name ,remaining_space , looking_for ,  preferred_age , message , pushKey);
                    reference.child("invitations").child(pushKey).setValue(invitation);
                    Toast.makeText(UserInvitePlayers.this, "Invitation successfully posted.", Toast.LENGTH_SHORT).show();
                    et_invite_message.setText("");
                    et_invite_remaining_space.setText("");
                    et_invite_preferred_age.setText("");
                    et_invite_looking_for.setText("");
                }
            }
        });
    }
}