package com.fyp.sporteaze.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Event.UserShowPostAdapter;
import com.fyp.sporteaze.Model.JoinTeam;
import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.UserTeamInfo;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class TeamJoinRequestAdapter extends RecyclerView.Adapter
{
    String user_id, user_name ,user_email, user_dob, user_phone, user_address , team_name;
    List<JoinTeam> joinTeamList ;
    String key;
    String team_id;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    DatabaseReference check_reference = firebaseDatabase.getReference();
    DatabaseReference pushRef = firebaseDatabase.getReference();
    Team team ;
    DatabaseReference user_push = firebaseDatabase.getReference();

    public TeamJoinRequestAdapter(List<JoinTeam> joinTeamList , String key, String team_id, String user_id) {
        this.joinTeamList = joinTeamList;
        this.key = key;
        this.team_id = team_id;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_join_request_layout, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        JoinTeam joinTeam = joinTeamList.get(position);
        viewHolderClass.requester_email.setText("User Email: " + joinTeam.getUser_email());
        viewHolderClass.requester_name.setText("User Name: " + joinTeam.getUser_name());
        viewHolderClass.requester_dob.setText("Date of Birth: " + joinTeam.getUser_dob());
        viewHolderClass.requester_address.setText("Address: " + joinTeam.getUser_address());
        viewHolderClass.requester_phone.setText("Phone: " + joinTeam.getUser_phone());


        viewHolderClass.btn_accept_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = check_reference.push().getKey();

                reference.child("teams").child(team_id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        team = snapshot.getValue(Team.class);
                        viewHolderClass.ll_requests.setVisibility(View.GONE);
                        if(team.email_11.matches("")){
                            pushRef.child("teams").child(team_id).child("email_11").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);
                        }
                        else if (team.email_10.matches("")){
                            pushRef.child("teams").child(team_id).child("email_10").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");

                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);
                        }
                        else if (team.email_9.matches("")){
                            pushRef.child("teams").child(team_id).child("email_9").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);

                        }

                        else if (team.email_8.matches("")){
                            pushRef.child("teams").child(team_id).child("email_8").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);
                        }
                        else if (team.email_7.matches("")){
                            pushRef.child("teams").child(team_id).child("email_7").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);

                        }
                        else if (team.email_6.matches("")){
                            pushRef.child("teams").child(team_id).child("email_6").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);

                        }
                        else if (team.email_5.matches("")){
                            pushRef.child("teams").child(team_id).child("email_5").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);

                        }
                        else if (team.email_4.matches("")){
                            pushRef.child("teams").child(team_id).child("email_4").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);

                        }
                        else if (team.email_3.matches("")){
                            pushRef.child("teams").child(team_id).child("email_3").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id, "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);

                        }
                        else if (team.email_2_vice_captain.matches("")){
                            pushRef.child("teams").child(team_id).child("email_2_vice_captain").setValue(joinTeam.getUser_email());
                            Toast.makeText(view.getContext(), "Added in team", Toast.LENGTH_SHORT).show();
                            user_push.child("Users").child(joinTeam.getUser_id()).child("team_id").setValue(team.getTeam_id());
                            user_push.child("Users").child(joinTeam.getUser_id()).child("enrolled").setValue("yes");
                            UserTeamInfo userTeamInfo = new UserTeamInfo(team_id , "approved" , team.getTeam_name() , user_id);
                            user_push.child("Users").child(joinTeam.getUser_id()).child(team_id).setValue(userTeamInfo);
                        }
                        else{
                            Toast.makeText(view.getContext(), "Slots are already full!", Toast.LENGTH_SHORT).show();
                        }

                        check_reference.child("team_join_request").child(key).removeValue();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        viewHolderClass.btn_reject_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolderClass.ll_requests.setVisibility(View.GONE);
                check_reference.child("team_join_request").child(key).removeValue();
//                check_reference.child("teams").child(key).setValue();
//                joinTeamList.remove(holder.getLayoutPosition());
               //                joinTeamList.remove(holder.getPosition());
//                Toast.makeText(v, joinTeamList.size(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return joinTeamList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView requester_name;
        TextView requester_email;
        TextView requester_dob;
        TextView requester_phone;
        TextView requester_address;
        Button btn_accept_request;
        Button btn_reject_request;
        LinearLayout ll_requests;
        View view;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);


            requester_name = itemView.findViewById(R.id.requester_name);
            requester_dob = itemView.findViewById(R.id.requester_dob);
            requester_email = itemView.findViewById(R.id.requester_email);
            requester_phone = itemView.findViewById(R.id.requester_phone);
            requester_address = itemView.findViewById(R.id.requester_address);
            btn_accept_request = itemView.findViewById(R.id.btn_accept_request);
            btn_reject_request = itemView.findViewById(R.id.btn_reject_request);
            ll_requests = itemView.findViewById(R.id.ll_requests);


        }
    }



}
