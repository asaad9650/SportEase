package com.fyp.sporteaze.Event;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AcceptedMatchRequestAdapter extends RecyclerView.Adapter {
    List<CreateMatchBetweenTeams> createMatchBetweenTeamsList;
    String team_id;
    String key;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    public AcceptedMatchRequestAdapter(List<CreateMatchBetweenTeams> createMatchBetweenTeamsList, String team_id , String key) {
        this.createMatchBetweenTeamsList = createMatchBetweenTeamsList;
        this.team_id = team_id;
        this.key = key;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accepted_match_request_layout_file, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        CreateMatchBetweenTeams acceptedMatchRequest = createMatchBetweenTeamsList.get(position);
//        viewHolderClass.accepted_by_team_captain.setText("Team Captain " + acceptedMatchRequest.getRequest_initiated_by());
        viewHolderClass.accepted_by_team_name.setText(Html.fromHtml("<b>Team Name: </b> " + acceptedMatchRequest.getTeam_2().getTeam_name()));
        viewHolderClass.accepted_by_team_captain.setText(Html.fromHtml("<b>Captain : </b> "+  acceptedMatchRequest.getTeam_2().getEmail_1_captain()));
        viewHolderClass.accepted_by_team_players.setText(Html.fromHtml("<b>Other Players : </b> "+acceptedMatchRequest.getTeam_2().getEmail_2_vice_captain() +"(VC), " + acceptedMatchRequest.getTeam_2().getEmail_3() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_4() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_5() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_6() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_7() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_8() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_9() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_10() + ", "+ acceptedMatchRequest.getTeam_2().getEmail_11() ));
    viewHolderClass.accepted_by_btn_accept.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            databaseReference.child("create_match_between_teams").child(key).child("status").setValue("accepted");
            Toast.makeText(view.getContext(), "Accepted Successfully!", Toast.LENGTH_SHORT).show();
            viewHolderClass.accepted_request_ll.setVisibility(View.GONE);
            databaseReference.child("create_match_between_teams").child(key).child("status").setValue("accepted");

        }
    });
        viewHolderClass.accepted_by_btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("create_match_between_teams").child(key).child("status").setValue("rejected");
                Toast.makeText(view.getContext(), "Request Rejected!", Toast.LENGTH_SHORT).show();
                viewHolderClass.accepted_request_ll.setVisibility(View.GONE);
                databaseReference.child("create_match_between_teams").child(key).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return createMatchBetweenTeamsList.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView accepted_by_team_name;
        TextView accepted_by_team_captain;
        TextView accepted_by_team_players;
        AppCompatButton accepted_by_btn_accept;
        AppCompatButton accepted_by_btn_reject;
        LinearLayout accepted_request_ll;
        View view;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            accepted_request_ll = itemView.findViewById(R.id.accepted_request_ll);
            accepted_by_team_name = itemView.findViewById(R.id.accepted_by_team_name);
            accepted_by_team_captain = itemView.findViewById(R.id.accepted_by_team_captain);
            accepted_by_team_players = itemView.findViewById(R.id.accepted_by_team_players);
            accepted_by_btn_accept = itemView.findViewById(R.id.accepted_by_btn_accept);
            accepted_by_btn_reject = itemView.findViewById(R.id.accepted_by_btn_reject);


        }
    }

}
