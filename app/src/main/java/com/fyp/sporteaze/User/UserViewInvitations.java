package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.security.identity.IdentityCredentialStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.UserTeamInfo;
import com.fyp.sporteaze.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;

public class UserViewInvitations extends AppCompatActivity {

    TextView txt_team_name, txt_no_of_players , no_requests;
    String user_name,user_email,user_password,user_cnic,user_phone,user_dob,user_address,user_gender,user_id, user_image, user_captain;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    DatabaseReference acceptRef  = firebaseDatabase.getReference();
    AppCompatButton btn_join_team;
    AppCompatButton btn_reject_team;
    DatabaseReference teamRef = firebaseDatabase.getReference();
    DatabaseReference teamNameRef = firebaseDatabase.getReference();
    DatabaseReference checkRef;
    DatabaseReference getAllEmails = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_invitations);
        txt_team_name = findViewById(R.id.txt_team_name);
        txt_no_of_players = findViewById(R.id.txt_no_of_players);
        btn_join_team = findViewById(R.id.btn_join_team);
        no_requests = findViewById(R.id.no_requests);
        btn_reject_team = findViewById(R.id.btn_reject_team);
        checkRef = firebaseDatabase.getReference();
        txt_team_name.setVisibility(View.GONE);
        txt_no_of_players.setVisibility(View.GONE);
        btn_join_team.setVisibility(View.GONE);
        btn_reject_team.setVisibility(View.GONE);
        no_requests.setVisibility(View.VISIBLE);
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();

        user_address = extras.getString("user_address");
        user_gender = extras.getString("user_gender");
        user_id= extras.getString("user_id");
        user_image= extras.getString("user_image");
        user_email = extras.getString("user_email");
        user_name = extras.getString("user_name");
        user_captain = extras.getString("captain");
        txt_team_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllEmails.child("Users").child(user_id).child("team_id").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for(DataSnapshot dss : snapshot.getChildren()){
//                            String team_id = (String) dss.child("team_id").getValue();
//                    }
                            String team_idd = snapshot.getValue(String.class);
                            Toast.makeText(UserViewInvitations.this, team_idd.toString(), Toast.LENGTH_SHORT).show();
                            DatabaseReference ref_team_emails = FirebaseDatabase.getInstance().getReference();
                            ref_team_emails.child("teams").child(team_idd).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Team team = snapshot.getValue(Team.class);

                                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                                    View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.team_popup, null);
                                    TextView team_name = dialogView.findViewById(R.id.team_name);
                                    AppCompatButton btn_leave = dialogView.findViewById(R.id.btn_leave);
                                    AppCompatButton btn_update  = dialogView.findViewById(R.id.btn_update);
                                    btn_update.setVisibility(View.GONE);
                                    btn_leave.setVisibility(View.GONE);
                                    TextView email1 = dialogView.findViewById(R.id.email1);
                                    TextView email2 = dialogView.findViewById(R.id.email2);
                                    TextView email3 = dialogView.findViewById(R.id.email3);
                                    TextView email4 = dialogView.findViewById(R.id.email4);
                                    TextView email5 = dialogView.findViewById(R.id.email5);
                                    TextView email6 = dialogView.findViewById(R.id.email6);
                                    TextView email7 = dialogView.findViewById(R.id.email7);
                                    TextView email8 = dialogView.findViewById(R.id.email8);
                                    TextView email9 = dialogView.findViewById(R.id.email9);
                                    TextView email10 = dialogView.findViewById(R.id.email10);
                                    TextView email11 = dialogView.findViewById(R.id.email11);

                                    team_name.setText("Team Name:  "+team.getTeam_name());
                                    if(team.getEmail_1_captain().matches("")){
                                        email1.setVisibility(View.GONE);
                                    }
                                    else{
                                        email1.setText("Created By:  "+team.getEmail_1_captain() + " (c)");
                                    }

                                    if(team.getEmail_2_vice_captain()== null || team.getEmail_2_vice_captain().matches("")){
                                        email2.setVisibility(View.GONE);
                                    }
                                    else{
                                        email2.setText(team.getEmail_2_vice_captain() + " (vc)");
                                    }
                                    if(team.getEmail_3()== null || team.getEmail_3().matches("")){
                                        email3.setVisibility(View.GONE);
                                    }
                                    else{
                                        email3.setText(team.getEmail_3());
                                    }
                                    if(team.getEmail_4()== null|| team.getEmail_4().matches("")){
                                        email4.setVisibility(View.GONE);
                                    }
                                    else{
                                        email4.setText(team.getEmail_4());
                                    }
                                    if(team.getEmail_5()== null|| team.getEmail_5().matches("")){
                                        email5.setVisibility(View.GONE);
                                    }
                                    else{
                                        email5.setText(team.getEmail_5());
                                    }
                                    if(team.getEmail_6()== null|| team.getEmail_6().matches("")){
                                        email6.setVisibility(View.GONE);
                                    }
                                    else{
                                        email6.setText(team.getEmail_6());
                                    }
                                    if(team.getEmail_7()== null|| team.getEmail_7().matches("")){
                                        email7.setVisibility(View.GONE);
                                    }
                                    else{
                                        email7.setText(team.getEmail_7());
                                    }
                                    if(team.getEmail_8()== null|| team.getEmail_8().matches("")){
                                        email8.setVisibility(View.GONE);
                                    }
                                    else{
                                        email8.setText(team.getEmail_8());
                                    }
                                    if(team.getEmail_9()== null|| team.getEmail_9().matches("")){
                                        email9.setVisibility(View.GONE);
                                    }
                                    else{
                                        email9.setText(team.getEmail_9());
                                    }
                                    if(team.getEmail_10()== null|| team.getEmail_10().matches("")){
                                        email10.setVisibility(View.GONE);
                                    }
                                    else{
                                        email10.setText(team.getEmail_10());
                                    }
                                    if(team.getEmail_11()== null|| team.getEmail_11().matches("")){
                                        email11.setVisibility(View.GONE);
                                    }
                                    else{
                                        email11.setText(team.getEmail_11());
                                    }

                                    builder.setView(dialogView);
                                    builder.setCancelable(true);
                                    builder.show();

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });




