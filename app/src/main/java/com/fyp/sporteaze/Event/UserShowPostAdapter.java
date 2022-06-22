package com.fyp.sporteaze.Event;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.Model.Invitation;
import com.fyp.sporteaze.Model.JoinTeam;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserShowPostAdapter  extends RecyclerView.Adapter {

    List<Invitation> invoiceList;
    List<CreateMatchBetweenTeams> createMatchBetweenTeamsList;
    String user_id , user_name, user_email, user_phone ,user_dob,user_address , team_name , team_id;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    DatabaseReference check_reference = firebaseDatabase.getReference();



    public UserShowPostAdapter(List<Invitation> invoiceList, List<CreateMatchBetweenTeams> createMatchBetweenTeamsList,String user_id, String user_name, String user_email, String user_phone, String user_dob, String user_address, String team_id) {
        this.invoiceList = invoiceList;
        this.createMatchBetweenTeamsList  = createMatchBetweenTeamsList;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_dob = user_dob;
        this.user_address = user_address;
        this.team_id= team_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_file, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;

        if (invoiceList!=null && createMatchBetweenTeamsList == null){
            Invitation invitation = invoiceList.get(position);
            viewHolderClass.id_post_title.setText(Html.fromHtml("<u><b>INVITATION</b></u>"));

        viewHolderClass.post_captain_email.setText("Captain: " + invitation.getCaptain_email());
        viewHolderClass.post_team_name.setText("Team Name: " +  invitation.getTeam_name());
        viewHolderClass.post_looking_for.setText("Looking for: " + invitation.getLooking_for());
        viewHolderClass.post_message.setText("Message: "+invitation.getMessage());
        viewHolderClass.post_preferred_age.setText("Preferred Age: " + invitation.getPreferred_age());
        viewHolderClass.post_remaining_space.setText("Remaining Space: " + invitation.getRemaining_space());
        team_name = invitation.getTeam_name();


        viewHolderClass.post_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!team_name.matches("")){
                    check_reference.child("team_join_request").orderByChild("user_id").equalTo(user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            Toast.makeText(view.getContext(), team_name, Toast.LENGTH_SHORT).show();
                            if(snapshot.exists()){
                                Toast.makeText(view.getContext(), "Already requested!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String pushKey = reference.push().getKey();
                                JoinTeam joinTeam = new JoinTeam(pushKey , user_id , team_name, user_name , user_email , user_phone , user_dob, user_address);
                                reference.child("team_join_request").child(pushKey).setValue(joinTeam);
                                Toast.makeText(view.getContext(), "Request Sent Successfully.!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
//        viewHolderClass.post_submit_btn.
//        viewHolderClass.recycler_invoice_total.setText(invoice.getTotal_charges());
    }
        else{

            CreateMatchBetweenTeams createMatchBetweenTeams = createMatchBetweenTeamsList.get(position);
            viewHolderClass.id_post_title.setText(Html.fromHtml("<u><b>MATCH</b></u>"));

            if(team_id.matches(createMatchBetweenTeams.getTeam_1().getTeam_id())){
                viewHolderClass.post_captain_email.setText(Html.fromHtml("<b>Opponent Team Name: </b> " +  createMatchBetweenTeams.getTeam_2().getTeam_name()));
                viewHolderClass.post_team_name.setText(Html.fromHtml("<b>Opponent Team Captain: </b> " +  createMatchBetweenTeams.getTeam_2().getEmail_1_captain()));
                viewHolderClass.post_looking_for.setText(Html.fromHtml("<b>Opponent Players : </b> "+createMatchBetweenTeams.getTeam_2().getEmail_1_captain() +"(C), " +createMatchBetweenTeams.getTeam_2().getEmail_2_vice_captain() +"(VC), " + createMatchBetweenTeams.getTeam_2().getEmail_3() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_4() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_5() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_6() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_7() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_8() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_9() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_10() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_11() ));
                if (createMatchBetweenTeams.getMatch_venue().matches("")){
                    viewHolderClass.post_message.setText(Html.fromHtml("<b>Venue: </b> " +  "Not Decided yet, will be confirmed through Direct Message."));

                }
                else{
                    viewHolderClass.post_message.setText(Html.fromHtml("<b>Venue: </b> " +  createMatchBetweenTeams.getMatch_venue()));

                }
                viewHolderClass.post_preferred_age.setText(Html.fromHtml("<b>Your Team: </b> " +  createMatchBetweenTeams.getTeam_1().getTeam_name()));
                if(createMatchBetweenTeams.getMatch_date().matches("")){
                    viewHolderClass.post_remaining_space.setText(Html.fromHtml("<b>Match Date : </b> " +  "Not Decided yet, will be confirmed through Direct Message."));

                }
                else{
                    viewHolderClass.post_remaining_space.setText(Html.fromHtml("<b>Date : </b> " +  createMatchBetweenTeams.getMatch_date()));

                }
//            viewHolderClass.post_submit_btn.setText("Request");
                viewHolderClass.post_submit_btn.setVisibility(View.GONE);
            }
            else if(createMatchBetweenTeams.getTeam_2().getTeam_id().matches(team_id)){
                viewHolderClass.post_captain_email.setText(Html.fromHtml("<b>Opponent Team Name: </b> " +  createMatchBetweenTeams.getTeam_1().getTeam_name()));
                viewHolderClass.post_team_name.setText(Html.fromHtml("<b>Opponent Team Captain: </b> " +  createMatchBetweenTeams.getTeam_1().getEmail_1_captain()));
                viewHolderClass.post_looking_for.setText(Html.fromHtml("<b>Opponent Players : </b> "+createMatchBetweenTeams.getTeam_1().getEmail_1_captain() +"(C), " +createMatchBetweenTeams.getTeam_1().getEmail_2_vice_captain() +"(VC), " + createMatchBetweenTeams.getTeam_1().getEmail_3() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_4() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_5() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_6() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_7() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_8() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_9() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_10() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_11() ));
                if (createMatchBetweenTeams.getMatch_venue().matches("")){
                    viewHolderClass.post_message.setText(Html.fromHtml("<b>Venue: </b> " +  "Not Decided yet, will be confirmed through Direct Message."));

                }
                else{
                    viewHolderClass.post_message.setText(Html.fromHtml("<b>Venue: </b> " +  createMatchBetweenTeams.getMatch_venue()));

                }
                viewHolderClass.post_preferred_age.setText(Html.fromHtml("<b>Your Team: </b> " +  createMatchBetweenTeams.getTeam_2().getTeam_name()));
                if(createMatchBetweenTeams.getMatch_date().matches("")){
                    viewHolderClass.post_remaining_space.setText(Html.fromHtml("<b>Match Date : </b> " +  "Not Decided yet, will be confirmed through Direct Message."));

                }
                else{
                    viewHolderClass.post_remaining_space.setText(Html.fromHtml("<b>Date : </b> " +  createMatchBetweenTeams.getMatch_date()));

                }
//            viewHolderClass.post_submit_btn.setText("Request");
                viewHolderClass.post_submit_btn.setVisibility(View.GONE);
            }


        }
    }


    @Override
    public int getItemCount() {
        if (invoiceList!=null) {
            return invoiceList.size();
        }
        else{
            return createMatchBetweenTeamsList.size();
        }
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView post_captain_email;
        TextView post_team_name;
        TextView post_preferred_age;
        TextView post_message;
        TextView post_remaining_space;
        TextView post_looking_for;
        TextView id_post_title;
        Button post_submit_btn;
        View view;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            id_post_title = itemView.findViewById(R.id.id_post_title);
            post_captain_email = itemView.findViewById(R.id.post_captain_email);
            post_team_name = itemView.findViewById(R.id.post_team_name);
            post_preferred_age = itemView.findViewById(R.id.post_preferred_age);
            post_message = itemView.findViewById(R.id.post_message);
            post_remaining_space = itemView.findViewById(R.id.post_remaining_space);
            post_looking_for = itemView.findViewById(R.id.post_looking_for);
            post_submit_btn = itemView.findViewById(R.id.post_submit_btn);

        }
    }


}
