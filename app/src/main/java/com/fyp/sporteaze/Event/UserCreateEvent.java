package com.fyp.sporteaze.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fyp.sporteaze.Model.Match;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserCreateEvent extends AppCompatActivity {

    String user_name, user_email, user_id , team_name , team_id;
    EditText et_create_match_winning_price, et_create_match_ground_booked, et_create_match_team_name;
    Button btn_create_event;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create_event);
        et_create_match_ground_booked = findViewById(R.id.et_create_match_ground_booked);
        et_create_match_winning_price = findViewById(R.id.et_create_match_winning_price);
        et_create_match_team_name = findViewById(R.id.et_create_match_team_name);
        btn_create_event = findViewById(R.id.btn_create_event);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
//        captain = extras.getString("captain");
        team_id = extras.getString("team_id");

        reference.child("teams").child(team_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                     team_name = snapshot.child("team_name").getValue(String.class);
                     et_create_match_team_name.setText(team_name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String team_id = "";
                String team_name = et_create_match_team_name.getText().toString().trim();
                String winning_price = et_create_match_winning_price.getText().toString().trim();
                String ground_booked = et_create_match_ground_booked.getText().toString().trim();
                if(team_name.matches("") || winning_price.matches("") || ground_booked.matches("")){
                    Toast.makeText(UserCreateEvent.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference root = db.getReference();
                    String event_key = root.push().getKey();
                    Match match = new Match(event_key, et_create_match_team_name.getText().toString().trim(), et_create_match_ground_booked.getText().toString().trim(), et_create_match_winning_price.getText().toString().trim() , team_id);
                    root.child("events").child(team_id).child(event_key).setValue(match);
                    Toast.makeText(UserCreateEvent.this, "Created Successfully!", Toast.LENGTH_SHORT).show();
                    String empty = "";
                    et_create_match_ground_booked.setText(empty);
                    et_create_match_winning_price.setText(empty);
                }
            }
        });

    }
}