package com.fyp.sporteaze.Event;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.Model.Match;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ShowCreatedPostAdapter extends RecyclerView.Adapter {
    List<Match> matchList;
    String key , team_id;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceMatchList1;
    List<Match> matchList1;


    public ShowCreatedPostAdapter(List<Match> matchList, String key , String team_id) {
        this.matchList = matchList;
        this.key = key;
        this.team_id = team_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.created_post_layout_file, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        Match match = matchList.get(position);
        viewHolderClass.txt_match_id.setText("Match ID: " + match.getMatch_id());

        databaseReference = FirebaseDatabase.getInstance().getReference();
        viewHolderClass.ll_view_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                AlertDialog alertDialog = builder.create();
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.created_post_popup,null);
                TextView txt_popup_team_name = dialogView.findViewById(R.id.txt_popup_team_name);
                TextView txt_popup_ground_booked = dialogView.findViewById(R.id.txt_popup_ground_booked);
                TextView txt_popup_winning_price = dialogView.findViewById(R.id.txt_popup_winning_price);
                TextView txt_popup_captain = dialogView.findViewById(R.id.txt_popup_captain);
                TextView txt_popup_other_players = dialogView.findViewById(R.id.txt_popup_other_players);
                TextView txt_popup_matches_played = dialogView.findViewById(R.id.txt_popup_matches_played);
                TextView txt_popup_matches_won = dialogView.findViewById(R.id.txt_popup_matches_won);
                TextView txt_popup_matches_lost = dialogView.findViewById(R.id.txt_popup_matches_lost);
                LinearLayout ll_created_post = dialogView.findViewById(R.id.ll_created_post);;
                AppCompatButton btn_popup_request_match  = dialogView.findViewById(R.id.btn_popup_request_match);
//                ll_created_post = itemView.
                AppCompatButton btn_popup_delete_post = dialogView.findViewById(R.id.btn_popup_delete_post);

                txt_popup_captain.setText(Html.fromHtml("<b>CAPTAIN: </b> " + match.getEmail_1_captain()));
                txt_popup_team_name.setText(Html.fromHtml("<b>TEAM NAME: </b> " + match.getTeam_name()));
                txt_popup_ground_booked.setText(Html.fromHtml("<b>GROUND BOOKED: </b> " + match.getGround_booked()));
                txt_popup_winning_price.setText(Html.fromHtml("<b>WINNING PRICE: </b> " + match.getWinning_price()));
                txt_popup_other_players.setText(Html.fromHtml("<b>OTHER PLAYERS: </b> " + match.getEmail_2_vice_captain() + "(VC), " + match.getEmail_3() + ", " + match.getEmail_4() + ", "+match.getEmail_5() + ", "+ match.getEmail_6()+ ", "+match.getEmail_7() + ", "+ match.getEmail_8() + ", "+ match.getEmail_10() + ", "+ match.getEmail_11()));
                txt_popup_matches_played.setText(Html.fromHtml("<b>TOTAL MATCHES PLAYED: </b> " + match.getTotal_matches_played()));
                txt_popup_matches_won.setText(Html.fromHtml("<b>MATCHES WON: </b> " + match.getMatches_won()));
                txt_popup_matches_lost.setText(Html.fromHtml("<b>MATCHES LOST: </b> " + match.getMatches_lost()));

                if (match.getTeam_id().matches(team_id)){
                    btn_popup_delete_post.setVisibility(View.VISIBLE);
                    btn_popup_request_match.setVisibility(View.GONE);
                }
                else{
                    btn_popup_delete_post.setVisibility(View.GONE);
                    btn_popup_request_match.setVisibility(View.VISIBLE);
                }

                btn_popup_request_match.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReferenceMatchList1 = FirebaseDatabase.getInstance().getReference();
                        databaseReferenceMatchList1.child("teams").child(team_id).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Match match1 = snapshot.getValue(Match.class);
                                DatabaseReference create_match_ref = FirebaseDatabase.getInstance().getReference();
                                String match_key  = create_match_ref.push().getKey();
                                CreateMatchBetweenTeams createMatchBetweenTeams = new CreateMatchBetweenTeams(match_key,match.getTeam_id(),match  , match1 , "" , "" , "pending");
                                create_match_ref.child("create_match_between_teams").child(match_key).setValue(createMatchBetweenTeams);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        Toast.makeText(view.getContext(), "Requested for a match successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                btn_popup_delete_post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                        viewHolderClass.ll_requests.setVisibility(View.GONE);
                        databaseReference.child("events").child(key).removeValue();
                    }
                });
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

            }
        });




    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView txt_match_id;
        LinearLayout ll_view_post;
        LinearLayout ll_requests;
        View view;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            ll_requests = itemView.findViewById(R.id.ll_requests);
            ll_view_post = itemView.findViewById(R.id.ll_view_post);
            txt_match_id = itemView.findViewById(R.id.txt_match_id);



        }
    }

}
