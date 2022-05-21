package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.Model.UserTeamInfo;
import com.fyp.sporteaze.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Locale;

public class UserAddTeam extends AppCompatActivity {
    EditText et_team_name,et_email_1,et_email_2,et_email_3,et_email_4,et_email_5,et_email_6,et_email_7,et_email_8,et_email_9,et_email_10;
    AppCompatButton btn_add_team;
    String created_by_id;
    String user_email;
    String user_name;
    String user_id;
    List team_emails;
    TextView team_status_name,team_status_email1,team_status_email2,team_status_email3,team_status_email4,team_status_email5,
            team_status_email6,team_status_email7,team_status_email8,team_status_email9,team_status_email10,team_status_email11;
    TextView team_email1_status,team_email2_status,team_email3_status,team_email4_status,team_email5_status,team_email6_status,
            team_email7_status,team_email8_status,team_email9_status,team_email10_status;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    LinearLayout team_status_layout;
    ConstraintLayout team_registration_layout;

    private DatabaseReference reference;
    DatabaseReference enrolledRef;
    DatabaseReference userRef;
    DatabaseReference statusCheckRef;
    DatabaseReference condRef;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference();
    DatabaseReference userRef2 = firebaseDatabase.getReference();
    DatabaseReference getAllEmails = firebaseDatabase.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_team);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        team_status_name = findViewById(R.id.team_status_name);
        team_status_email1 = findViewById(R.id.team_status_email1);
        team_status_email2 = findViewById(R.id.team_status_email2);
        team_status_email3 = findViewById(R.id.team_status_email3);
        team_status_email4 = findViewById(R.id.team_status_email4);
        team_status_email5 = findViewById(R.id.team_status_email5);
        team_status_email6 = findViewById(R.id.team_status_email6);
        team_status_email7 = findViewById(R.id.team_status_email7);
        team_status_email8 = findViewById(R.id.team_status_email8);
        team_status_email9 = findViewById(R.id.team_status_email9);
        team_status_email10 = findViewById(R.id.team_status_email10);
        team_status_email11 = findViewById(R.id.team_status_email11);

