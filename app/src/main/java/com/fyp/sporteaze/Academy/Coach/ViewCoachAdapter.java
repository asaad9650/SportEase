package com.fyp.sporteaze.Academy.Coach;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.sporteaze.Admin.AcademyHelperAdapter;
import com.fyp.sporteaze.Admin.AdminAcademyUpdate;
import com.fyp.sporteaze.Admin.DataHolder;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ViewCoachAdapter extends RecyclerView.Adapter{

    List<Coach> coachList;
    String  academy_id,academy_email ;

    public ViewCoachAdapter(List<Coach> coachList, String academy_id, String academy_email) {
        this.coachList = coachList;
        this.academy_id = academy_id;
        this.academy_email = academy_email;
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
        viewHolderClass.recycler_coach_name.setText(coaches.getCoach_name());
        viewHolderClass.recycler_coach_serial.setText("S.No");


        viewHolderClass.btn_coach_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.view_coach_popup,null);
                TextView txt_coach_S_no = dialogView.findViewById(R.id.txt_coach_S_no);
                TextView txt_coach_name = dialogView.findViewById(R.id.txt_coach_name);
                TextView txt_coach_address = dialogView.findViewById(R.id.txt_coach_address);
                TextView txt_coach_age = dialogView.findViewById(R.id.txt_coach_age);
                TextView txt_coach_expertise = dialogView.findViewById(R.id.txt_coach_expertise);
                TextView txt_coach_charges = dialogView.findViewById(R.id.txt_coach_charges);
                TextView txt_coach_phone = dialogView.findViewById(R.id.txt_coach_phone);


                AppCompatButton btn_popup_coach_update = dialogView.findViewById(R.id.btn_popup_coach_update);
                AppCompatButton btn_popup_coach_delete = dialogView.findViewById(R.id.btn_popup_coach_delete);

                txt_coach_S_no.setText("Coach ID: " + coaches.getCoach_id());
                txt_coach_name.setText("Name: "+coaches.getCoach_name());
                txt_coach_address.setText("Address: "+coaches.getCoach_address());
                txt_coach_age.setText("Age: "+coaches.getCoach_age());
                txt_coach_expertise.setText("Expertise: "+coaches.getCoach_expertise());
                txt_coach_charges.setText("Charges: "+coaches.getCoach_charges());
                txt_coach_phone.setText("Phone: "+coaches.getCoach_phone());


                btn_popup_coach_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( view.getContext(), AcademyCoachUpdate.class);
                        intent.putExtra("academy_id", academy_id);
                        intent.putExtra("academy_email", academy_email);
                        intent.putExtra("coach_id", coaches.getCoach_id());
                        intent.putExtra("coach_name", coaches.getCoach_name());
                        intent.putExtra("coach_address", coaches.getCoach_address());
                        intent.putExtra("coach_age", coaches.getCoach_age());
                        intent.putExtra("coach_expertise", coaches.getCoach_expertise());
                        intent.putExtra("coach_charges",coaches.getCoach_charges());
                        intent.putExtra("coach_phone", coaches.getCoach_phone());
                        intent.putExtra("coach_image", coaches.getCoach_image());
                        view.getContext().startActivity(intent);
                        ((Activity)view.getContext()).finish();

                    }
                });

                btn_popup_coach_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog diaBox = AskOption(view.getRootView().getContext() , coaches.getCoach_id() );
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
                        coachList.remove(id);

//
                        root.child("coaches").child(id).removeValue();
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
