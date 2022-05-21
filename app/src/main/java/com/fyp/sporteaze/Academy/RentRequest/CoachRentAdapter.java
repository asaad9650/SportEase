package com.fyp.sporteaze.Academy.RentRequest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.CancellationSignal;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Academy.Coach.ViewCoachAdapter;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CoachRentAdapter extends RecyclerView.Adapter {

    List<CoachBooking> coachBookingList;




    public CoachRentAdapter(List<CoachBooking> coachBookingList) {
        this.coachBookingList = coachBookingList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.academy_coach_rent_layout, parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        CoachBooking coachBooking= coachBookingList.get(position);
        SpannableString content = new SpannableString("Details");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        viewHolderClass.recycler_coach_booking_date_and_time.setText(coachBooking.getCoach_name());
        viewHolderClass.recycler_coach_booking_details.setText(content);

//        viewHolderClass.recycler_coach_booking_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), "Ye hai bhai " + coachBooking.getCoach_booked_by(), Toast.LENGTH_SHORT).show();
//            }
//        });

        viewHolderClass.recycler_coach_booking_details.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.coach_rent_popup,null);
                TextView txt_coach_request_by = dialogView.findViewById(R.id.txt_coach_request_by);
                TextView txt_coach_request_purpose = dialogView.findViewById(R.id.txt_coach_request_purpose);
                TextView txt_coach_request_coach_selected = dialogView.findViewById(R.id.txt_coach_request_coach_selected);
                TextView txt_coach_request_coach_charges = dialogView.findViewById(R.id.txt_coach_request_coach_charges);
                AppCompatButton btn_popup_coach_request_close = dialogView.findViewById(R.id.btn_popup_coach_request_close);
                btn_popup_coach_request_close.setVisibility(View.GONE);
                txt_coach_request_by.setText("From: " + coachBooking.getCoach_booked_by());
                txt_coach_request_purpose.setText("Purpose: "+coachBooking.getCoach_booking_purpose());
                txt_coach_request_coach_selected.setText("Coach Selected: "+coachBooking.getCoach_name());
                txt_coach_request_coach_charges.setText("Charges: "+coachBooking.getCoach_charges());
//                Button button = builder.getB(DialogInterface.BUTTON_POSITIVE);
//                btn_popup_coach_request_close.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                    public void onClick(View view) {
//                        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            @Override
//                            public void onCancel(DialogInterface dialogInterface) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                    }
//                });

//                builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });


//                btn_popup_coach_request_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        builder.
//
//                    }
//                });


                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

            }
        });
        viewHolderClass.btn_accept_coach_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("coach_bookings").child(coachBooking.getCoach_booking_id()).child("coach_request_status").setValue("accepted");
                coachBookingList.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
            }
        });

        viewHolderClass.btn_reject_coach_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("coach_bookings").child(coachBooking.getCoach_booking_id()).child("coach_request_status").setValue("rejected");
                coachBookingList.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return coachBookingList.size();
    }


    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView recycler_coach_booking_date_and_time;
        TextView recycler_coach_booking_details;
        View view;
        AppCompatButton btn_accept_coach_booking;
        AppCompatButton btn_reject_coach_booking;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            recycler_coach_booking_date_and_time = itemView.findViewById(R.id.recycler_coach_booking_date_and_time);
            recycler_coach_booking_details = itemView.findViewById(R.id.recycler_coach_booking_details);
//            users_email = itemView.findViewById(R.id.recylcer_user_email);
            btn_accept_coach_booking=itemView.findViewById(R.id.btn_accept_coach_booking);
            btn_reject_coach_booking=itemView.findViewById(R.id.btn_reject_coach_booking);

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
