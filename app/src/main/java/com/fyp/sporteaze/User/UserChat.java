package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.fyp.sporteaze.Admin.UsersHelperAdatper;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserChat extends AppCompatActivity {

    List<User> userList;
    String user_name, user_email, user_id, captain;

    RecyclerView recyclerView;
    UserChatAdapter userChatAdapter;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_id = extras.getString("user_id");

        recyclerView = findViewById(R.id.users_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    User us = ds.getValue(User.class);
                    userList.add(us);
                }
                userChatAdapter = new UserChatAdapter(userList, user_id);
                recyclerView.setAdapter(userChatAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        databaseReference2 = FirebaseDatabase.getInstance().getReference("Users").child(user_id);
    }
}
