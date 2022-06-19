package com.fyp.sporteaze.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fyp.sporteaze.Event.UserShowPost;
import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.text.ParseException;

public class UserMyTeam extends AppCompatActivity {
    TextView my_team_name , my_team__no_of_player , no_teams_added_yet;
    AppCompatButton btn_view_my_team ;
    ConstraintLayout view_post_box;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference ref = firebaseDatabase.getReference();
    DatabaseReference teamRef = firebaseDatabase.getReference();
    String user_id, user_email , user_name, captain , team_id;
    String team_key;
    DatabaseReference acceptRef  = firebaseDatabase.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_my_team);
        my_team_name = findViewById(R.id.my_team_name);
        my_team__no_of_player = findViewById(R.id.my_team__no_of_player);
        btn_view_my_team = findViewById(R.id.btn_vieww_my_team);
        no_teams_added_yet = findViewById(R.id.no_teams_added_yet);

        view_post_box = findViewById(R.id.view_post_box);
        btn_view_my_team.setVisibility(View.GONE);
        my_team_name.setVisibility(View.GONE);
        my_team__no_of_player.setVisibility(View.GONE);
        no_teams_added_yet.setVisibility(View.VISIBLE);
        view_post_box.setVisibility(View.GONE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");

//        view_post_box.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(UserMyTeam.this, UserShowPost.class);
//
////                intent.putExtra("user_address",user.address );
////                intent.putExtra("user_gender",user.gender);
////                intent.putExtra("user_image",user.image);
//                intent.putExtra("user_id" , user_id);
//                intent.putExtra("user_email", user_email);
//                intent.putExtra("user_name",user_name);
//                intent.putExtra("captain", captain);
//                startActivity(intent);
//            }
//        });



//        if("yes".equalsIgnoreCase(captain)){
//        btn_leave_my_team.setVisibility(View.VISIBLE);
//        }
//        else{
//            btn_leave_my_team.setVisibility(View.GONE);
//        }




        btn_view_my_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.team_popup, null);
                TextView team_name = dialogView.findViewById(R.id.team_name);
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


                TextView email11_status = dialogView.findViewById(R.id.email11_status);
                TextView email10_status = dialogView.findViewById(R.id.email10_status);
                TextView email9_status = dialogView.findViewById(R.id.email9_status);
                TextView email8_status = dialogView.findViewById(R.id.email8_status);
                TextView email7_status = dialogView.findViewById(R.id.email7_status);
                TextView email6_status = dialogView.findViewById(R.id.email6_status);
                TextView email5_status = dialogView.findViewById(R.id.email5_status);
                TextView email4_status = dialogView.findViewById(R.id.email4_status);
                TextView email3_status = dialogView.findViewById(R.id.email3_status);
                TextView email2_status = dialogView.findViewById(R.id.email2_status);
                TextView email1_status = dialogView.findViewById(R.id.email1_status);
                DatabaseReference status_ref = FirebaseDatabase.getInstance().getReference();

                AppCompatButton btn_leave = dialogView.findViewById(R.id.btn_leave);
                AppCompatButton btn_update  = dialogView.findViewById(R.id.btn_update);
                btn_update.setVisibility(View.GONE);
                if(captain.matches("yes")){
                    btn_update.setVisibility(View.VISIBLE);
                }

                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(team_key!=null ) {
                            Toast.makeText(UserMyTeam.this, team_key, Toast.LENGTH_SHORT).show();

                            DatabaseReference team_info_reference = FirebaseDatabase.getInstance().getReference();
                            team_info_reference.child("teams").child(team_key).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Team team_infoo = snapshot.getValue(Team.class);

                                    Intent inte = new Intent(UserMyTeam.this, UserUpdateTeam.class);
                                    inte.putExtra( "captain_email" , team_infoo.getEmail_1_captain());
                                    inte.putExtra( "vice_captain_email" , team_infoo.getEmail_2_vice_captain());
                                    inte.putExtra( "email_3",team_infoo.getEmail_3());
                                    inte.putExtra( "email_4" , team_infoo.getEmail_4());
                                    inte.putExtra( "email_5",team_infoo.getEmail_5());
                                    inte.putExtra( "email_6" , team_infoo.getEmail_6());
                                    inte.putExtra( "email_7",team_infoo.getEmail_7());
                                    inte.putExtra( "email_8" , team_infoo.getEmail_8());
                                    inte.putExtra( "email_9",team_infoo.getEmail_9());
                                    inte.putExtra( "email_10" , team_infoo.getEmail_10());
                                    inte.putExtra( "email_11",team_infoo.getEmail_11());
                                    inte.putExtra( "team_id" ,team_infoo.team_id);
                                    inte.putExtra( "team_name",team_infoo.team_name);
                                    inte.putExtra("user_id" ,user_id);
                                    inte.putExtra("user_name",user_name);
                                    inte.putExtra("user_email",user_email);
                                    startActivity(inte);
                                    finish();

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
                btn_leave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AskOption(view.getRootView().getContext() , team_id , user_email , user_id );
                        Intent intent = new Intent(UserMyTeam.this , DashboardActivity.class);
                    }
                });





                if(team_key!=null) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("teams").child(team_key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                         Team team = snapshot.getValue(Team.class);
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_1_captain).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
                                        email1_status.setText(status);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_2_vice_captain).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email2_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_3).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email3_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_4).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email4_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_5).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email5_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_6).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email6_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_7).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email7_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_8).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email8_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_9).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email9_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_10).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email10_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            status_ref.child("Users").orderByChild("email").equalTo(team.email_11).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot snapshot1: snapshot.getChildren()){

                                        String status = (String) snapshot1.child(team_key).child("status").getValue();
//                                        if(status.matches("yes")){
                                        email11_status.setText(status);


//                                        Toast.makeText(UserMyTeam.this, status, Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });









                            team_name.setText(team.getTeam_name());
                            if(team.getEmail_1_captain().matches("") || team.getEmail_1_captain().matches("")){
                                email1.setVisibility(View.GONE);
                                email1_status.setVisibility(View.GONE);
                            }
                            else{
                                email1.setText(team.getEmail_1_captain() + " (c)");
                            }

                            if(team.getEmail_2_vice_captain()== null || team.getEmail_2_vice_captain().matches("")){
                                email2.setVisibility(View.GONE);
                                email2_status.setVisibility(View.GONE);
                            }
                            else{
                                email2.setText(team.getEmail_2_vice_captain() + " (vc)");
                            }
                            if(team.getEmail_3()== null || team.getEmail_3().matches("")){
                                email3.setVisibility(View.GONE);
                                email3_status.setVisibility(View.GONE);
                            }
                            else{
                                email3.setText(team.getEmail_3());
                            }
                            if(team.getEmail_4()== null || team.getEmail_4().matches("")){
                                email4.setVisibility(View.GONE);
                                email4_status.setVisibility(View.GONE);
                            }
                            else{
                                email4.setText(team.getEmail_4());
                            }
                            if(team.getEmail_5()== null || team.getEmail_5().matches("")){
                                email5.setVisibility(View.GONE);
                                email5_status.setVisibility(View.GONE);
                            }
                            else{
                                email5.setText(team.getEmail_5());
                            }
                            if(team.getEmail_6()== null || team.getEmail_6().matches("")){
                                email6.setVisibility(View.GONE);
                                email6_status.setVisibility(View.GONE);
                            }
                            else{
                                email6.setText(team.getEmail_6());
                            }
                            if(team.getEmail_7()== null || team.getEmail_7().matches("")){
                                email7.setVisibility(View.GONE);
                                email7_status.setVisibility(View.GONE);
                            }
                            else{
                                email7.setText(team.getEmail_7());
                            }
                            if(team.getEmail_8()== null || team.getEmail_8().matches("")){
                                email8.setVisibility(View.GONE);
                                email8_status.setVisibility(View.GONE);
                            }
                            else{
                                email8.setText(team.getEmail_8());
                            }
                            if(team.getEmail_9()== null || team.getEmail_9().matches("")){
                                email9.setVisibility(View.GONE);
                                email9_status.setVisibility(View.GONE);
                            }
                            else{
                                email9.setText(team.getEmail_9());
                            }
                            if(team.getEmail_10()== null || team.getEmail_10().matches("")){
                                email10.setVisibility(View.GONE);
                                email10_status.setVisibility(View.GONE);
                            }
                            else{
                                email10.setText(team.getEmail_10());
                            }
                            if(team.getEmail_11()== null || team.getEmail_11().matches("")){
                                email11.setVisibility(View.GONE);
                                email11_status.setVisibility(View.GONE);
                            }
                            else{
                                email11.setText(team.getEmail_11());
                            }

                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    builder.setView(dialogView);
                    builder.setCancelable(true);
                    builder.show();

                }
