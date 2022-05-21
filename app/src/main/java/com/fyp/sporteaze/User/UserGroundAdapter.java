package com.fyp.sporteaze.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Academy.Ground.AcademyGroundUpdate;
import com.fyp.sporteaze.Academy.Ground.ViewGroundAdapter;
import com.fyp.sporteaze.Model.Ground;
import com.fyp.sporteaze.Model.GroundBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserGroundAdapter extends RecyclerView.Adapter {

    List<Ground> groundList;
    String user_id, user_name,user_email;


    public UserGroundAdapter(List<Ground> groundList, String user_id, String user_name, String user_email) {
        this.groundList = groundList;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grounds_layyout_file, parent,false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;
        Ground grounds= groundList.get(position);
        viewHolderClass.recycler_ground_name.setText("Name: "+ grounds.getGround_name());
        viewHolderClass.recycler_ground_serial.setText("Ground ID: " + grounds.getGround_id());




//        Bundle extras = intent.getExtras();
//        user_id = extras.getString("user_id");

//        viewHolderClass.btn_ground_view_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), user_id + " ye aya hai bhai", Toast.LENGTH_SHORT).show();            }
//        });
        viewHolderClass.btn_ground_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.user_view_ground_popup,null);
                TextView txt_ground_id = dialogView.findViewById(R.id.txt_user_ground_id);
                TextView txt_ground_name = dialogView.findViewById(R.id.txt_user_ground_name);
                TextView txt_ground_location = dialogView.findViewById(R.id.txt_user_ground_location);
                TextView txt_ground_timings = dialogView.findViewById(R.id.txt_user_ground_timings);
                TextView txt_ground_size = dialogView.findViewById(R.id.txt_user_ground_size);
//                TextView txt_medical_trainer_availability = dialogView.findViewById(R.id.txt_user_medical_trainer_availability);
//                TextView txt_analyst_availability = dialogView.findViewById(R.id.txt_user_analyst_availability);
                EditText ground_booking_purpose = dialogView.findViewById(R.id.ground_booking_purpose);
                CheckBox with_medical_trainer = dialogView.findViewById(R.id.with_medical_trainer);
                CheckBox with_analyst  =dialogView.findViewById(R.id.with_analyst);
                //                TextView txt_coach_phone = dialogView.findViewById(R.id.txt_coach_phone);


                AppCompatButton btn_book_ground = dialogView.findViewById(R.id.btn_book_ground);
//                AppCompatButton btn_popup_ground_delete = dialogView.findViewById(R.id.btn_popup_ground_delete);

                txt_ground_id.setText("Name: " + grounds.getGround_name());
                txt_ground_name.setText("Location: "+grounds.getGround_address());
                txt_ground_location.setText("Size: "+grounds.getGround_size());
                txt_ground_size.setText("Timings: "+grounds.getGround_timings());
                txt_ground_timings.setText("Charges: " + grounds.getGround_charges());
//                txt_medical_trainer_availability.setText("Medical Trainer: "+grounds.getGround_medical_trainer_availability());
//                txt_analyst_availability.setText("Analyst: "+grounds.getGround_analyst_availability());
//                txt_coach_phone.setText("Phone: "+coaches.getCoach_phone());
                btn_book_ground.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(user_name.equals("") || user_name == null){
                            if(!ground_booking_purpose.getText().toString().trim().matches("")){
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference();
                                String key = root.push().getKey();
                                if(with_analyst.isChecked() && with_medical_trainer.isChecked()){
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_email,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"yes" , "yes",grounds.getGround_charges());
                                    root.child("ground_bookings").child(user_id).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                                else if(with_analyst.isChecked() && !(with_medical_trainer.isChecked())){
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_email,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"no" , "yes"  , grounds.getGround_charges());
                                    root.child("ground_bookings").child(user_id).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                                else if (with_medical_trainer.isChecked() && !(with_analyst.isChecked())){
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_email,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"yes" , "no" , grounds.getGround_charges());
                                    root.child("ground_bookings").child(user_id).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                                else{
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_email,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"no" , "no", grounds.getGround_charges());
                                    root.child("ground_bookings").child(user_id).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                            }

                            else{
                                Toast.makeText(view.getContext(), "Please enter purpose", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            if(!ground_booking_purpose.getText().toString().trim().matches("")){
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference root = db.getReference();
                                String key = root.push().getKey();
                                if(with_analyst.isChecked() && with_medical_trainer.isChecked()){
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_name,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"yes" , "yes",grounds.getGround_charges());
                                    root.child("ground_bookings").child(key).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                                else if(with_analyst.isChecked() && !(with_medical_trainer.isChecked())){
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_name,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"no" , "yes"  , grounds.getGround_charges());
                                    root.child("ground_bookings").child(key).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                                else if (with_medical_trainer.isChecked() && !(with_analyst.isChecked())){
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_name,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"yes" , "no" , grounds.getGround_charges());
                                    root.child("ground_bookings").child(key).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                                else{
                                    GroundBooking groundBooking = new GroundBooking(grounds.getAcademy_id(),"pending",user_name,key,grounds.getGround_id(), grounds.getGround_name() , grounds.getGround_address() , grounds.getGround_size(),grounds.getGround_timings(),ground_booking_purpose.getText().toString(),"no" , "no", grounds.getGround_charges());
                                    root.child("ground_bookings").child(key).setValue(groundBooking);
                                    Toast.makeText(view.getContext(), "Ground booked successfully", Toast.LENGTH_SHORT).show();
                                    builder.create().cancel();
                                }
                            }

                            else{
                                Toast.makeText(view.getContext(), "Please enter purpose", Toast.LENGTH_SHORT).show();
                            }
                        }

//                        AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
//                        View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.user_view_ground_popup,null);
//                        dialogView.findViewById(R.id.btn_book_ground);
                    }
                });




//                Glide.with(view.getContext()).load(users.academy_image).into(img_academy_view_details_image);

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return groundList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView recycler_ground_name;
        TextView recycler_ground_serial;
        View view;
        AppCompatButton btn_ground_view_details;
        AppCompatButton btn_academy_delete;



        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            recycler_ground_name = itemView.findViewById(R.id.recycler_ground_name);
            recycler_ground_serial = itemView.findViewById(R.id.recycler_ground_serial);
//            users_email = itemView.findViewById(R.id.recylcer_user_email);
            btn_ground_view_details=itemView.findViewById(R.id.btn_ground_view_details);
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
