package com.fyp.sporteaze.Event;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyp.sporteaze.BackPressDialog;
import com.fyp.sporteaze.LoginActivity;
import com.fyp.sporteaze.Model.CreateMatchBetweenTeams;
import com.fyp.sporteaze.Model.Invitation;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.fyp.sporteaze.Search.UserSearch;
import com.fyp.sporteaze.User.DashboardActivity;
import com.fyp.sporteaze.User.IndividualCoachBooking;
import com.fyp.sporteaze.User.UserChat;
import com.fyp.sporteaze.User.UserMyAcademy;
import com.fyp.sporteaze.User.UserMyCoach;
import com.fyp.sporteaze.User.UserShowAcademies;
import com.fyp.sporteaze.User.UserUpdateProfile;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserShowPost extends AppCompatActivity {
    List<Invitation> invitationList;
    List<CreateMatchBetweenTeams> createMatchBetweenTeamsList;
    RecyclerView posts_recycler_view;
    UserShowPostAdapter userShowPostAdapter;

    DatabaseReference databaseReference;
    DatabaseReference createdMatchReference;
    DatabaseReference root ;

    TextView no_new_post;
    String user_name, user_email, user_id , captain , team_id ,user_phone, user_address , user_dob;
    BackPressDialog backPressDialog = new BackPressDialog();
//    String academy_email , academy_id, academy_name;
//    LinearLayout no_invoices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_show_post);

        no_new_post = findViewById(R.id.no_new_post);
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

        posts_recycler_view = findViewById(R.id.posts_recycler_view);
        posts_recycler_view.setVisibility(View.GONE);
        no_new_post.setVisibility(View.VISIBLE);
        posts_recycler_view.setLayoutManager(new LinearLayoutManager(this));

        invitationList = new ArrayList<>();
        createMatchBetweenTeamsList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("invitations");
        root = FirebaseDatabase.getInstance().getReference();

        createdMatchReference = FirebaseDatabase.getInstance().getReference("create_match_between_teams");
        if(team_id.matches("")){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()){

                        Invitation us = ds.getValue(Invitation.class);
                        invitationList.add(us);
                    }
                    userShowPostAdapter = new UserShowPostAdapter(invitationList, null  , user_id, user_name , user_email, user_phone , user_dob,user_address);
                    posts_recycler_view.setAdapter(userShowPostAdapter);
                    posts_recycler_view.setVisibility(View.VISIBLE);
                    no_new_post.setVisibility(View.GONE);
//                if (invoiceList.size()==0){
//                    no_invoices.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//                else{
//                    no_invoices.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                academyHelperAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        else if (!team_id.matches("")){
            createdMatchReference.orderByChild("status").equalTo("accepted").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds: snapshot.getChildren()){
//                        if(ds.child("request_initiated_by").getValue(String.class).matches(team_id)) {
//                        Toast.makeText(UserShowPost.this, "Team ID " + team_id, Toast.LENGTH_SHORT).show();
                            CreateMatchBetweenTeams createMatchBetweenTeams = ds.getValue(CreateMatchBetweenTeams.class);
                            if(createMatchBetweenTeams.getTeam_1().getTeam_id().matches(team_id) || createMatchBetweenTeams.getTeam_2().getTeam_id().matches(team_id)) {
                                createMatchBetweenTeamsList.add(createMatchBetweenTeams);
                            }
//                        }
                        userShowPostAdapter = new UserShowPostAdapter(null, createMatchBetweenTeamsList  , user_id, user_name , user_email, user_phone , user_dob,user_address);
                        posts_recycler_view.setAdapter(userShowPostAdapter);
                        posts_recycler_view.setVisibility(View.VISIBLE);
                        no_new_post.setVisibility(View.GONE);
                    }

//                if (invoiceList.size()==0){
//                    no_invoices.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//                else{
//                    no_invoices.setVisibility(View.GONE);
//                    recyclerView.setVisibility(View.VISIBLE);
//                }
//                academyHelperAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("User");
        }

        NavigationView nav_view = (NavigationView) findViewById(R.id.academy_nav_view);
        View headerView = nav_view.getHeaderView(0);
        TextView nav_username = (TextView)headerView.findViewById(R.id.user_nav_head);
        if(!user_name.equals("") && user_name!= null){
            nav_username.setText(user_name);
        }
        else{
            nav_username.setText(user_email);

        }


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer , toolbar,
                R.string.Open_nav_drawer, R.string.Close_nav_drawer)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getTitle().equals("Home")){
                    Intent intent = new Intent(UserShowPost.this, DashboardActivity.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                    startActivity(intent);
                    drawer.closeDrawers();
                }
                if(item.getTitle().equals("Search")){
//                    Toast.makeText(UserShowPost.this, "Search Clicked", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UserShowPost.this , UserSearch.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

                    startActivity(intent);
                    drawer.closeDrawers();
                }
                if(item.getTitle().equals("Individual Coach"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(UserShowPost.this , IndividualCoachBooking.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("My Coach"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(UserShowPost.this , UserMyCoach.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                    startActivity(intent);
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("Chat"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(UserShowPost.this , UserChat.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                    startActivity(intent);
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("My Academy"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(UserShowPost.this , UserMyAcademy.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                    startActivity(intent);
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("Academy Registration"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(UserShowPost.this , UserShowAcademies.class);
                    intent.putExtra("user_email" , user_email);
                    intent.putExtra("user_name", user_name);
                    intent.putExtra("captain" , captain);
                    intent.putExtra("user_id", user_id);
                    intent.putExtra("user_dob", user_dob);
                    intent.putExtra("user_phone" , user_phone);
                    intent.putExtra("user_address" , user_address);
                    intent.putExtra("team_id" , team_id);

//                                    intent.putExtra("user_id",userMod.user_id);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if (item.getTitle().equals("Update profile"))
                {
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();

                    root.child("Users").orderByChild("email").equalTo(user_email).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot sn: snapshot.getChildren())
                            {
                                User user = sn.getValue(User.class);

//                                Toast.makeText(DashboardActivity.this, user.password, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(UserShowPost.this , UserUpdateProfile.class);
                                intent.putExtra("user_id" ,user.user_id);
                                intent.putExtra("user_name",user.name);
                                intent.putExtra("user_email",user.email);
                                intent.putExtra("user_password",user.password);
                                intent.putExtra("user_phone",user.phone);
                                intent.putExtra("user_cnic",user.cnic );
                                intent.putExtra("user_dob",user.dob);
                                intent.putExtra("user_address",user.address );
                                intent.putExtra("user_gender",user.gender);
                                intent.putExtra("user_image",user.image);
                                startActivity(intent);

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    Intent intent = new Intent(DashboardActivity.this , UserUpdateProfile.class);
//                    intent.putExtra("user_name" ,user_name);
//                    intent.putExtra("user_email",user_email);
//                    startActivity(intent);
//                    Intent intent = new Intent(MainActivity.this , ShowDetails.class);
//                    startActivity(intent);
//                    finish();
//                    return true;
                }
                else if(item.getTitle().equals("Logout")){

                    Intent intent = new Intent(UserShowPost.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }



                return false;
            }


        });

    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            backPressDialog.logout(this);

            //moveTaskToBack(false);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}