//        team_email1_status = findViewById(R.id.team_email1_status);
//        team_email2_status = findViewById(R.id.team_email2_status);
//        team_email3_status = findViewById(R.id.team_email3_status);
//        team_email4_status = findViewById(R.id.team_email4_status);
//        team_email5_status = findViewById(R.id.team_email5_status);
//        team_email6_status = findViewById(R.id.team_email6_status);
//        team_email7_status = findViewById(R.id.team_email7_status);
//        team_email8_status = findViewById(R.id.team_email8_status);
//        team_email9_status = findViewById(R.id.team_email9_status);
//        team_email10_status = findViewById(R.id.team_email10_status);



        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        team_registration_layout = findViewById(R.id.team_registration_layout);
        team_status_layout = findViewById(R.id.team_status_layout);
        reference = FirebaseDatabase.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference();
        enrolledRef = FirebaseDatabase.getInstance().getReference();
        condRef = FirebaseDatabase.getInstance().getReference();

        et_team_name = findViewById(R.id.et_team_name);
        et_email_1 = findViewById(R.id.et_email_1);
        et_email_2 = findViewById(R.id.et_email_2);
        et_email_3 = findViewById(R.id.et_email_3);
        et_email_4 = findViewById(R.id.et_email_4);
        et_email_5 = findViewById(R.id.et_email_5);
        et_email_6 = findViewById(R.id.et_email_6);
        et_email_7 = findViewById(R.id.et_email_7);
        et_email_8 = findViewById(R.id.et_email_8);
        et_email_9 = findViewById(R.id.et_email_9);
        et_email_10 = findViewById(R.id.et_email_10);
        btn_add_team = findViewById(R.id.btn_add_team);

        statusCheckRef = FirebaseDatabase.getInstance().getReference();

        String key = reference.push().getKey();

        team_registration_layout.setVisibility(View.GONE);
        team_status_layout.setVisibility(View.GONE);

        statusCheckRef.child("Users").child(user_id).orderByChild("enrolled").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String enrolled =(String)snapshot.child("enrolled").getValue();

                if(enrolled.trim().matches("yes")) {
                    team_status_layout.setVisibility(View.VISIBLE);
                    team_registration_layout.setVisibility(View.GONE);
                }
                else{
                    team_status_layout.setVisibility(View.GONE);
                    team_registration_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        Toast.makeText(UserAddTeam.this, "Is team me hai na tu " +user_email, Toast.LENGTH_SHORT).show();
//        Toast.makeText(, "", Toast.LENGTH_SHORT).show();
        getAllEmails.child("Users").child(user_id).orderByChild("team_id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()){
                    String team_id = (String) dss.child("team_id").getValue();
                    if(team_id!=null){
                        getAllEmailStatus(team_id);
                        if(team_emails!=null){
//                        Toast.makeText(UserAddTeam.this, team_emails.indexOf(0), Toast.LENGTH_SHORT).show();
                        }
                    }
//                    Toast.makeText(UserAddTeam.this, "Is team me hai na tu " +team_id, Toast.LENGTH_SHORT).show();
                }
//                registered_in_team_id = (String)snapshot.getChildren().getValue();
//                Toast.makeText(UserAddTeam.this, "Is team me hai na tu " +snapshot.toString(), Toast.LENGTH_SHORT).show();
//                String team_id = (String) snapshot.child("team_name").getValue();
//                Toast.makeText(UserAddTeam.this, "Is team me register hain bhai sahab " + team_id, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_add_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_email_1.getText().toString().trim().matches(emailPattern) || et_email_2.getText().toString().trim().matches(emailPattern)
                        || et_email_3.getText().toString().trim().matches(emailPattern) || et_email_4.getText().toString().trim().matches(emailPattern)
                        || et_email_5.getText().toString().trim().matches(emailPattern) || et_email_6.getText().toString().trim().matches(emailPattern)
                        || et_email_7.getText().toString().trim().matches(emailPattern) || et_email_8.getText().toString().trim().matches(emailPattern)
                        || et_email_9.getText().toString().trim().matches(emailPattern)|| et_email_10.getText().toString().trim().matches(emailPattern))
                {
                    if(user_email.matches(et_email_1.getText().toString()) ||user_email.matches(et_email_2.getText().toString())||
                            user_email.matches(et_email_3.getText().toString())||user_email.matches(et_email_4.getText().toString())||
                            user_email.matches(et_email_5.getText().toString())||user_email.matches(et_email_6.getText().toString())||
                            user_email.matches(et_email_7.getText().toString())||user_email.matches(et_email_8.getText().toString())||
                            user_email.matches(et_email_9.getText().toString())||user_email.matches(et_email_10.getText().toString()))
                    {

                        Toast.makeText(UserAddTeam.this, "You can not add yourself in your team, you already are a captain.\nPlease use different email.", Toast.LENGTH_SHORT).show();

                    }

                    else {


                        condRef.child("teams").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dsTeam : snapshot.getChildren()) {
                                    Team team = dsTeam.getValue(Team.class);
//                                Toast.makeText(UserAddTeam.this, team.getEmail_1_captain().toString(), Toast.LENGTH_SHORT).show();
                                    if (et_email_1.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_1.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_1.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_2.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_2.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_3.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_3.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_3.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_4.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_4.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_4.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_5.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_5.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_5.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_6.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_2.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_6.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_6.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_7.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_7.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_7.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_8.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_8.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_8.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_9.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_9.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_9.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else if (et_email_10.getText().toString().matches(team.getEmail_1_captain()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_2_vice_captain()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_3()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_4()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_5()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_6()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_7()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_8()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_9()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_10()) ||
                                            et_email_10.getText().toString().matches(team.getEmail_11())
                                    ) {
                                        Toast.makeText(UserAddTeam.this, "This user " + et_email_10.getText().toString() + " is already registered in a team.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(UserAddTeam.this, "Registered!", Toast.LENGTH_SHORT).show();

                                        Team Objteam = new Team(et_team_name.getText().toString(), user_email.trim(), et_email_1.getText().toString().trim(), et_email_2.getText().toString().trim(), et_email_3.getText().toString().trim(), et_email_4.getText().toString().trim(), et_email_5.getText().toString().trim(), et_email_6.getText().toString().trim(), et_email_7.getText().toString().trim(), et_email_8.getText().toString().trim(), et_email_9.getText().toString().trim(), et_email_10.getText().toString().trim(), key, created_by_id);
                                        reference.child("teams").child(key).setValue(Objteam);
                                        pushIntoUser(Objteam, Objteam.getEmail_1_captain());
                                        pushIntoUser(Objteam, Objteam.getEmail_2_vice_captain());
                                        pushIntoUser(Objteam, Objteam.getEmail_3());
                                        pushIntoUser(Objteam, Objteam.getEmail_4());
                                        pushIntoUser(Objteam, Objteam.getEmail_5());
                                        pushIntoUser(Objteam, Objteam.getEmail_6());
                                        pushIntoUser(Objteam, Objteam.getEmail_7());
                                        pushIntoUser(Objteam, Objteam.getEmail_8());
                                        pushIntoUser(Objteam, Objteam.getEmail_9());
                                        pushIntoUser(Objteam, Objteam.getEmail_10());
                                        pushIntoUser(Objteam, Objteam.getEmail_11());
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
            }
                else{
                    Toast.makeText(UserAddTeam.this, "Email address is not valid", Toast.LENGTH_SHORT).show();
                }

                }
        });


    }

    void getAllEmailStatus(String team_id){

        DatabaseReference ref_team_emails = FirebaseDatabase.getInstance().getReference();
        ref_team_emails.child("teams").child(team_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Team team = snapshot.getValue(Team.class);
                if(team.getEmail_1_captain().matches("")){
                    team_status_email1.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_2_vice_captain().matches("")){
                    team_status_email2.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_3().matches("")){
                    team_status_email3.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_4().matches("")){
                    team_status_email4.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);


                }
                if(team.getEmail_5().matches("")){
                    team_status_email5.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_6().matches("")){
                    team_status_email6.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_7().matches("")){
                    team_status_email7.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_8().matches("")){
                    team_status_email8.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_9().matches("")){
                    team_status_email9.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_10().matches("")){
                    team_status_email10.setVisibility(View.GONE);
//                    team_email1_status.setVisibility(View.GONE);
                }
                if(team.getEmail_11().matches("")){
                    team_status_email11.setVisibility(View.GONE);
//                    team_email11_status.setVisibility(View.GONE);
                }
                team_status_name.setText(team.getTeam_name());
                team_status_email1.setText(team.getEmail_2_vice_captain());
                team_status_email2.setText(team.getEmail_3());
                team_status_email3.setText(team.getEmail_4());
                team_status_email4.setText(team.getEmail_5());
                team_status_email5.setText(team.getEmail_6());
                team_status_email6.setText(team.getEmail_7());
                team_status_email7.setText(team.getEmail_8());
                team_status_email8.setText(team.getEmail_9());
                team_status_email9.setText(team.getEmail_10());
                team_status_email10.setText(team.getEmail_11());

//                getStatus(team.getEmail_2_vice_captain() , team.getTeam_id() , team);
//                getStatus(team.getEmail_3());
//                getStatus(team.getEmail_4());
//                getStatus(team.getEmail_5());
//                getStatus(team.getEmail_6());
//                getStatus(team.getEmail_7());
//                getStatus(team.getEmail_8());
//                getStatus(team.getEmail_9());
//                getStatus(team.getEmail_10());
//                team_emails.add(team.email_2_vice_captain);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//        getStatus(team_id);
//        return team_emails;
    }
