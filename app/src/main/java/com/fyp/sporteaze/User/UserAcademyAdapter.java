package com.fyp.sporteaze.User;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Admin.AcademyHelperAdapter;
import com.fyp.sporteaze.Admin.AdminAcademyUpdate;
import com.fyp.sporteaze.Admin.DataHolder;
import com.fyp.sporteaze.Model.Academy_registration_individual;
import com.fyp.sporteaze.Model.Academy_registration_team;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAcademyAdapter extends RecyclerView.Adapter {

    List<DataHolder> academyList;
    String user_id, team_id , individual_academy;


    public UserAcademyAdapter(List<DataHolder> academyList, String user_id, String team_id, String individual_academy) {
        this.academyList = academyList;
        this.user_id = user_id;
        this.team_id = team_id;
        this.individual_academy = individual_academy;
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
        viewHolderClass.academies_name.setText("Name: "+users.getAcademy_name());
        viewHolderClass.recycler_academy_serial.setText("Academy ID: " + users.getAcademy_id());


        viewHolderClass.btn_academy_view_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.user_view_academy_popup,null);

                TextView txt_user_academy_id = dialogView.findViewById(R.id.txt_user_academy_id);
                TextView txt_user_academy_name = dialogView.findViewById(R.id.txt_user_academy_name);
                TextView txt_user_academy_address = dialogView.findViewById(R.id.txt_user_academy_address);
                TextView txt_user_academy_phone = dialogView.findViewById(R.id.txt_user_academy_phone);

                AppCompatButton btn_book_coach = dialogView.findViewById(R.id.btn_book_academy);


                txt_user_academy_id.setText(users.getAcademy_id());
                txt_user_academy_name.setText(users.getAcademy_name());
                txt_user_academy_address.setText(users.getAcademy_address());
                txt_user_academy_phone.setText(users.getAcademy_phone());


                btn_book_coach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference();

                        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference team_name_ref = FirebaseDatabase.getInstance().getReference();

                        if(!individual_academy.matches("") && individual_academy !=null){
                            String key = db.push().getKey();

                            user_ref.child("Users").child(user_id).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    User userMod = snapshot.getValue(User.class);
                                    Academy_registration_individual academy_registration_individual = new Academy_registration_individual(
                                            key,
                                            user_id,
                                            userMod.name,
                                            userMod.phone,
                                            userMod.email,
                                            userMod.address,
                                            users.getAcademy_id(),
                                            users.getAcademy_name(),
                                            "pending");

                                    db.child("academy_individual_bookings").child(user_id).setValue(academy_registration_individual);

                                    Toast.makeText(view.getContext(), "Requested successfully", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        }
                        else{
                            String key = db.push().getKey();
                            team_name_ref.child("teams").child(team_id).child("team_name").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String team_name = snapshot.getValue(String.class);

                                    user_ref.child("Users").child(user_id).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            User userMod = snapshot.getValue(User.class);
                                            Academy_registration_team academy_registration_team = new Academy_registration_team(
                                                    key,
                                                    team_name,
                                                    team_id,
                                                    userMod.name,
                                                    users.getAcademy_id(),
                                                    users.getAcademy_name(),
                                                    "pending");

                                            db.child("academy_team_bookings").child(user_id).setValue(academy_registration_team);

                                            Toast.makeText(view.getContext(), "Requested successfully", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });

                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();

            }
        });

//        viewHolderClass.btn_academy_view_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent( view.getContext(), UserAcademyRegistration.class);
//                intent.putExtra("user_id", user_id);
//                intent.putExtra("academy_id" , users.getAcademy_id());
//                intent.putExtra("academy_name" , users.getAcademy_name());
//                view.getContext().startActivity(intent);
//                ((Activity)view.getContext()).finish();
//            }
//        });
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