package com.fyp.sporteaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText password;
    AppCompatButton submit;
    DatabaseReference databaseReference;
    String key;
    BackPressDialog backPressDialog = new BackPressDialog();
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            backPressDialog.exit_application(this);

            //moveTaskToBack(false);

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        password = findViewById(R.id.txtPassword);
        submit = findViewById(R.id.submit);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        key = getIntent().getStringExtra("key");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString() != ""){
                    databaseReference.child("Users").child(key).child("password").setValue(password.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "Password changed successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                }

                else{
                    Toast.makeText(getApplicationContext(), "Please enter the password.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}