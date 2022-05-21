package com.fyp.sporteaze.Academy.RentRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.sporteaze.Academy.AcademyDashboard;
import com.fyp.sporteaze.Academy.Coach.AcademyCoachHome;
import com.fyp.sporteaze.Academy.Ground.AcademyGroundHome;
import com.fyp.sporteaze.BackPressDialog;
import com.fyp.sporteaze.LoginActivity;
import com.fyp.sporteaze.R;
import com.google.android.material.navigation.NavigationView;

public class AcademyHome extends AppCompatActivity {
    String academy_email , academy_id, academy_name;

    ConstraintLayout view_coach_request_box,view_ground_request_box;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_home);
        view_ground_request_box = findViewById(R.id.view_ground_request_box);
        view_coach_request_box = findViewById(R.id.view_coach_request_box);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");


        Toolbar toolbar = (Toolbar)findViewById(R.id.academy_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("Rent Request");
        }


        view_coach_request_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcademyHome.this , CoachRentRequest.class);
                intent.putExtra("academy_email", academy_email);
                intent.putExtra("academy_id",academy_id);
                intent.putExtra("academy_name", academy_name);
                startActivity(intent);
            }
        });

        view_ground_request_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcademyHome.this , GroundRentRequest.class);
                intent.putExtra("academy_email", academy_email);
                intent.putExtra("academy_id",academy_id);
                intent.putExtra("academy_name", academy_name);
                startActivity(intent);
            }
        });



        NavigationView nav_view = (NavigationView) findViewById(R.id.academy_nav_view);
        View headerView = nav_view.getHeaderView(0);
        TextView nav_username = (TextView)headerView.findViewById(R.id.academy_nav_head);
        nav_username.setText(academy_name);


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
                if(item.getTitle().equals("Coaches"))
                {
                  //  googleAuth.getInstance().signOut();
                    Intent intent = new Intent(AcademyHome.this , AcademyCoachHome.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
//                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if (item.getTitle().equals("Grounds"))
                {
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AcademyHome.this , AcademyGroundHome.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
//                    finish();
//                    return true;
                }

                else if (item.getTitle().equals("User registration request"))
                {

                    Intent intent = new Intent(AcademyHome.this, AcademyRentRequest.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(MainActivity.this , Door_Lock_Unlock.class);
//                    startActivity(intent);
//                    finish();
//                    return true;
                }

                else if (item.getTitle().equals("Team registration request"))
                {

                    Intent intent = new Intent(AcademyHome.this, AcademyTeamRentRequest.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(MainActivity.this , Door_Lock_Unlock.class);
//                    startActivity(intent);
//                    finish();
//                    return true;
                }
                else if(item.getTitle().equals("Logout")){

                    Intent intent = new Intent(AcademyHome.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


                return false;
            }


        });




    }
}