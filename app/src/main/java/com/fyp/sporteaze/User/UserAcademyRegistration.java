package com.fyp.sporteaze.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.sporteaze.Model.Academy_registration_individual;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAcademyRegistration extends AppCompatActivity {

    EditText et_academy_registration_name, et_academy_registration_address,et_academy_registration_age,
            et_academy_registration_expertise,et_academy_registration_phone,et_academy_registration_email;

    AppCompatButton btn_register_in_academy;
    String user_id,academy_id,academy_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_academy_registration);

        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");

        et_academy_registration_name = findViewById(R.id.et_academy_registration_name);
        et_academy_registration_address = findViewById(R.id.et_academy_registration_address);
        et_academy_registration_age = findViewById(R.id.et_academy_registration_age);
        et_academy_registration_expertise = findViewById(R.id.et_academy_registration_expertise);
        et_academy_registration_phone = findViewById(R.id.et_academy_registration_phone);
        et_academy_registration_email = findViewById(R.id.et_academy_registration_email);

        btn_register_in_academy = findViewById(R.id.btn_register_in_academy);



        btn_register_in_academy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_academy_registration_address.getText().toString().matches("")||
                        et_academy_registration_name.getText().toString().matches("")||
                        et_academy_registration_age.getText().toString().matches("")||
                        et_academy_registration_email.getText().toString().matches("")||
                        et_academy_registration_expertise.getText().toString().matches("")||
                        et_academy_registration_phone.getText().toString().matches(""))
                {
                    Toast.makeText(UserAcademyRegistration.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                    String key = db.push().getKey();
//                    Academy_registration_individual academy_registration = new Academy_registration_individual(key ,
//                            user_id,
//                            et_academy_registration_name.getText().toString().trim(),
//                            et_academy_registration_email.getText().toString().trim(),
//                            et_academy_registration_phone.getText().toString().trim(),
//                            et_academy_registration_age.getText().toString().trim(),
//                            et_academy_registration_expertise.getText().toString().trim(),
//                            et_academy_registration_address.getText().toString().trim(),
//                            academy_id ,
//                            academy_name ,
//                            "pending");

//                    db.child("academy_bookings").child(key).setValue(academy_registration);

                    Toast.makeText(UserAcademyRegistration.this, "Successfully requested.", Toast.LENGTH_SHORT).show();
                    et_academy_registration_phone.setText("");
                    et_academy_registration_name.setText("");
                    et_academy_registration_address.setText("");
                    et_academy_registration_email.setText("");
                    et_academy_registration_expertise.setText("");
                    et_academy_registration_age.setText("");
                }
            }
        });

    }
}