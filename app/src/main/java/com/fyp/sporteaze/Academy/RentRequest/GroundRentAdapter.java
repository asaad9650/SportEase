package com.fyp.sporteaze.Academy.RentRequest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.Model.GroundBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class GroundRentAdapter extends RecyclerView.Adapter {

    List<GroundBooking> groundBookingList;


    public GroundRentAdapter(List<GroundBooking> groundBookingList) {
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


        GroundBooking groundBooking = groundBookingList.get(position);
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

                txt_ground_request_by.setText("From: " + groundBooking.getBooked_by_id());
                txt_ground_request_purpose.setText("Purpose: "+groundBooking.getGround_booking_purpose());
                txt_ground_request_timings.setText("Timings: "+groundBooking.getGround_timings());
                txt_ground_request_ground_charges.setText("Charges: "+groundBooking.getGround_charges());
//                Button button = builder.getB(DialogInterface.BUTTON_POSITIVE);
//                btn_popup_coach_request_close.setOnClickListener(new View.OnClickListener() {
//                    @Override
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
        viewHolderClass.btn_accept_ground_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("ground_bookings").child(groundBooking.getGround_booking_id()).child("ground_request_status").setValue("accepted");
                groundBookingList.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
            }
        });

        viewHolderClass.btn_reject_ground_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("ground_bookings").child(groundBooking.getGround_booking_id()).child("ground_request_status").setValue("rejected");
                groundBookingList.remove(holder.getAbsoluteAdapterPosition());
                notifyDataSetChanged();
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
