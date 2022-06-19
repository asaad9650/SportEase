package com.fyp.sporteaze.User;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class IndividualCoachAdapter extends RecyclerView.Adapter {


    List<Coach> coachList;
    String user_id, user_name,user_email;

    public IndividualCoachAdapter(List<Coach> coachList, String user_id, String user_name , String user_email) {
        this.coachList = coachList;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coaches_layout_file, parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        Coach coaches= coachList.get(position);
        viewHolderClass.recycler_coach_name.setText("Name: " +coaches.getCoach_name());
        viewHolderClass.recycler_coach_serial.setText("Coach ID: " + coaches.getCoach_id());

        viewHolderClass.btn_coach_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.individual_coach_booking_popup,null);
                TextView txt_user_coach_id = dialogView.findViewById(R.id.txt_user_coach_id);
                TextView txt_user_coach_name = dialogView.findViewById(R.id.txt_user_coach_name);
                TextView txt_user_coach_address = dialogView.findViewById(R.id.txt_user_coach_address);
                TextView txt_user_coach_age = dialogView.findViewById(R.id.txt_user_coach_age);
                TextView txt_user_coach_expertise = dialogView.findViewById(R.id.txt_user_coach_expertise);
                TextView txt_user_coach_charges = dialogView.findViewById(R.id.txt_user_coach_charges);
                TextView txt_user_coach_phone = dialogView.findViewById(R.id.txt_user_coach_phone);
                EditText individual_coach_booking_purpose = dialogView.findViewById(R.id.individual_coach_booking_purpose);
                individual_coach_booking_purpose.setVisibility(View.GONE);
//                CheckBox individual_with_analyst = dialogView.findViewById(R.id.individual_with_analyst);
//                CheckBox individual_with_medical_trainer = dialogView.findViewById(R.id.individual_with_medical_trainer);

                AppCompatButton btn_book_coach = dialogView.findViewById(R.id.btn_book_coach);

                txt_user_coach_id.setText("Coach ID: " + coaches.getCoach_id());
                txt_user_coach_name.setText("Name: "+coaches.getCoach_name());
                txt_user_coach_address.setText("Address: "+coaches.getCoach_address());
                txt_user_coach_age.setText("Age: "+coaches.getCoach_age());
                txt_user_coach_expertise.setText("Expertise: "+coaches.getCoach_expertise());
                txt_user_coach_charges.setText("Charges: "+coaches.getCoach_charges());
                txt_user_coach_phone.setText("Phone: "+coaches.getCoach_phone());
                btn_book_coach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference();
                        String key = root.push().getKey();
//                        if(individual_coach_booking_purpose.getText().toString().trim().matches("")){
//                            Toast.makeText(dialogView.getContext(), "Please enter purpose", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
                            if(user_name.equals("") || user_name == null){
                                CoachBooking coachBooking = new CoachBooking(coaches.getAcademy_id(),"pending",user_email,user_id, coaches.getCoach_id() , coaches.getCoach_name(),coaches.getCoach_address(), coaches.getCoach_age(), coaches.getCoach_expertise(), coaches.getCoach_phone(), coaches.getCoach_charges(), "individual");
                                root.child("coach_bookings").child(user_id).setValue(coachBooking);
                                DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
                                db_ref.child("Users").child(user_id).child("individual_coach_booking_id").setValue(key);
                                Toast.makeText(dialogView.getContext(), "Coach booked successfully.", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                CoachBooking coachBooking = new CoachBooking(coaches.getAcademy_id(),"pending",user_name,user_id, coaches.getCoach_id() , coaches.getCoach_name(),coaches.getCoach_address(), coaches.getCoach_age(), coaches.getCoach_expertise(), coaches.getCoach_phone(), coaches.getCoach_charges(), "individual");
                                root.child("coach_bookings").child(user_id).setValue(coachBooking);
                                DatabaseReference db_ref = FirebaseDatabase.getInstance().getReference();
                                db_ref.child("Users").child(user_id).child("individual_coach_booking_id").setValue(key);
                                Toast.makeText(dialogView.getContext(), "Coach booked successfully.", Toast.LENGTH_SHORT).show();
                            }

//                        }

//                        if(radioButton_for_personal.isChecked() || radioButton_for_team.isChecked()){
//
//                            if(radioButton_for_personal.isChecked()){
//                                CoachBooking coachBooking = new CoachBooking("pending",user_id,key, coaches.getCoach_id() , coaches.getCoach_name(),coaches.getCoach_address(), coaches.getCoach_age(), coaches.getCoach_expertise(), coaches.getCoach_phone(), coaches.getCoach_charges(), "personal training");
//                                root.child("coach_bookings").child(key).setValue(coachBooking);
//                                Toast.makeText(dialogView.getContext(), "Coach booked successfully.", Toast.LENGTH_SHORT).show();
//
//                            }
//                            else{
//                                CoachBooking coachBooking = new CoachBooking("pending",user_id,key, coaches.getCoach_id() , coaches.getCoach_name(),coaches.getCoach_address(), coaches.getCoach_age(), coaches.getCoach_expertise(), coaches.getCoach_phone(), coaches.getCoach_charges(), "team training");
//                                root.child("coach_bookings").child(key).setValue(coachBooking);
//                                Toast.makeText(dialogView.getContext(), "Coach booked successfully.", Toast.LENGTH_SHORT).show();
//
//                            }
//
//                        }
//                        else{
//                            Toast.makeText(dialogView.getContext(), "Please select one option", Toast.LENGTH_SHORT).show();
//                        }


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
        return coachList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView recycler_coach_name;
        TextView recycler_coach_serial;
        View view;
        AppCompatButton btn_coach_view_details;
        AppCompatButton btn_academy_delete;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            recycler_coach_name = itemView.findViewById(R.id.recycler_coach_name);
            recycler_coach_serial = itemView.findViewById(R.id.recycler_coach_serial);
//            users_email = itemView.findViewById(R.id.recylcer_user_email);
            btn_coach_view_details=itemView.findViewById(R.id.btn_coach_view_details);
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
