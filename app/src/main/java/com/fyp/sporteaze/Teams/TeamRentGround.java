package com.fyp.sporteaze.Teams;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fyp.sporteaze.Model.TeamGroundBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeamRentGround extends AppCompatActivity {
    EditText et_rent_ground_name ,et_rent_ground_date ,et_rent_ground_time ,et_rent_ground_team , et_rent_ground_purpose ;
    Button rent_ground_btn;
    CheckBox with_medical, with_analyst;

    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob ,ground_charges , academy_id;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    String ground_size, ground_id, ground_name, ground_address;
    DatabaseReference team_name_reference = FirebaseDatabase.getInstance().getReference();
    String team_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_rent_ground);

        et_rent_ground_name = findViewById(R.id.et_rent_ground_name);
        et_rent_ground_date = findViewById(R.id.et_rent_ground_date);
        et_rent_ground_time = findViewById(R.id.et_rent_ground_time);
        et_rent_ground_team = findViewById(R.id.et_rent_ground_team);
        et_rent_ground_purpose = findViewById(R.id.et_rent_ground_purpose);
        rent_ground_btn = findViewById(R.id.rent_ground_btn);
        with_analyst = findViewById(R.id.ground_with_analyst);
        with_medical = findViewById(R.id.ground_with_medical);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        ground_id = extras.getString("ground_id");
        ground_name  = extras.getString("ground_name");
        ground_size = extras.getString("ground_size");
        ground_address = extras.getString("ground_location");
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");
        user_phone = extras.getString("user_phone");
        user_address = extras.getString("user_address");
        user_dob = extras.getString("user_dob");
        ground_charges = extras.getString("ground_charges");
        academy_id  = extras.getString("academy_id");


        et_rent_ground_name.setText(ground_name);
        et_rent_ground_purpose.setText(ground_address);
        team_name_reference.child("teams").child(team_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team_name = snapshot.child("team_name").getValue(String.class);
                et_rent_ground_team.setText(team_name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        if(team_name!=null && !team_name.matches("")){
//        }




        rent_ground_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ground_name = et_rent_ground_name.getText().toString().trim();
                String ground_date = et_rent_ground_date.getText().toString().trim();
                String ground_time = et_rent_ground_time.getText().toString().trim();
                String ground_team_name = et_rent_ground_team.getText().toString().trim();
                String ground_rent_purpose = et_rent_ground_purpose.getText().toString().trim();
                String _with_analyst ;
                String _with_medical;
                if(with_analyst.isChecked()){
                    _with_analyst = "yes";
                }
                else{
                    _with_analyst = "no";
                }

                if(with_medical.isChecked()){
                    _with_medical="yes";
                }
                else{
                    _with_medical = "no";
                }

                if (ground_name.matches("") || ground_date.matches(" ") || ground_time.matches("") || ground_team_name.matches("") || ground_rent_purpose.matches("")){
                    Toast.makeText(TeamRentGround.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(ground_date.matches("[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$") && ground_time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]-([01]?[0-9]|2[0-3]):[0-5][0-9]")){

                        String key = databaseReference.push().getKey();
                        TeamGroundBooking teamGroundBooking = new TeamGroundBooking(key, team_id , ground_name , ground_id , ground_size , ground_address , ground_time , ground_date , ground_rent_purpose , _with_medical  , _with_analyst , ground_charges , "pending" , academy_id , team_name);
                        databaseReference.child("team_ground_bookings").child(key).setValue(teamGroundBooking);
                        et_rent_ground_date.setText("");
                        et_rent_ground_purpose.setText("");
                        et_rent_ground_time.setText("");
                        with_medical.setChecked(false);
                        with_analyst.setChecked(false);
                        Toast.makeText(TeamRentGround.this, "Booked Successfully", Toast.LENGTH_SHORT).show();


                    }
                    else{
                        Toast.makeText(TeamRentGround.this, "Please check your formats, Date and Time should only be formatted in  DD/MM/YYYY and HH:MM respectively.", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }
}