package com.fyp.sporteaze.Event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.Model.Invitation;
import com.fyp.sporteaze.Model.JoinTeam;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserShowPostAdapter  extends RecyclerView.Adapter {

    List<Invitation> invoiceList;
    String user_id , user_name, user_email, user_phone ,user_dob,user_address , team_name;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference reference = firebaseDatabase.getReference();
    DatabaseReference check_reference = firebaseDatabase.getReference();



    public UserShowPostAdapter(List<Invitation> invoiceList, String user_id, String user_name, String user_email, String user_phone, String user_dob, String user_address) {
        this.invoiceList = invoiceList;
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_dob = user_dob;
        this.user_address = user_address;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout_file, parent, false);
        ViewHolderClass viewHolderClass = new ViewHolderClass(view);
        return viewHolderClass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolderClass viewHolderClass = (ViewHolderClass) holder;
        Invitation invitation = invoiceList.get(position);
        viewHolderClass.post_captain_email.setText("Captain: " + invitation.getCaptain_email());
        viewHolderClass.post_team_name.setText("Team Name: " +  invitation.getTeam_name());
        viewHolderClass.post_looking_for.setText("Looking for: " + invitation.getLooking_for());
        viewHolderClass.post_message.setText("Message: "+invitation.getMessage());
        viewHolderClass.post_preferred_age.setText("Preferred Age: " + invitation.getPreferred_age());
        viewHolderClass.post_remaining_space.setText("Remaining Space: " + invitation.getRemaining_space());
        team_name = invitation.getTeam_name();


        viewHolderClass.post_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!team_name.matches("")){
                    check_reference.child("team_join_request").orderByChild("user_id").equalTo(user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(view.getContext(), "Already requested!!", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String pushKey = reference.push().getKey();
                                JoinTeam joinTeam = new JoinTeam(pushKey , user_id , team_name, user_name , user_email , user_phone , user_dob, user_address);
                                reference.child("team_join_request").child(pushKey).setValue(joinTeam);
                                Toast.makeText(view.getContext(), "Request Sent Successfully.!!", Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });
//        viewHolderClass.post_submit_btn.
//        viewHolderClass.recycler_invoice_total.setText(invoice.getTotal_charges());
    }

    @Override
    public int getItemCount() {
        return invoiceList.size();
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView post_captain_email;
        TextView post_team_name;
        TextView post_preferred_age;
        TextView post_message;
        TextView post_remaining_space;
        TextView post_looking_for;
        Button post_submit_btn;
        View view;


        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);


            post_captain_email = itemView.findViewById(R.id.post_captain_email);
            post_team_name = itemView.findViewById(R.id.post_team_name);
            post_preferred_age = itemView.findViewById(R.id.post_preferred_age);
            post_message = itemView.findViewById(R.id.post_message);
            post_remaining_space = itemView.findViewById(R.id.post_remaining_space);
            post_looking_for = itemView.findViewById(R.id.post_looking_for);
            post_submit_btn = itemView.findViewById(R.id.post_submit_btn);


        }
    }


}
