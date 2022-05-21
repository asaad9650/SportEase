package com.fyp.sporteaze.Academy.RentRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.fyp.sporteaze.Model.Academy_registration_individual;
import com.fyp.sporteaze.Model.CoachBooking;
import com.fyp.sporteaze.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AcademyRentRequest extends AppCompatActivity {
    List<Academy_registration_individual> academy_registrationList;
    RecyclerView academy_rent_request_recycler_view;
    AcademyRentAdapter academyRentAdapter;
    DatabaseReference databaseReference;
    TextView text_no_academy_request ;
    String academy_email , academy_id, academy_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_rent_request);


        academy_rent_request_recycler_view = findViewById(R.id.academy_rent_request_recycler_view);
        text_no_academy_request = findViewById(R.id.text_no_academy_request);
        academy_rent_request_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        academy_registrationList = new ArrayList<>();
        academy_rent_request_recycler_view.setVisibility(View.GONE);
        text_no_academy_request.setVisibility(View.VISIBLE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");
        databaseReference = FirebaseDatabase.getInstance().getReference("academy_individual_bookings");
        databaseReference.orderByChild("academy_id").equalTo(academy_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot2) {

                for (DataSnapshot dataSnapshot : snapshot2.getChildren()) {
                    Academy_registration_individual us = dataSnapshot.getValue(Academy_registration_individual.class);

                    if(us.getRequest_status().matches("pending")) {
                        academy_registrationList.add(us);
                        academy_rent_request_recycler_view.setVisibility(View.VISIBLE);
                        text_no_academy_request.setVisibility(View.GONE);
                        academyRentAdapter = new AcademyRentAdapter(academy_registrationList, academy_id);
                        academy_rent_request_recycler_view.setAdapter(academyRentAdapter);
                        Toast.makeText(AcademyRentRequest.this, us.getAcademy_name(), Toast.LENGTH_SHORT).show();
                        if (academy_registrationList.size() == 0) {
                            text_no_academy_request.setVisibility(View.VISIBLE);
                            academy_rent_request_recycler_view.setVisibility(View.GONE);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        Toolbar toolbar = (Toolbar)findViewById(R.id.academy_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setTitle("Rent Request");
        }
//        databaseReference.orderByChild("expertise").equalTo("Bowling").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot ds: snapshot.getChildren()){
//                    Academy_registration us = ds.getValue(Academy_registration.class);
//                    academy_registrationList.add(us);
//                }
//                recyclerView.setVisibility(View.VISIBLE);
//                text_no_academy_request.setVisibility(View.GONE);
//                academyRentAdapter = new AcademyRentAdapter(academy_registrationList , "");
//                recyclerView.setAdapter(academyRentAdapter);
//                if(academy_registrationList.size()== 0){
//                    text_no_academy_request.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//
//                //academyHelperAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

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
                    Intent intent = new Intent(AcademyRentRequest.this , AcademyCoachHome.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
                    finish();
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
                    drawer.closeDrawers();
//                    return true;
                }
                else if (item.getTitle().equals("Grounds"))
                {
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AcademyRentRequest.this , AcademyGroundHome.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
//                    finish();
//                    return true;
                }
                else if (item.getTitle().equals("Rent request"))
                {
//                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(AcademyRentRequest.this, AcademyHome.class);
                    intent.putExtra("academy_email", academy_email);
                    intent.putExtra("academy_id",academy_id);
                    intent.putExtra("academy_name", academy_name);
                    startActivity(intent);
//                    Intent intent = new Intent(MainActivity.this , Door_Lock_Unlock.class);
//                    startActivity(intent);
                    finish();
//                    return true;
                }

                else if (item.getTitle().equals("Team registration request"))
                {

                    Intent intent = new Intent(AcademyRentRequest.this, AcademyTeamRentRequest.class);
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
//                else if (item.getTitle().equals("Registration request"))
//                {
//
////                    Intent intent = new Intent(AcademyHome.this, AcademyRentRequest.class);
////                    intent.putExtra("academy_email", academy_email);
////                    intent.putExtra("academy_id",academy_id);
////                    startActivity(intent);
////                    Toast.makeText(getApplicationContext() , item.getTitle().toString() , Toast.LENGTH_SHORT).show();
//
////                    Intent intent = new Intent(MainActivity.this , Door_Lock_Unlock.class);
////                    startActivity(intent);
//                    //finish();
////                    return true;
//                }
                else if(item.getTitle().equals("Logout")){

                    Intent intent = new Intent(AcademyRentRequest.this , LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


                return false;
            }


        });

    }
}