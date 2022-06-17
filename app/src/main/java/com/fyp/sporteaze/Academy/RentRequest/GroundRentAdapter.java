package com.fyp.sporteaze.Academy.RentRequest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.Model.GroundBooking;
import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.TeamGroundBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class GroundRentAdapter extends RecyclerView.Adapter {

    List<TeamGroundBooking> groundBookingList;
    DatabaseReference update_date_reference = FirebaseDatabase.getInstance().getReference();
    String team_id1;
    String team_id2;
    String create_match_key;
    public GroundRentAdapter(List<TeamGroundBooking> groundBookingList) {
        this.groundBookingList = groundBookingList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ground_rent_layout, parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        TeamGroundBooking groundBooking = groundBookingList.get(position);
        SpannableString content = new SpannableString("Details");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        viewHolderClass.recycler_ground_booking_date_and_time.setText(groundBooking.getGround_name().toString());
        viewHolderClass.recycler_ground_booking_details.setText(content);


        viewHolderClass.recycler_ground_booking_details.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.ground_rent_popup,null);
                TextView txt_ground_request_by = dialogView.findViewById(R.id.txt_ground_request_by);
                TextView txt_ground_request_purpose = dialogView.findViewById(R.id.txt_ground_request_purpose);
                TextView txt_ground_request_timings = dialogView.findViewById(R.id.txt_ground_request_timings);
                TextView txt_ground_request_ground_charges = dialogView.findViewById(R.id.txt_ground_request_ground_charges);
                AppCompatButton btn_popup_coach_request_close = dialogView.findViewById(R.id.btn_popup_coach_request_close);
                btn_popup_coach_request_close.setVisibility(View.GONE);

                txt_ground_request_by.setText("From: " + groundBooking.getTeam_name());
                txt_ground_request_purpose.setText("Date: "+groundBooking.getGround_date());
                txt_ground_request_timings.setText("Timings: "+groundBooking.getGround_timings());
                txt_ground_request_ground_charges.setText("Charges: "+groundBooking.getGround_name());
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

            }
        });


        viewHolderClass.btn_accept_ground_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("team_ground_bookings").child(groundBooking.getBooking_id()).child("request_status").setValue("accepted");
                groundBookingList.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
                update_date_reference.child("create_match_between_teams").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            CreateMatchBetweenTeams createMatchBetweenTeams = ds.getValue(CreateMatchBetweenTeams.class);
                            team_id1 = createMatchBetweenTeams.getTeam_1().getTeam_id();
                            team_id2 = createMatchBetweenTeams.getTeam_2().getTeam_id();
//                            create_match_key = ds.getKey();
                            if (team_id1.matches(groundBooking.getTeam_id()) || team_id2.matches(groundBooking.getTeam_id())) {
                                Toast.makeText(view.getContext(), ds.getKey(), Toast.LENGTH_SHORT).show();
                                update_date_reference.child("create_match_between_teams").child(ds.getKey()).child("match_date").setValue(groundBooking.getGround_date());
                                update_date_reference.child("create_match_between_teams").child(ds.getKey()).child("match_venue").setValue(groundBooking.getGround_name() + " " + groundBooking.getGround_location());
                                break;
                            }
                        }
//                        if (team_id1.matches(groundBooking.getTeam_id())){
//                            Toast.makeText(view.getContext(), "Pehli match", Toast.LENGTH_SHORT).show();
//                        }
//                        else if(team_id2.matches(groundBooking.getTeam_id())){
//                            Toast.makeText(view.getContext(), "Dusri match", Toast.LENGTH_SHORT).show();
//
//                        }
//                        Toast.makeText(view.getContext(), snapshot.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        viewHolderClass.btn_reject_ground_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("team_ground_bookings").child(groundBooking.getBooking_id()).child("request_status").setValue("rejected");
                groundBookingList.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//                databaseReference.child("ground_bookings").child(groundBooking.getGround_booking_id()).child("ground_request_status").setValue("rejected");
//                groundBookingList.remove(holder.getAbsoluteAdapterPosition());
//                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groundBookingList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView recycler_ground_booking_date_and_time;
        TextView recycler_ground_booking_details;
        View view;
        AppCompatButton btn_accept_ground_booking;
        AppCompatButton btn_reject_ground_booking;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            recycler_ground_booking_date_and_time = itemView.findViewById(R.id.recycler_ground_booking_date_and_time);
            recycler_ground_booking_details = itemView.findViewById(R.id.recycler_ground_booking_details);
//            users_email = itemView.findViewById(R.id.recylcer_user_email);
            btn_accept_ground_booking=itemView.findViewById(R.id.btn_accept_ground_booking);
            btn_reject_ground_booking=itemView.findViewById(R.id.btn_reject_ground_booking);

//            btn_academy_view_details.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Intent intent = new Intent( view.getContext(), AdminViewAcademyDetails.class);
//                    intent.putExtra("academy_image", get);
//                    view.getContext().startActivity(intent);
//
//
//                }
//            });

        }
    }
}
