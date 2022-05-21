package com.fyp.sporteaze.Admin;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.Ground;
import com.fyp.sporteaze.R;

import java.util.List;

public class AdminGroundAdapter extends RecyclerView.Adapter {

    List<Ground> groundList;

    public AdminGroundAdapter(List<Ground> groundList) {
        this.groundList = groundList;
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


        Ground ground= groundList.get(position);
        viewHolderClass.recycler_coach_name.setText(ground.getGround_name());
        viewHolderClass.recycler_coach_serial.setText(ground.getGround_id());


        viewHolderClass.btn_coach_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.admin_view_ground_popup,null);
                ImageView admin_ground_image = dialogView.findViewById(R.id.admin_ground_image);
                TextView admin_ground_id = dialogView.findViewById(R.id.admin_ground_id);
                TextView admin_ground_name = dialogView.findViewById(R.id.admin_ground_name);

                TextView admin_ground_location = dialogView.findViewById(R.id.admin_ground_location);
                TextView admin_ground_size = dialogView.findViewById(R.id.admin_ground_size);
                TextView admin_ground_medical_availability = dialogView.findViewById(R.id.admin_ground_medical_availability);
                TextView admin_ground_analyst_availability = dialogView.findViewById(R.id.admin_ground_analyst_availability);
                TextView admin_ground_timings = dialogView.findViewById(R.id.admin_ground_timings);
                TextView admin_ground_charges = dialogView.findViewById(R.id.admin_ground_charges);

                if(ground.getGround_image()!=null &&!ground.getGround_image().matches("")){
                    Glide.with(dialogView.getContext()).load(ground.getGround_image()).into(admin_ground_image);
                }
                admin_ground_id.setText("Ground ID: " + ground.getGround_id());
                admin_ground_name.setText("Ground Name: " + ground.getGround_name());
                admin_ground_location.setText("Location: "+ground.getGround_address());
                admin_ground_size.setText("Ground Size: "+ground.getGround_size());
                admin_ground_medical_availability.setText("Medical Analyst: "+ground.getGround_medical_trainer_availability());
                admin_ground_analyst_availability.setText("Ground Analyst: "+ground.getGround_analyst_availability());
                admin_ground_charges.setText("Charges: "+ground.getGround_charges());
                admin_ground_timings.setText("Timings: "+ground.getGround_timings());




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
