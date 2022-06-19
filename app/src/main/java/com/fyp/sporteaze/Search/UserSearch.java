package com.fyp.sporteaze.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Team;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserSearch extends AppCompatActivity {
    List<User> userList ;
    List<Team> teamList;
    RecyclerView search_recycler_view;
    UserSearchAdapter userSearchAdapter;
    DatabaseReference ref;
    DatabaseReference ref2;

    EditText et_search_field;
    ImageView btn_search_filter;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob;
    String user_or_team_filter = "user";
    String gender = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        search_recycler_view = findViewById(R.id.search_recycler_view);
        et_search_field = findViewById(R.id.et_search_field);
        btn_search_filter = findViewById(R.id.btn_search_filter);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");
        user_phone = extras.getString("user_phone");
        user_address = extras.getString("user_address");
        user_dob = extras.getString("user_dob");

        search_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        teamList = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref2 = FirebaseDatabase.getInstance().getReference().child("teams");


        btn_search_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =   new AlertDialog.Builder(view.getRootView().getContext());
                AlertDialog alertDialog = builder.create();
                View dialogView = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.search_filter_popup,null);
                RadioButton check_box_search_player = dialogView.findViewById(R.id.check_box_search_player);
                RadioButton check_box_search_team = dialogView.findViewById(R.id.check_box_search_team);
                RadioButton check_box_search_male = dialogView.findViewById(R.id.check_box_search_male);
                RadioButton check_box_search_female = dialogView.findViewById(R.id.check_box_search_female);
                Button btn_search = dialogView.findViewById(R.id.btn_search);
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userList.clear();
                        userSearchAdapter.notifyDataSetChanged();
                        if(et_search_field.getText().toString().matches("")) {
                            if (check_box_search_male.isChecked()) {
                                gender = "Male";
                                ref.orderByChild("gender").equalTo(gender).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            User user = dataSnapshot.getValue(User.class);
                                            if (!user.getUser_id().matches(user_id)) {
                                                userList.add(user);
                                            }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            if (check_box_search_female.isChecked()) {
                                gender = "Female";
                                ref.orderByChild("gender").equalTo(gender).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            User user = dataSnapshot.getValue(User.class);
                                            if (!user.getUser_id().matches(user_id)) {
                                                userList.add(user);
                                            }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            if (check_box_search_team.isChecked()) {
                                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            Team team = dataSnapshot.getValue(Team.class);
//                                        if (!team.getTeam_id().matches(team_id)) {
                                            teamList.add(team);
//                                            userList.add(user);
//                                        }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("team.getTeamName()", "user.address", null, teamList , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            if (check_box_search_player.isChecked()) {
                                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            User user = dataSnapshot.getValue(User.class);
                                            if (!user.getUser_id().matches(user_id)) {
                                                userList.add(user);
                                            }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                        }
                        else{
                            String search_query = et_search_field.getText().toString().trim();
                            if(check_box_search_player.isChecked()){
                                if(search_query.contains("@")){
                                    ref.orderByChild("email").equalTo(search_query).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                User user = dataSnapshot.getValue(User.class);
                                                if (!user.getUser_id().matches(user_id)) {
                                                    userList.add(user);
                                                }
                                            }
                                            userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id);
                                            search_recycler_view.setAdapter(userSearchAdapter);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                else{
                                    ref.orderByChild("name").equalTo(search_query).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                                User user = dataSnapshot.getValue(User.class);
                                                if (!user.getUser_id().matches(user_id)) {
                                                    userList.add(user);
                                                }
                                            }
                                            userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id );
                                            search_recycler_view.setAdapter(userSearchAdapter);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }


                            }
                            if(check_box_search_team.isChecked()){
                                ref2.orderByChild("team_name").equalTo(search_query).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            Team team = dataSnapshot.getValue(Team.class);
//                                        if (!team.getTeam_id().matches(team_id)) {
                                            teamList.add(team);
//                                            userList.add(user);
//                                        }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("team.getTeamName()", "user.address", null, teamList , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            if (check_box_search_male.isChecked()) {
                                gender = "Male";
                                ref.orderByChild("gender").equalTo(gender).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            User user = dataSnapshot.getValue(User.class);
                                            if (!user.getUser_id().matches(user_id)) {
                                                userList.add(user);
                                            }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                            }
                            if (check_box_search_female.isChecked()) {
                                gender = "Female";
                                ref.orderByChild("gender").equalTo(gender).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            User user = dataSnapshot.getValue(User.class);
                                            if (!user.getUser_id().matches(user_id)) {
                                                userList.add(user);
                                            }
                                        }
                                        userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList, null , user_id);
                                        search_recycler_view.setAdapter(userSearchAdapter);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }

                        }

                    }
                });


                builder.setView(dialogView);
                builder.setCancelable(true);
                builder.show();


            }
        });




        if(gender.matches("")) {
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        User user = dataSnapshot.getValue(User.class);
                        if (!user.getUser_id().matches(user_id)) {
                            userList.add(user);
                        }
                    }
                    userSearchAdapter = new UserSearchAdapter("user.email", "user.address", userList , null , user_id);
                    search_recycler_view.setAdapter(userSearchAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }


}