package com.fyp.sporteaze.Teams;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.fyp.sporteaze.R;

public class TeamRentGround extends AppCompatActivity {
    EditText et_rent_ground_name ,et_rent_ground_date ,et_rent_ground_time ,et_rent_ground_team , et_rent_ground_purpose;
    Button rent_ground_btn;
    CheckBox with_medical, with_analyst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_rent_ground);

        et_rent_ground_name = findViewById(R.id.et_rent_ground_name);
        et_rent_ground_date = findViewById(R.id.et_rent_ground_date);
        et_rent_ground_time = findViewById(R.id.et_rent_ground_time);
        et_rent_ground_team = findViewById(R.id.et_rent_ground_team);
        et_rent_ground_purpose = findViewById(R.id.et_rent_ground_purpose);
        rent_ground_btn = findViewById(R.id.rent_ground_btn);
        with_analyst = findViewById(R.id.with_analyst);
        with_medical = findViewById(R.id.with_medical_trainer);
    }
}