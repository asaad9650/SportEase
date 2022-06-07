package com.fyp.sporteaze.Teams;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.sporteaze.R;

public class TeamRentCoach extends AppCompatActivity {
    EditText et_coach_rent_date, et_coach_rent_time , et_coach_rent_team, et_coach_rent_purpose , et_coach_rent_ground;
    Button btn_rent_coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_rent_coach);
        et_coach_rent_date = findViewById(R.id.et_rent_coach_date);
        et_coach_rent_time = findViewById(R.id.et_rent_coach_time);
        et_coach_rent_team = findViewById(R.id.et_rent_coach_team);
        et_coach_rent_purpose  = findViewById(R.id.et_rent_coach_purpose);
        et_coach_rent_ground = findViewById(R.id.et_rent_coach_ground);
        btn_rent_coach = findViewById(R.id.rent_coach_btn);

    }
}