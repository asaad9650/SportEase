package com.fyp.sporteaze.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fyp.sporteaze.R;

public class TeamGroundBookingDetails extends AppCompatActivity {
    String user_id,user_name,user_email,coach_name,coach_charges,coach_booking_status,coach_expertise, coach_booking_age;
    LinearLayout layout_my_coach , layout_no_coach;
    TextView my_coach_name, my_coach_age, my_coach_charges, my_coach_phone, my_coach_address, my_coach_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_ground_booking_details);


        layout_my_coach = findViewById(R.id.layout_my_coach);
        my_coach_name = findViewById(R.id.my_coach_name);
        my_coach_age = findViewById(R.id.my_coach_age);
        my_coach_charges = findViewById(R.id.my_coach_charges);
        my_coach_phone = findViewById(R.id.my_coach_phone);
        my_coach_address = findViewById(R.id.my_coach_address);
        my_coach_status = findViewById(R.id.my_coach_status);
        layout_no_coach = findViewById(R.id.layout_no_coach);
        layout_my_coach = findViewById(R.id.layout_my_coach);
        layout_my_coach.setVisibility(View.VISIBLE);
        layout_no_coach.setVisibility(View.GONE);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        coach_name= extras.getString("ground_name");
        coach_charges = extras.getString("ground_charges");
        coach_booking_status = extras.getString("ground_booking_status");
        coach_expertise = extras.getString("ground_location");
        coach_booking_age = extras.getString("ground_size");


        my_coach_name.setText("Ground Name: " + coach_name);
        my_coach_age.setText("Ground Size: "+ coach_charges);
        my_coach_charges.setText("Ground Location: "+coach_expertise);
        my_coach_phone.setText("Ground Booking Status: "+coach_booking_status);
        my_coach_age.setText("Ground Location: " + coach_booking_age);
        my_coach_address.setVisibility(View.GONE);
        my_coach_status.setVisibility(View.GONE);
    }
}