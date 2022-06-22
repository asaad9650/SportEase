package com.fyp.sporteaze.Event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UpcomingMatchesAdapter extends RecyclerView.Adapter {
    List<CreateMatchBetweenTeams> createMatchBetweenTeamsList;
    String user_id , user_name, user_email, user_phone ,user_dob,user_address , team_name , team_id;
    DatabaseReference date_reference = FirebaseDatabase.getInstance().getReference().child("create_match_between_teams");
    int month, year, day;
    public UpcomingMatchesAdapter(List<CreateMatchBetweenTeams> createMatchBetweenTeamsList, String user_id, String user_name, String user_email, String user_phone, String user_dob, String user_address , String team_id) {
        this.createMatchBetweenTeamsList = createMatchBetweenTeamsList;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_dob = user_dob;
        this.user_address = user_address;
        this.team_id = team_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_matches_layout_file, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CreateMatchBetweenTeams createMatchBetweenTeams = createMatchBetweenTeamsList.get(position);
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        if(createMatchBetweenTeams.getMatch_date().matches("")){
            if(createMatchBetweenTeams.getTeam_1().getTeam_id().matches(team_id)) {
                viewHolderClass.upcoming_match_team_name.setText(Html.fromHtml("<u><b>Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_1().getTeam_name()));
                viewHolderClass.upcoming_match_team_captain.setText(Html.fromHtml("<u><b>Team Captain:</b></u>  " + createMatchBetweenTeams.getTeam_1().getEmail_1_captain()));
                viewHolderClass.upcoming_match_other_players.setText(Html.fromHtml("<u><b>Other Players:</b></u>  " + createMatchBetweenTeams.getTeam_1().getEmail_2_vice_captain() + "(VC), " + createMatchBetweenTeams.getTeam_1().getEmail_3() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_4() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_5() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_6() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_7() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_8() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_9() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_10() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_11()));
                viewHolderClass.upcoming_match_winning_price.setText(Html.fromHtml("<u><b>Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_1().getTeam_name()));
                viewHolderClass.upcoming_match_ground_name.setText(Html.fromHtml("<u><b>Ground Name and Address :</b></u>  Not decided yet" + createMatchBetweenTeams.getMatch_venue()));
                viewHolderClass.upcoming_match_status.setText(Html.fromHtml("<u><b>Status:</b></u>  " + createMatchBetweenTeams.getStatus()));
                viewHolderClass.upcoming_match_opponent_team.setText(Html.fromHtml("<u><b>Opponent Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_2().getTeam_name()));
                viewHolderClass.upcoming_match_starting_time.setText(Html.fromHtml("<u><b>Date:</b></u>  Not decided yet." + createMatchBetweenTeams.getMatch_date()));
                viewHolderClass.upcoming_match_opponent_players.setText(Html.fromHtml("<u><b>Opponent Players:</b></u>  " + createMatchBetweenTeams.getTeam_2().getEmail_1_captain() +"(C), "+ createMatchBetweenTeams.getTeam_2().getEmail_2_vice_captain() + "(VC), " + createMatchBetweenTeams.getTeam_2().getEmail_3() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_4() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_5() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_6() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_7() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_8() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_9() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_10() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_11()));
                viewHolderClass.okay_button.setVisibility(View.GONE);
            }
            else if(createMatchBetweenTeams.getTeam_2().getTeam_id().matches(team_id)){
                viewHolderClass.upcoming_match_team_name.setText(Html.fromHtml("<u><b>Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_2().getTeam_name()));
                viewHolderClass.upcoming_match_other_players.setText(Html.fromHtml("<u><b>Other Players:</b></u>  " + createMatchBetweenTeams.getTeam_2().getEmail_2_vice_captain() + "(VC), " + createMatchBetweenTeams.getTeam_2().getEmail_3() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_4() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_5() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_6() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_7() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_8() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_9() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_10() + ", " + createMatchBetweenTeams.getTeam_2().getEmail_11()));
                viewHolderClass.upcoming_match_team_captain.setText(Html.fromHtml("<u><b>Team Captain:</b></u>  " + createMatchBetweenTeams.getTeam_2().getEmail_1_captain()));
                viewHolderClass.upcoming_match_winning_price.setText(Html.fromHtml("<u><b>Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_2().getTeam_name()));
                viewHolderClass.upcoming_match_ground_name.setText(Html.fromHtml("<u><b>Ground Name and Address :</b></u>  Not decided yet" + createMatchBetweenTeams.getMatch_venue()));
                viewHolderClass.upcoming_match_status.setText(Html.fromHtml("<u><b>Status:</b></u>  " + createMatchBetweenTeams.getStatus()));
                viewHolderClass.upcoming_match_opponent_team.setText(Html.fromHtml("<u><b>Opponent Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_1().getTeam_name()));
                viewHolderClass.upcoming_match_starting_time.setText(Html.fromHtml("<u><b>Date:</b></u>  Not decided yet" + createMatchBetweenTeams.getMatch_date()));
                viewHolderClass.upcoming_match_opponent_players.setText(Html.fromHtml("<u><b>Opponent Players:</b></u>  " +createMatchBetweenTeams.getTeam_1().getEmail_1_captain() +"(C), "+ createMatchBetweenTeams.getTeam_1().getEmail_2_vice_captain() + "(VC), " + createMatchBetweenTeams.getTeam_1().getEmail_3() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_4() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_5() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_6() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_7() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_8() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_9() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_10() + ", " + createMatchBetweenTeams.getTeam_1().getEmail_11()));
                viewHolderClass.okay_button.setVisibility(View.GONE);
            }
        }
        else{
            try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date strDate = sdf.parse(createMatchBetweenTeams.getMatch_date());
            if (new Date().before(strDate)){
                viewHolderClass.upcoming_match_team_name.setText(Html.fromHtml("<u><b>Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_1().getTeam_name()));
                viewHolderClass.upcoming_match_team_captain.setText(Html.fromHtml("<u><b>Team Captain:</b></u>  " +  createMatchBetweenTeams.getTeam_1().getEmail_1_captain()));
                viewHolderClass.upcoming_match_other_players.setText(Html.fromHtml("<u><b>Other Players:</b></u>  " +createMatchBetweenTeams.getTeam_1().getEmail_2_vice_captain() +"(VC), " + createMatchBetweenTeams.getTeam_1().getEmail_3() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_4() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_5() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_6() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_7() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_8() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_9() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_10() + ", "+ createMatchBetweenTeams.getTeam_1().getEmail_11() ));
                viewHolderClass.upcoming_match_winning_price.setText(Html.fromHtml("<u><b>Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_1().getTeam_name()));
                viewHolderClass.upcoming_match_ground_name.setText(Html.fromHtml("<u><b>Ground Name and Address :</b></u>  " + createMatchBetweenTeams.getMatch_venue()));
                viewHolderClass.upcoming_match_status.setText(Html.fromHtml("<u><b>Status:</b></u>  " + "Played"));
                viewHolderClass.upcoming_match_opponent_team.setText(Html.fromHtml("<u><b>Opponent Team Name:</b></u>  " + createMatchBetweenTeams.getTeam_2().getTeam_name()));
                viewHolderClass.upcoming_match_starting_time.setText(Html.fromHtml("<u><b>Date:</b></u>  " + createMatchBetweenTeams.getMatch_date()));
                viewHolderClass.upcoming_match_opponent_players.setText(Html.fromHtml("<u><b>Opponent Players:</b></u>  " +createMatchBetweenTeams.getTeam_2().getEmail_1_captain() +"(C), "+createMatchBetweenTeams.getTeam_2().getEmail_2_vice_captain() +"(VC), " + createMatchBetweenTeams.getTeam_2().getEmail_3() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_4() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_5() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_6() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_7() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_8() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_9() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_10() + ", "+ createMatchBetweenTeams.getTeam_2().getEmail_11() ));
//                viewHolderClass.okay_button.setVisibility(View.GONE);
                viewHolderClass.postpone_button.setVisibility(View.GONE);
                viewHolderClass.cancel_match_button.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            Toast.makeText(holder.itemView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        }


        viewHolderClass.postpone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();

                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(), new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                try{
                                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                    Date strDate = sdf.parse(dayOfMonth + "/" + (monthOfYear+1) +"/" +year);

                                    if(new Date().before(strDate)){
                                        date_reference.child(createMatchBetweenTeams.getCreated_team_match_id()).child("match_date").setValue(dayOfMonth + "/" + (monthOfYear+1) +"/" +year);
                                        viewHolderClass.upcoming_match_starting_time.setText(Html.fromHtml("<u><b>Date:</b></u>  " + dayOfMonth + "/" + (monthOfYear+1) +"/" +year));

                                    }
                                    else{
                                        Toast.makeText(view.getContext(), "You cannot set a past date for upcoming match.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e){
                                    Toast.makeText(view.getContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                                }

//                                    if(new Date().before()){}


                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        viewHolderClass.cancel_match_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertbox = new AlertDialog.Builder(view.getContext())
                        .setTitle("Cancel")
                        .setMessage("Are you sure you want to cancel match?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {

                                DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                                db.child("create_match_between_teams").child(createMatchBetweenTeams.getCreated_team_match_id()).child("status").removeValue();
//                                Intent intent = new Intent(context , LoginActivity.class);
//                                context.startActivity(intent);
//                                ((Activity)context).finish();

                                // finish();
                                //close();


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        })
                        .show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return createMatchBetweenTeamsList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView upcoming_match_team_name;
        TextView upcoming_match_ground_name;
        TextView upcoming_match_winning_price;
        TextView upcoming_match_team_captain;
        TextView upcoming_match_other_players;
        TextView upcoming_match_status;
        TextView upcoming_match_starting_time;

        TextView upcoming_match_opponent_team;
        TextView upcoming_match_opponent_players;
        Button okay_button;
        Button postpone_button;
        Button cancel_match_button;
        View view;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);

            cancel_match_button = itemView.findViewById(R.id.cancel_match_button);
            postpone_button = itemView.findViewById(R.id.postpone_button);
            okay_button = itemView.findViewById(R.id.okay_button);
            upcoming_match_opponent_players = itemView.findViewById(R.id.upcoming_match_opponent_players);
            upcoming_match_opponent_team = itemView.findViewById(R.id.upcoming_match_opponent_team);
            upcoming_match_starting_time = itemView.findViewById(R.id.upcoming_match_starting_time);
            upcoming_match_status = itemView.findViewById(R.id.upcoming_match_status);
            upcoming_match_other_players = itemView.findViewById(R.id.upcoming_match_other_players);
            upcoming_match_team_captain = itemView.findViewById(R.id.upcoming_match_team_captain);
            upcoming_match_winning_price = itemView.findViewById(R.id.upcoming_match_winning_price);
            upcoming_match_ground_name = itemView.findViewById(R.id.upcoming_match_ground_name);
            upcoming_match_team_name = itemView.findViewById(R.id.upcoming_match_team_name);

        }
    }

}
