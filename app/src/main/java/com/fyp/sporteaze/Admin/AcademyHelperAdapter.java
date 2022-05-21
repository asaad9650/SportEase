package com.fyp.sporteaze.Admin;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AcademyHelperAdapter extends RecyclerView.Adapter {

    List<DataHolder> academyList;

    public AcademyHelperAdapter(List<DataHolder> academyList){

        this.academyList = academyList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.academies_layout_file, parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        DataHolder users= academyList.get(position);
        viewHolderClass.academies_name.setText("Name: "+users.academy_name);
        viewHolderClass.recycler_academy_serial.setText("Academy ID: " + users.academy_id);



        viewHolderClass.btn_academy_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"The Item Clicked is: "+ users.getAcademy_name().toString(),Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent( view.getContext(), AdminViewAcademyDetails.class);
//                    intent.putExtra("academy_image", users.academy_image);
//                intent.putExtra("academy_name", users.academy_name);
//                intent.putExtra("academy_email", users.academy_email);
//                intent.putExtra("academy_address", users.academy_address);
//                intent.putExtra("academy_phone", users.academy_phone);
//                view.getContext().startActivity(intent);
//                    view.getContext().startActivity(intent);

                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.academy_view_details_popup,null);
                TextView txt_academy_view_details_name = dialogView.findViewById(R.id.txt_academy_view_details_name);
                TextView txt_academy_view_details_email = dialogView.findViewById(R.id.txt_academy_view_details_email);
                TextView txt_academy_view_details_phone = dialogView.findViewById(R.id.txt_academy_view_details_phone);
                TextView txt_academy_view_details_address = dialogView.findViewById(R.id.txt_academy_view_details_address);
                ImageView img_academy_view_details_image = dialogView.findViewById(R.id.img_academy_view_details_image);
                AppCompatButton btn_popup_academy_update = dialogView.findViewById(R.id.btn_popup_academy_update);
                AppCompatButton btn_popup_academy_delete = dialogView.findViewById(R.id.btn_popup_academy_delete);

                txt_academy_view_details_name.setText("Academy Name: "+users.academy_name);
                txt_academy_view_details_email.setText("Email: "+users.academy_email);
                txt_academy_view_details_phone.setText("Phone: "+users.academy_address);
                txt_academy_view_details_address.setText("Address: "+users.academy_phone);
                Glide.with(view.getContext()).load(users.academy_image).into(img_academy_view_details_image);

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

                btn_popup_academy_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog diaBox = AskOption(view.getRootView().getContext() , users.academy_id );
                        diaBox.show();

                    }
                });



                btn_popup_academy_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent( view.getContext(), AdminAcademyUpdate.class);
                        intent.putExtra("academy_image", users.academy_image);
                        intent.putExtra("academy_name", users.academy_name);
                        intent.putExtra("academy_email", users.academy_email);
                        intent.putExtra("academy_address", users.academy_address);
                        intent.putExtra("academy_phone", users.academy_phone);
                        intent.putExtra("academy_password", users.academy_password);
                        intent.putExtra("academy_id", users.academy_id);
                        view.getContext().startActivity(intent);
                        ((Activity)view.getContext()).finish();

                    }
                });

            }
        });
    }
    private AlertDialog AskOption(Context context ,String id)
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
                        academyList.remove(id);

//
                        root.child("academies").child(id).removeValue();
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
        return academyList.size();
    }
    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView academies_name;
        TextView recycler_academy_serial;
        View view;
        AppCompatButton btn_academy_view_details;
        AppCompatButton btn_academy_delete;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            academies_name = itemView.findViewById(R.id.recylcer_academy_name);
            recycler_academy_serial = itemView.findViewById(R.id.recycler_academy_serial);
//            users_email = itemView.findViewById(R.id.recylcer_user_email);
            btn_academy_view_details=itemView.findViewById(R.id.btn_academy_view_details);
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
