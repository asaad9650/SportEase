package com.fyp.sporteaze.Academy.RentRequest;

import android.app.AlertDialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.Academy_registration_individual;
import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AcademyRentAdapter extends RecyclerView.Adapter {

    List<Academy_registration_individual> academy_registrationList;
    String academy_id;

    public AcademyRentAdapter(List<Academy_registration_individual> academy_registrationList, String academy_id) {
        this.academy_registrationList = academy_registrationList;
        this.academy_id = academy_id;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.academy_rent_layout, parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        Academy_registration_individual academy_registration = academy_registrationList.get(position);
//        SpannableString content = new SpannableString("Details");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        viewHolderClass.recycler_academy_booking_date_and_time.setText(academy_registration.getUser_name());
        viewHolderClass.recycler_academy_booking_details.setText(academy_registration.getUser_email());

        viewHolderClass.btn_view_academy_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.academy_rent_popup,null);
                TextView requested_by_name = dialogView.findViewById(R.id.requested_by_name);
                TextView requested_by_age = dialogView.findViewById(R.id.requested_by_age);
                TextView requested_by_email = dialogView.findViewById(R.id.requested_by_email);
                TextView requested_by_address = dialogView.findViewById(R.id.requested_by_address);
                AppCompatButton btn_popup_academy_request_accept = dialogView.findViewById(R.id.btn_popup_academy_request_accept);
                AppCompatButton btn_popup_academy_request_reject = dialogView.findViewById(R.id.btn_popup_academy_request_reject);

                requested_by_name.setText("From: " + academy_registration.getUser_name());
                requested_by_age.setText("Email: " + academy_registration.getUser_email());
                requested_by_email.setText("Phone: " + academy_registration.getUser_phone());
                requested_by_address.setText("Address: " + academy_registration.getUser_address());


                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();


                btn_popup_academy_request_accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                        reference.child("academy_individual_bookings").child(academy_registration.getUser_id()).child("request_status").setValue("accepted");
                        academy_registrationList.remove(holder.getAbsoluteAdapterPosition());
                        notifyDataSetChanged();
                    }
                });


                btn_popup_academy_request_reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

                        reference.child("academy_individual_bookings").child(academy_registration.getUser_id()).child("request_status").setValue("rejected");
                        academy_registrationList.remove(holder.getAbsoluteAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {

        return academy_registrationList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView recycler_academy_booking_date_and_time;
        TextView recycler_academy_booking_details;
        View view;
        AppCompatButton btn_view_academy_booking;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            recycler_academy_booking_date_and_time = itemView.findViewById(R.id.recycler_academy_booking_date_and_time);
            recycler_academy_booking_details = itemView.findViewById(R.id.recycler_academy_booking_details);
//            users_email = itemView.findViewById(R.id.recylcer_user_email);
            btn_view_academy_booking=itemView.findViewById(R.id.btn_view_academy_booking);

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
