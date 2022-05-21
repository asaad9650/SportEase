package com.fyp.sporteaze.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.fyp.sporteaze.R;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class UserChatWith extends AppCompatActivity {
    TextView chat_with_email_or_name;
    String user_id,  chat_with_id, user_chat_with;

    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    Firebase reference1, reference2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat_with);

        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);




        Intent intent = getIntent();


        Bundle extras = intent.getExtras();
        user_id = extras.getString("user_id");
        chat_with_id = extras.getString("user_chat_with_id");
        user_chat_with = extras.getString("user_chat_with");
//        captain = extras.getString("captain");

        chat_with_email_or_name = findViewById(R.id.chat_with_email_or_name);


        chat_with_email_or_name.setText(user_chat_with);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://sporteaze-60476-default-rtdb.asia-southeast1.firebasedatabase.app//messages/" + user_id + "_" + chat_with_id);
        reference2 = new Firebase("https://sporteaze-60476-default-rtdb.asia-southeast1.firebasedatabase.app//messages/" + chat_with_id + "_" + user_id);
//        getSupportActionBar().setTitle(user_chat_with);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if(!messageText.equals("")){
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", messageText);
                    map.put("user", chat_with_id);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();

                if(!userName.equals(user_id)){
//                    String namee = "You:";
//                    SpannableString ss = new SpannableString(namee);
//                    SpannableStringBuilder ssb = new SpannableStringBuilder(namee);
//                    ssb.setSpan(fcsRed, 7, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//


                    addMessageBox("You:\n" + message, 1);
                }
                else{
                    addMessageBox(user_chat_with + ":\n" + message, 2);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }

    public void addMessageBox(String message, int type){
        TextView textView = new TextView(UserChatWith.this);

//        textView
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 5, 5, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }
}