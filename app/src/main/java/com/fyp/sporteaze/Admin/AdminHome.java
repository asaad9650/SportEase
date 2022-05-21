package com.fyp.sporteaze.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.fyp.sporteaze.BackPressDialog;
import com.fyp.sporteaze.R;

public class    AdminHome extends AppCompatActivity {

    private AppCompatButton btn_create_academy, btn_show_users, btn_show_academies , btn_show_coaches , btn_show_grounds;
    BackPressDialog backPressDialog= new BackPressDialog();

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            backPressDialog.logout(this);

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        btn_create_academy = findViewById(R.id.btn_create_academy);
        btn_show_academies = findViewById(R.id.btn_show_academies);
        btn_show_users = findViewById(R.id.btn_show_users);
        btn_show_coaches = findViewById(R.id.btn_show_coaches);
        btn_show_grounds = findViewById(R.id.btn_show_grounds);

        btn_show_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this , AdminShowUsers.class);
                startActivity(intent);
            }
        });

        btn_show_grounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this , AdminViewGrounds.class);
                startActivity(intent);
            }
        });

        btn_create_academy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this , AdminCreateAcademy.class);
                startActivity(intent);

            }
        });

        btn_show_coaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this , AdminViewCoaches.class);
                startActivity(intent);
            }
        });

        btn_show_academies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this , AdminShowAcademies.class);
                startActivity(intent);

            }
        });
    }
}