//                                             gdfgdgdgdfgdfgdfgdfgdfg                                                           //
//                            if(team_id!=null){


//                                if(team_emails!=null){
//                        Toast.makeText(UserAddTeam.this, team_emails.indexOf(0), Toast.LENGTH_SHORT).show();
//                                }

//                    Toast.makeText(UserAddTeam.this, "Is team me hai na tu " +team_id, Toast.LENGTH_SHORT).show();
                        }
//                registered_in_team_id = (String)snapshot.getChildren().getValue();
//                Toast.makeText(UserAddTeam.this, "Is team me hai na tu " +snapshot.toString(), Toast.LENGTH_SHORT).show();
//                String team_id = (String) snapshot.child("team_name").getValue();
//                Toast.makeText(UserAddTeam.this, "Is team me register hain bhai sahab " + team_id, Toast.LENGTH_SHORT).show();
//                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

        checkRef.child("Users").child(user_id).orderByChild("enrolled").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                String enrolled = (String) snapshot2.child("enrolled").getValue();


                String get_team_id = (String) snapshot2.child("team_id").getValue();
//                Toast.makeText(UserViewInvitations.this, "Ye do harami the " + enrolled+get_team_id, Toast.LENGTH_SHORT).show();

            if(enrolled.matches("pending")&& get_team_id!=null) {
                teamNameRef.child("teams").child(get_team_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot3) {

                        String naaaam = (String) snapshot3.child("team_name").getValue();

                        int i = 0;
                        try {
                            i = NumberFormat.getInstance().parse(snapshot3.getChildrenCount() + "").intValue();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        i = i - 3;
                        no_requests.setVisibility(View.GONE);
                        txt_team_name.setVisibility(View.VISIBLE);
                        btn_reject_team.setVisibility(View.VISIBLE);
                        btn_join_team.setVisibility(View.VISIBLE);
                        txt_no_of_players.setVisibility(View.VISIBLE);
                        txt_no_of_players.setText(i + "");

                        txt_team_name.setText(naaaam);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        }
        btn_reject_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(UserViewInvitations.this, user_email, Toast.LENGTH_SHORT).show();
                reference.child("Users").child(user_id).orderByChild("status").equalTo("pending").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            UserTeamInfo userAddTeam = ds.getValue(UserTeamInfo.class);
                            acceptRef.child("Users").child(user_id).child(userAddTeam.getTeam_id()).child("status").setValue("rejected");
                            acceptRef.child("Users").child(user_id).child("enrolled").setValue("no");
                            acceptRef.child("Users").child(user_id).child("team_id").removeValue();

                            removeFromTeam(userAddTeam.getTeam_id(),user_email);
//                            Toast.makeText(UserViewInvitations.this, userAddTeam.getTeam_id()+" "+user_email, Toast.LENGTH_SHORT).show();


//                            removeEmailFromTeam();
                            Query query = teamRef.child("teams").child(userAddTeam.getTeam_id()).orderByValue().equalTo(user_email);
                            query.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot ds2 : snapshot.getChildren()) {
                                        Toast.makeText(UserViewInvitations.this, ds2.toString(), Toast.LENGTH_SHORT).show();
                                        ds2.getRef().setValue("");

                                        Intent intent = new Intent(UserViewInvitations.this , DashboardActivity.class);
                                        intent.putExtra("user_email" , user_email);
                                        intent.putExtra("user_name", user_name);
                                        intent.putExtra("captain" , user_captain);
                                        intent.putExtra("user_id", user_id);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            // UserTeamInfo userTeamInfo = ds.getValue(UserTeamInfo.class);
//                            Toast.makeText(UserViewInvitations.this, userAddTeam.getTeam_name() + " " + userAddTeam.getStatus() , Toast.LENGTH_SHORT).show();

//                            txt_no_of_players.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
//        Toast.makeText(UserViewInvitations.this, user_id , Toast.LENGTH_SHORT).show();
        btn_join_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Users").child(user_id).orderByChild("status").equalTo("pending").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            UserTeamInfo userAddTeam = ds.getValue(UserTeamInfo.class);
                            DatabaseReference new_ref = firebaseDatabase.getReference();
                            new_ref.child("Users").child(user_id).child("enrolled").setValue("yes");
                            acceptRef.child("Users").child(user_id).child(userAddTeam.getTeam_id()).child("status").setValue("approved");


                            // UserTeamInfo userTeamInfo = ds.getValue(UserTeamInfo.class);
//                            Toast.makeText(UserViewInvitations.this, userAddTeam.getTeam_name() + " " + userAddTeam.getStatus() , Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(UserViewInvitations.this , DashboardActivity.class);
                            intent.putExtra("user_email" , user_email);
                            intent.putExtra("user_name", user_name);
                            intent.putExtra("captain" , user_captain);
                            intent.putExtra("user_id", user_id);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    void removeFromTeam(String team_id, String user_email){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("teams").child(team_id);

        ref.orderByValue().equalTo(user_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String key_to_update = snapshot1.getKey();
                    ref.child(key_to_update).setValue("");
                    Toast.makeText(UserViewInvitations.this, snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}