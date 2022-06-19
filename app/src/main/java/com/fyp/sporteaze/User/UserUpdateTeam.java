package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.Model.UserTeamInfo;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserUpdateTeam extends AppCompatActivity {

    String user_id, user_email , user_name , team_id ,team_name, email_captain , email_vice_captain , email_3, email_4, email_5, email_6, email_7 , email_8 , email_9 , email_10, email_11 ;

    EditText et_team_name,et_email_1,et_email_2,et_email_3,et_email_4,et_email_5,et_email_6,et_email_7,et_email_8,et_email_9,et_email_10 , et_email_11;
    AppCompatButton btn_add_team;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ConstraintLayout update_team_layout;
    ProgressBar loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_team);
        update_team_layout = findViewById(R.id.update_team_layout);
        loader = findViewById(R.id.loader);
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
        et_email_11 = findViewById(R.id.et_email_11);
        btn_add_team = findViewById(R.id.btn_add_team);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_email = extras.getString("user_email");
        user_name = extras.getString("user_name");
        team_id = extras.getString("team_id");
        team_name = extras.getString("team_name");
        email_captain = extras.getString("captain_email");
        email_vice_captain = extras.getString("vice_captain_email");
        email_3 = extras.getString("email_3");
        email_4 = extras.getString("email_4");
        email_5 = extras.getString("email_5");
        email_6 = extras.getString("email_6");
        email_7 = extras.getString("email_7");
        email_8 = extras.getString("email_8");
        email_9 = extras.getString("email_9");
        email_10 = extras.getString("email_10");
        email_11 = extras.getString("email_11");

        if(team_name != null && !team_name.matches("")){
            et_team_name.setText(team_name);
        }

        if(!email_captain.matches("") && email_captain != null){
            et_email_1.setText(email_captain);
        }
        if(email_vice_captain!=null && !email_vice_captain.matches("") ){
            et_email_2.setText(email_vice_captain);
        }
        if(!email_3.matches("") && email_3!=null){
            et_email_3.setText(email_3);
        }

        if(!email_4.matches("") && email_4!=null){
            et_email_4.setText(email_4);
        }
        if(!email_5.matches("") && email_5!=null){
            et_email_5.setText(email_5);
        }
        if(!email_6.matches("") && email_6!=null){
            et_email_6.setText(email_6);
        }
        if(!email_7.matches("") && email_7!=null){
            et_email_7.setText(email_7);
        }

        if(!email_8.matches("") && email_8!=null){
            et_email_8.setText(email_8);
        }

        if(!email_9.matches("") && email_9!=null){
            et_email_9.setText(email_9);
        }

        if(!email_10.matches("") && email_10!=null){
            et_email_10.setText(email_10);
        }
        if(!email_11.matches("") && email_11!=null){
            et_email_11.setText(email_11);
        }

        btn_add_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!et_team_name.getText().toString().matches("") && et_email_1.getText().toString().trim().matches(emailPattern) && et_email_2.getText().toString().trim().matches(emailPattern)
                        && et_email_3.getText().toString().trim().matches(emailPattern) && et_email_4.getText().toString().trim().matches(emailPattern)
                        && et_email_5.getText().toString().trim().matches(emailPattern) && et_email_6.getText().toString().trim().matches(emailPattern)
                        && et_email_7.getText().toString().trim().matches(emailPattern) && et_email_8.getText().toString().trim().matches(emailPattern)
                        && et_email_9.getText().toString().trim().matches(emailPattern) && et_email_10.getText().toString().trim().matches(emailPattern))
                {

                    Team team = new Team(et_team_name.getText().toString().trim() ,
                            et_email_1.getText().toString().trim() ,
                            et_email_2.getText().toString().trim(),
                            et_email_3.getText().toString().trim(),et_email_4.getText().toString().trim(),et_email_5.getText().toString().trim(),et_email_6.getText().toString().trim(),
                            et_email_7.getText().toString().trim(),et_email_8.getText().toString().trim(),et_email_9.getText().toString().trim(),et_email_10.getText().toString().trim(),
                            et_email_11.getText().toString().trim(),team_id,user_id
                    );
                    update_team_layout.setVisibility(View.GONE);
                    loader.setVisibility(View.VISIBLE);
                    pushData(team);

//                    pushData(et_team_name,et_email_1, et_email_2, et_email_3, et_email_4,et_email_5,et_email_6,et_email_7,et_email_8,et_email_9,et_email_10,et_email_11);
                }

                else{
                    Toast.makeText(UserUpdateTeam.this, "Please enter all fields correctly!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void pushData(Team teamData){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("teams").child(team_id);
        ref.setValue(teamData);
        DatabaseReference cap_ref = FirebaseDatabase.getInstance().getReference();
        cap_ref.child("Users").child(user_id).child("captain").setValue("no");
        pushIntoUser(teamData , teamData.getEmail_1_captain() , "yes");
        pushIntoUser(teamData , teamData.getEmail_2_vice_captain() , "no");
        pushIntoUser(teamData , teamData.getEmail_3() , "no");
        pushIntoUser(teamData , teamData.getEmail_4() , "no");
        pushIntoUser(teamData , teamData.getEmail_5() , "no");
        pushIntoUser(teamData , teamData.getEmail_6() , "no");
        pushIntoUser(teamData , teamData.getEmail_7() , "no");
        pushIntoUser(teamData , teamData.getEmail_8() , "no");
        pushIntoUser(teamData , teamData.getEmail_9() , "no");
        pushIntoUser(teamData , teamData.getEmail_10() , "no");
        pushIntoUser(teamData , teamData.getEmail_11() , "no");
        DatabaseReference cap_val = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

        cap_ref.child("Users").child(user_id).child("captain").setValue("no");
        cap_ref.child("Users").child(user_id).child("enrolled").setValue("no");
        cap_ref.child("Users").child(user_id).child(team_id).removeValue();
        cap_ref.child("Users").child(user_id).child("team_id").setValue("");
        Intent intent = new Intent(UserUpdateTeam.this , DashboardActivity.class);
        intent.putExtra("user_name" , user_name);
        intent.putExtra("user_email" , user_email);
        intent.putExtra("user_id",  user_id);

        cap_val.child("captain").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String cap_ = snapshot.getValue(String.class);

                intent.putExtra("captain" , cap_);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Toast.makeText(UserUpdateTeam.this, "Team updated successfully", Toast.LENGTH_SHORT).show();
        startActivity(intent );
        finish();



//        pushIntoUser(teamData);
    }

    void pushIntoUser(Team team , String member_email , String captain){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userRef2 = FirebaseDatabase.getInstance().getReference();

        ref.child("Users").orderByChild("email").equalTo(member_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    if(captain.matches("yes")){
                        UserTeamInfo userAddTeam = new UserTeamInfo(team.getTeam_id(), "approved" , team.getTeam_name() , user_id);
                        userRef2.child("Users").child(user.user_id).child("enrolled").setValue("yes");
                        userRef2.child("Users").child(user.user_id).child("captain").setValue("yes");
                        userRef2.child("Users").child(user.user_id).child("team_id").setValue(team.getTeam_id());
                        userRef2.child("Users").child(user.user_id).child(team.getTeam_id()).setValue(userAddTeam);
                    }
                    else{
                        UserTeamInfo userAddTeam = new UserTeamInfo(team.getTeam_id(), "pending" , team.getTeam_name() , user_id);
                        userRef2.child("Users").child(user.user_id).child("captain").setValue("no");
                        userRef2.child("Users").child(user.user_id).child("enrolled").setValue("pending");
                        userRef2.child("Users").child(user.user_id).child("team_id").setValue(team.getTeam_id());
                        userRef2.child("Users").child(user.user_id).child(team.getTeam_id()).setValue(userAddTeam);
                    }


//                    userRef2.child("Users").child(user_id).child("team_id").setValue(team.getTeam_id());


                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
//        Toast.makeText(UserAddTeam.this, "Team added successfully!", Toast.LENGTH_SHORT).show();


    }

}