//                else{
//                    Toast.makeText(UserMyTeam.this, "Null hai saala", Toast.LENGTH_SHORT).show();
//                }
//                txt_user_coach_charges.setText("Charges: "+coaches.getCoach_charges());
//                txt_user_coach_phone.setText("Phone: "+coaches.getCoach_phone());
            }
        });




        DatabaseReference new_ref = FirebaseDatabase.getInstance().getReference();
        new_ref.child("Users").child(user_id).orderByChild("status").equalTo("approved").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ref.child("Users").child(user_id).orderByChild("team_id").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
//                            Toast.makeText(UserMyTeam.this, "exist", Toast.LENGTH_SHORT).show();

                            for(DataSnapshot ds : snapshot1.getChildren()){

                                if(ds.getKey().matches("name")|| ds.getKey().matches("address")||ds.getKey().matches("cnic")||ds.getKey().matches("dob")||ds.getKey().matches("email")||ds.getKey().matches("enrolled")||ds.getKey().matches("gender")||ds.getKey().matches("image")||ds.getKey().matches("password")||ds.getKey().matches("phone")||ds.getKey().matches("user_id")||ds.getKey().matches("enrolled")||ds.getKey().matches("captain"))
                                {

                                }
                                else{
                                    team_key = ds.getKey();
                                    getTeamInfo(team_key);

                                }

                            }
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







    }

    private AlertDialog AskOption(Context context , String team_id , String email , String us_id)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                .setTitle("Leave team")
                .setMessage("Are you sure you want to leave?")
                .setIcon(R.drawable.ic_baseline_delete_24)

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {


                    public void onClick(DialogInterface dialog, int whichButton) {

                        if(captain.matches("yes")){
                            Toast.makeText(UserMyTeam.this, "You can't leave a team when you are a captain, if you want to change a captain, change it from update team.", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(UserMyTeam.this, "Removed Successfully", Toast.LENGTH_SHORT).show();

//                            acceptRef.child("Users").child(user_id).child(team_id).removeValue();
//                            acceptRef.child("Users").child(user_id).child("enrolled").setValue("no");
//                            acceptRef.child("Users").child(user_id).child("team_id").setValue("");
//                            removeFromTeam(team_id,user_email);

                            Intent intent = new Intent(UserMyTeam.this , DashboardActivity.class);
                            intent.putExtra("user_id" ,user_id);
                            intent.putExtra("user_name",user_name);
                            intent.putExtra("user_email",user_email);
                            intent.putExtra("captain" , "");
                            intent.putExtra("team_id" , "");
                            startActivity(intent);
                            finish();
                        }


           }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        myQuittingDialogBox.setCancelable(true);
        myQuittingDialogBox.show();

        return myQuittingDialogBox;
    }

    void removeFromTeam(String team_id, String user_email){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("teams").child(team_id);

        ref.orderByValue().equalTo(user_email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String key_to_update = snapshot1.getKey();
                    ref.child(key_to_update).setValue("");
                    finish();
//                    Toast.makeText(UserMyTeam.this, snapshot1.getKey(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void getTeamInfo(String key){

         //        }

        teamRef.child("teams").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for(DataSnapshot dss : snapshot1.getChildren()){
                    if(dss.getKey().matches("team_name")){
                        no_teams_added_yet.setVisibility(View.GONE);
                        view_post_box.setVisibility(View.GONE);
                        my_team_name.setVisibility(View.VISIBLE);
                        my_team__no_of_player.setVisibility(View.VISIBLE);
                        btn_view_my_team.setVisibility(View.VISIBLE);
                        my_team_name.setText(dss.getValue().toString());
                        try {
                            int i = NumberFormat.getInstance().parse(snapshot1.getChildrenCount()+"").intValue();
                            i = i-3;
                            my_team__no_of_player.setText(""+i);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}