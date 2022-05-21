package com.fyp.sporteaze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CodeActivity extends AppCompatActivity {

    EditText code;

    AppCompatButton submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);
        
        code = findViewById(R.id.code);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(code.getText().toString()) == getIntent().getIntExtra("code", 0)){
                    Intent intent = new Intent(CodeActivity.this, ChangePasswordActivity.class);
                    intent.putExtra("key", getIntent().getStringExtra( "key"));
                    startActivity(intent);
                }

                else{
                    Toast.makeText(getApplicationContext(), "Incorrect code.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}