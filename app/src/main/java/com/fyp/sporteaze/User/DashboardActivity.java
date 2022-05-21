package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Academy.Coach.AcademyCoachHome;
import com.fyp.sporteaze.Academy.Ground.AcademyGroundHome;
import com.fyp.sporteaze.BackPressDialog;
import com.fyp.sporteaze.LoginActivity;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity  {

String user_name, user_email, user_id , captain;
ConstraintLayout add_team_box , view_invitations_box, view_box , view_bookings_box;
FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
DatabaseReference reference = firebaseDatabase.getReference();
BackPressDialog backPressDialog = new BackPressDialog();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");
        captain = extras.getString("captain");

//        Toast.makeText(DashboardActivity.this, user_email+captain, Toast.LENGTH_LONG).show();
//        user_id = extras.getString("user_id");
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference root = db.getReference();

        Toolbar toolbar = (Toolbar)findViewById(R.id.user_toolbar);
        setSupportActionBar(toolbar);
        add_team_box = findViewById(R.id.add_team_box);
        view_invitations_box = findViewById(R.id.view_invitations_box);

        view_box = findViewById(R.id.view_teams_box);
        view_bookings_box = findViewById(R.id.view_bookings_box);
//        view_bookings_box.setVisibility(View.GONE);
        DatabaseReference creator_ref = FirebaseDatabase.getInstance().getReference();

        view_bookings_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                Intent intent= new Intent(DashboardActivity.this, UsersBookings.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("user_email" , user_email);
                startActivity(intent);


            }
        });

        if("yes".equalsIgnoreCase(captain)){
            view_bookings_box.setVisibility(View.VISIBLE);
        }
        else{
            view_bookings_box.setVisibility(View.INVISIBLE);
        }


//        creator_ref.child("Users").orderByChild("email").equalTo(user_email).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                isCreator = true;
//                 }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        Toast.makeText(DashboardActivity.this, "is creater "+ isCreator.toString(), Toast.LENGTH_SHORT).show();

        add_team_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent intent = new Intent(DashboardActivity.this, UserAddTeam.class);
            intent.putExtra("user_email",user_email);
            intent.putExtra("user_id",user_id);
            startActivity(intent);
//            finish();

            }
        });

        view_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DashboardActivity.this, UserMyTeam.class);
                intent.putExtra("user_email",user_email);
                intent.putExtra("user_id",user_id);
                intent.putExtra("user_name" , user_name);
                intent.putExtra("captain", captain);
                startActivity(intent);
//                finish();
            }
        });

        view_invitations_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("Users").orderByChild("email").equalTo(user_email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot sn: snapshot.getChildren())
                        {
                            User user = sn.getValue(User.class);

//                                Toast.makeText(DashboardActivity.this, user.password, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(DashboardActivity.this, UserViewInvitations.class);

                            intent.putExtra("user_address",user.address );
                            intent.putExtra("user_gender",user.gender);
                            intent.putExtra("user_image",user.image);
                            intent.putExtra("user_id" , user.user_id);
                            intent.putExtra("user_email", user.email);
                            intent.putExtra("user_name",user.name);
                            intent.putExtra("captain", captain);
                            startActivity(intent);
//                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


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
                if(item.getTitle().equals("Individual Coach"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(DashboardActivity.this , IndividualCoachBooking.class);
                    intent.putExtra("user_id" ,user_id);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_email",user_email);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("My Coach"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(DashboardActivity.this , UserMyCoach.class);
                    intent.putExtra("user_id" ,user_id);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_email",user_email);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("Chat"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(DashboardActivity.this , UserChat.class);
                    intent.putExtra("user_id" ,user_id);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_email",user_email);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("My Academy"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(DashboardActivity.this , UserMyAcademy.class);
                    intent.putExtra("user_id" ,user_id);
                    intent.putExtra("user_name",user_name);
                    intent.putExtra("user_email",user_email);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if(item.getTitle().equals("Academy Registration"))
                {
                    //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(DashboardActivity.this , UserShowAcademies.class);
                    intent.putExtra("user_id" ,user_id);
//                    intent.putExtra("user_name",user.name);
                    intent.putExtra("user_email",user_email);
                    intent.putExtra("individual_academy", "individual_academy");
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
                                Intent intent = new Intent(DashboardActivity.this , UserUpdateProfile.class);
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

                    Intent intent = new Intent(DashboardActivity.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }



                return false;
            }


        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

           backPressDialog.logout(this);

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}