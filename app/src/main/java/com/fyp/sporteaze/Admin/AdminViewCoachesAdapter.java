package com.fyp.sporteaze.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.sporteaze.Academy.Coach.AcademyCoachUpdate;
import com.fyp.sporteaze.Academy.Coach.ViewCoachAdapter;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.R;

import java.util.List;

public class AdminViewCoachesAdapter extends RecyclerView.Adapter {

    List<Coach> coachList;


    public AdminViewCoachesAdapter(List<Coach> coachList) {
        this.coachList = coachList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_view_coach, parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        Coach coaches= coachList.get(position);
        viewHolderClass.recycler_coach_name.setText(coaches.getCoach_name());
        viewHolderClass.recycler_coach_serial.setText("S.No");

        viewHolderClass.btn_coach_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.admin_view_coach_popup,null);
                TextView txt_coach_S_no = dialogView.findViewById(R.id.txt_coach_S_no);
                TextView txt_coach_name = dialogView.findViewById(R.id.txt_coach_name);
                TextView txt_coach_address = dialogView.findViewById(R.id.txt_coach_address);
                TextView txt_coach_age = dialogView.findViewById(R.id.txt_coach_age);
                TextView txt_coach_expertise = dialogView.findViewById(R.id.txt_coach_expertise);
                TextView txt_coach_charges = dialogView.findViewById(R.id.txt_coach_charges);
                TextView txt_coach_phone = dialogView.findViewById(R.id.txt_coach_phone);
                ImageView admin_coach_image = dialogView.findViewById(R.id.admin_coach_image);


//                if(coaches.getCoach_image()==null){
//                    Toast.makeText(dialogView.getContext(), "Null hai" , Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(dialogView.getContext(), "Nae hai", Toast.LENGTH_SHORT).show();
//                }

                if(coaches.getCoach_image()!=null && !(coaches.getCoach_image().matches(""))){
//                    Toast.makeText(dialogView.getContext(), "Ye hai" + coaches.getCoach_image().toString(), Toast.LENGTH_SHORT).show();
                    Glide.with(dialogView.getContext()).load(coaches.getCoach_image()).into(admin_coach_image);
                }


                txt_coach_S_no.setText("Coach ID: " + coaches.getCoach_id());
                txt_coach_name.setText("Name: "+coaches.getCoach_name());
                txt_coach_address.setText("Address: "+coaches.getCoach_address());
                txt_coach_age.setText("Age: "+coaches.getCoach_age());
                txt_coach_expertise.setText("Expertise: "+coaches.getCoach_expertise());
                txt_coach_charges.setText("Charges: "+coaches.getCoach_charges());
                txt_coach_phone.setText("Phone: "+coaches.getCoach_phone());




//                Glide.with(view.getContext()).load(users.academy_image).into(img_academy_view_details_image);

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