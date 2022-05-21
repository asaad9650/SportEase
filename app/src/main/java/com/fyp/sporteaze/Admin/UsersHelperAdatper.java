package com.fyp.sporteaze.Admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UsersHelperAdatper extends RecyclerView.Adapter
{
    List<User> userList;

    public UsersHelperAdatper(List<User> userList){
        this.userList = userList;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_layout_file,parent,false);

        ViewHolderClass viewHolderClass = new ViewHolderClass(view);

        return viewHolderClass;




    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewHolderClass viewHolderClass = (ViewHolderClass)holder;


        User users= userList.get(position);
        viewHolderClass.user_id.setText(users.user_id);
        viewHolderClass.user_name.setText(users.name);
        viewHolderClass.btn_show_user_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.admin_view_user_popup,null);
                TextView recycler_user_id = dialogView.findViewById(R.id.recycler_user_id);
                TextView recycler_user_email = dialogView.findViewById(R.id.recycler_user_email);
                TextView recycler_user_name = dialogView.findViewById(R.id.recycler_user_name);
                TextView recycler_user_address = dialogView.findViewById(R.id.recycler_user_address);
                TextView recycler_user_cnic = dialogView.findViewById(R.id.recycler_user_cnic);
                TextView recycler_user_dob = dialogView.findViewById(R.id.recycler_user_dob);
                TextView recycler_user_gender = dialogView.findViewById(R.id.recycler_user_gender);
                TextView recycler_user_phone = dialogView.findViewById(R.id.recycler_user_phone);
                AppCompatButton btn_recycler_user_delete = dialogView.findViewById(R.id.btn_recycler_user_delete);
//                AppCompatButton btn_popup_academy_delete = dialogView.findViewById(R.id.btn_popup_academy_delete);

                recycler_user_id.setText("User ID: "+users.user_id);
                recycler_user_email.setText("Email: "+users.email);
                recycler_user_name.setText("Name: "+users.name);
                recycler_user_address.setText("Address: "+users.address);
//                recycler_user_address.setText(users);
                recycler_user_cnic.setText("Cnic: "+users.cnic);
                recycler_user_dob.setText("Date of birth: "+users.dob);
                recycler_user_gender.setText("Gender: " +users.gender);
                recycler_user_phone.setText("Phone: "+users.phone);
                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

                btn_recycler_user_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        AlertDialog diaBox = AskOption(view.getRootView().getContext() , users.user_id );
                        diaBox.show();

                    }
                });



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
                        userList.remove(id);

//
                        root.child("Users").child(id).removeValue();
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
        return userList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView user_name, user_id;
        AppCompatButton btn_show_user_details;

        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            user_id = itemView.findViewById(R.id.layout_user_id);
            user_name = itemView.findViewById(R.id.layout_user_name);
            btn_show_user_details = itemView.findViewById(R.id.btn_show_user_details);
        }
    }
}
