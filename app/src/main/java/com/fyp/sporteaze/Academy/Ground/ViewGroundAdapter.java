package com.fyp.sporteaze.Academy.Ground;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Academy.Coach.AcademyCoachUpdate;
import com.fyp.sporteaze.Academy.Coach.ViewCoachAdapter;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.Ground;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ViewGroundAdapter extends RecyclerView.Adapter {

    List<Ground> groundList;
    String  academy_id , academy_email;


    public ViewGroundAdapter(List<Ground> groundList, String academy_id, String academy_email) {
        this.groundList = groundList;
        this.academy_id = academy_id;
        this.academy_email = academy_email;
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
        viewHolderClass.recycler_ground_name.setText("Name: "+grounds.getGround_name());
        viewHolderClass.recycler_ground_serial.setText("Ground ID: "+grounds.getGround_id());

        viewHolderClass.btn_ground_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.view_ground_popup,null);
                TextView txt_ground_id = dialogView.findViewById(R.id.txt_ground_id);
                TextView txt_ground_name = dialogView.findViewById(R.id.txt_ground_name);
                TextView txt_ground_location = dialogView.findViewById(R.id.txt_ground_location);
                TextView txt_ground_timings = dialogView.findViewById(R.id.txt_ground_timings);
                TextView txt_ground_size = dialogView.findViewById(R.id.txt_ground_size);
                TextView txt_medical_trainer_availability = dialogView.findViewById(R.id.txt_medical_trainer_availability);
                TextView txt_analyst_availability = dialogView.findViewById(R.id.txt_analyst_availability);
//                TextView txt_coach_phone = dialogView.findViewById(R.id.txt_coach_phone);


                AppCompatButton btn_popup_ground_update = dialogView.findViewById(R.id.btn_popup_ground_update);
                AppCompatButton btn_popup_ground_delete = dialogView.findViewById(R.id.btn_popup_ground_delete);

                txt_ground_id.setText("Ground ID: " + grounds.getGround_id());
                txt_ground_name.setText("Name: "+grounds.getGround_name());
                txt_ground_location.setText("Location: "+grounds.getGround_address());
                txt_ground_size.setText("Size: "+grounds.getGround_size());
                txt_ground_timings.setText("Timings: " + grounds.getGround_timings());
                txt_medical_trainer_availability.setText("Medical Trainer: "+grounds.getGround_medical_trainer_availability());
                txt_analyst_availability.setText("Analyst: "+grounds.getGround_analyst_availability());
//                txt_coach_phone.setText("Phone: "+coaches.getCoach_phone());


                btn_popup_ground_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( view.getContext(), AcademyGroundUpdate.class);
                        intent.putExtra("academy_email", academy_email);
                        intent.putExtra("academy_id",academy_id);
                        intent.putExtra("ground_id", grounds.getGround_id());
                        intent.putExtra("ground_name", grounds.getGround_name());
                        intent.putExtra("ground_location", grounds.getGround_address());
                        intent.putExtra("ground_size", grounds.getGround_size());
                        intent.putExtra("ground_timings", grounds.getGround_timings());
                        intent.putExtra("ground_medical_trainer_availability",grounds.getGround_medical_trainer_availability());
                        intent.putExtra("ground_analyst_availability", grounds.getGround_analyst_availability());
                        intent.putExtra("ground_image", grounds.getGround_image());
                        intent.putExtra("ground_charges", grounds.getGround_charges());
                        view.getContext().startActivity(intent);
                        ((Activity)view.getContext()).finish();

                    }
                });

                btn_popup_ground_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog diaBox = AskOption(view.getRootView().getContext() , grounds.getGround_id() );
                        diaBox.show();
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

    private AlertDialog AskOption(Context context , String id)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(context)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage("Are you sure you want to delete?")
                .setIcon(R.drawable.ic_baseline_delete_24)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //your deleting code
                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference();
                        groundList.remove(id);

//
                        root.child("grounds").child(id).removeValue();
                        //notifyDataSetChanged();
                        // Activity.recreate();
                        dialog.dismiss();
                        ((Activity)context).finish();
//                        academyList.remove(id);
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
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
