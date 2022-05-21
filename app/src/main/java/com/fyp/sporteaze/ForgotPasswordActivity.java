package com.fyp.sporteaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.fyp.sporteaze.Support.GMailSender;
import com.fyp.sporteaze.Support.JavaMailAPI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText email;
    String keyMain;
    AppCompatButton submit;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
//https://api.openweathermap.org/data/2.5/weather?q=karachi&appid=f23057d8b3333b8dabc35e7f5381e241

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.forgot_password_email);
        submit = findViewById(R.id.forgot_password_submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().toString().trim() != "" && Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    databaseReference.child("Users").orderByChild("email").equalTo(email.getText().toString().trim()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot snapshot1: snapshot.getChildren()) {
                                    keyMain = snapshot1.getKey();
//                                    Toast.makeText(getApplicationContext(), keyMain + " Ye hai", Toast.LENGTH_LONG).show();
                                    try {

//                                        final String user_email_to_send_code = "fypsporteaze@gmail.com";
//                                        final String pass = "sporteaze.22";
//                                        int code = Integer.parseInt(String.format("%04d", new Random().nextInt(10000)));
//                                        String messageToSend = "Your password reset verification code for Sporteaze is " +code;
//                                        Properties props = new Properties();
//                                        props.put("mail.smtp.auth" ,"true");
//                                        props.put("mail.smtp.starttls.enable",true);
//                                        props.put("mail.smtp.host" , "smtp.gmail.com");
//                                        props.put("mail.smtp.port","587");
//
//                                        Session session = Session.getInstance(props, new javax.mail.Authenticator(){
//                                                    @Override
//                                                    protected PasswordAuthentication getPasswordAuthentication() {
//                                                        return new PasswordAuthentication(user_email_to_send_code, pass);
//                                                    }
//                                                });
//                                        try{
//                                            MimeMessage message = new MimeMessage(session);
//                                            message.setFrom(new InternetAddress(user_email_to_send_code));
//                                            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse("asaad9650@gmail.com"));
//                                            message.setSubject("Password Reset For Sporteaze");
//                                            message.setText(messageToSend);
//                                            Transport.send(message);
//                                            Toast.makeText(ForgotPasswordActivity.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
//                                        }
//                                        catch (MessagingException e){
//                                            throw new RuntimeException(e);
//                                        }
//                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                                        StrictMode.setThreadPolicy(policy);


                                        Toast.makeText(ForgotPasswordActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                        int code = Integer.parseInt(String.format("%04d", new Random().nextInt(10000)));
                                        JavaMailAPI javaMailAPI = new JavaMailAPI(ForgotPasswordActivity.this,
                                                email.getText().toString().trim(),
                                                "Password Reset For Sporteaze",
                                                "Your password reset verification code for Sporteaze is " + code);
                                        javaMailAPI.execute();

//                                        GMailSender sender = new GMailSender("fypsporteaze21@gmail.com", "sporteaze.22");
//                                        sender.sendMail("Password Reset For Sporteaze",
//                                                "Your password reset verification code for Sporteaze is " +code,
//                                                "fypsporteaze@gmail.com",
//                                                "saad.amir@foundri.net");


//                                        Toast.makeText(getApplicationContext(), keyMain + " Or Ye hai code "+ code, Toast.LENGTH_LONG).show();
//
                                        Intent intent = new Intent(ForgotPasswordActivity.this, CodeActivity.class);
                                        intent.putExtra("code", code);
                                        intent.putExtra("key", keyMain);
                                        startActivity(intent);
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }

//                                    break;
//                                }
//                                new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                    }
//                                }).start();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Email does not exist.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please enter proper credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}