//    String getStatus(String email_to_match  , String team_ID , Team team_obj){
////        Toast.makeText(UserAddTeam.this, email_to_match, Toast.LENGTH_SHORT).show();
//        String email = "";
//        DatabaseReference statusReference = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference statusReference2 = FirebaseDatabase.getInstance().getReference();
//        statusReference.child("Users").orderByChild("team_id").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    String team_id = (String)dataSnapshot.child("team_id").getValue();
//                    String useremail = (String) dataSnapshot.child("email").getValue();
//                    String status = (String) dataSnapshot.child("enrolled").getValue();
//
//                    if(team_id!= null && useremail!=null && status!=null ) {
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_2_vice_captain())) {
////                            if(status.matches("yes")){
////                                team_email1_status.setText("accepted");
//                                team_email1_status.setVisibility(View.GONE);
////                            }
////                            else if (status.matches("pending")){
////                                team_email1_status.setText("pending");
////
////                            }
////                            else{
////                                team_email1_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_3())) {
////                            if(status.matches("yes")){
////                                team_email2_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email2_status.setText("pending");
////
////                            }
////                            else{
////                                team_email2_status.setText("rejected");
////                            }
//                            team_email2_status.setVisibility(View.GONE);
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_4())) {
////                            if(status.matches("yes")){
////                                team_email3_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email3_status.setText("pending");
//                                team_email3_status.setVisibility(View.GONE);
////
////                            }
////                            else{
////                                team_email3_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_5())) {
////                            if(status.matches("yes")){
////                                team_email4_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email4_status.setText("pending");
//                                team_email4_status.setVisibility(View.GONE);
////
////                            }
////                            else{
////                                team_email4_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_6())) {
////                            if(status.matches("yes")){
////                                team_email5_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email5_status.setText("pending");
////                                team_email5_status.setVisibility(View.GONE);
////
////                            }
////                            else{
////                                team_email5_status.setText("rejected");
////                                team_email5_status.setVisibility(View.GONE);
////
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_7())) {
////                            if(status.matches("yes")){
////                                team_email6_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email6_status.setText("pending");
//                                team_email6_status.setVisibility(View.GONE);
////
////
////                            }
////                            else{
////                                team_email6_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_8())) {
////                            if(status.matches("yes")){
////                                team_email7_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email7_status.setText("pending");
//                                team_email7_status.setVisibility(View.GONE);
////
////                            }
////                            else{
////                                team_email7_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_9())) {
////                            if(status.matches("yes")){
////                                team_email8_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email8_status.setText("pending");
//                                team_email8_status.setVisibility(View.GONE);
////
////
////                            }
////                            else{
////                                team_email8_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_10())) {
////                            if(status.matches("yes")){
////                                team_email9_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email9_status.setText("pending");
//                                team_email9_status.setVisibility(View.GONE);
////
////
////                            }
////                            else{
////                                team_email9_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//                        if (team_id.matches(team_ID) && useremail.matches(team_obj.getEmail_11())) {
////                            if(status.matches("yes")){
////                                team_email10_status.setText("accepted");
////                            }
////                            else if (status.matches("pending")){
////                                team_email10_status.setText("pending");
//                                team_email10_status.setVisibility(View.GONE);
////
////
////                            }
////                            else{
////                                team_email10_status.setText("rejected");
////                            }
//
////                        Toast.makeText(UserAddTeam.this, team_id + "", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//
////                    for(DataSnapshot dataSnapshot3 : dataSnapshot.getChildren()) {
//
////                        String status = snapshot.getKey();
////                        String team_idd = (String) dataSnapshot3.child("team_id").getValue();
//
////                        if (team_idd.matches(team_ID)) {
////                            Toast.makeText(UserAddTeam.this,dataSnapshot3.getKey()+" Ye aya hai " + team_idd , Toast.LENGTH_SHORT).show();
////                        }
////                    }
////                    String enrolled = (String) dataSnapshot.child("enrolled").getValue();
////                    Toast.makeText(UserAddTeam.this, "enrolled " + enrolled  , Toast.LENGTH_SHORT).show();
//                }
//
////                Toast.makeText(UserAddTeam.this, snapshot.toString(), Toast.LENGTH_SHORT).show();
////                email = (String)snapshot.child("email").getValue();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return email;
//    }

    void pushIntoUser(Team team , String member_email){
        ref.child("Users").orderByChild("email").equalTo(member_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                   if(user_email.matches(member_email)){
                       UserTeamInfo userAddTeam = new UserTeamInfo(team.getTeam_id(), "approved" , team.getTeam_name() , user_id);
                       userRef2.child("Users").child(user_id).child("enrolled").setValue("yes");
                       userRef2.child("Users").child(user_id).child("captain").setValue("yes");
                       userRef2.child("Users").child(user_id).child("team_id").setValue(team.getTeam_id());
                       userRef2.child("Users").child(user.user_id).child(team.getTeam_id()).setValue(userAddTeam);
                   }
                   else{
                       UserTeamInfo userAddTeam = new UserTeamInfo(team.getTeam_id(), "pending" , team.getTeam_name() , user_id);
                       userRef2.child("Users").child(user.user_id).child("enrolled").setValue("pending");
                       userRef2.child("Users").child(user.user_id).child("team_id").setValue(team.getTeam_id());
                       userRef2.child("Users").child(user.user_id).child(team.getTeam_id()).setValue(userAddTeam);
                   }


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
//        Toast.makeText(UserAddTeam.this, "Team added successfully!", Toast.LENGTH_SHORT).show();


    }


}