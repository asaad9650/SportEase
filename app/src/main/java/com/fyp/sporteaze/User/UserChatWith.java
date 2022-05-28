package com.fyp.sporteaze.User;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.fyp.sporteaze.R;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class UserChatWith extends AppCompatActivity {
    TextView chat_with_email_or_name , chat_with_address;
    ImageView chat_with_picture;
    String user_id,  chat_with_id, user_chat_with , user_city_name , user_profile_pic;

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
        user_profile_pic = extras.getString("user_profile_pic");
        user_city_name = extras.getString("user_city_name");
//        captain = extras.getString("captain");

        chat_with_email_or_name = findViewById(R.id.chat_with_email_or_name);
        chat_with_address = findViewById(R.id.chat_with_address);
        chat_with_picture = findViewById(R.id.chat_with_picture);
        chat_with_email_or_name.setText(user_chat_with);
        chat_with_address.setText(user_city_name);
        Glide.with(getApplicationContext()).load(user_profile_pic).into(chat_with_picture);
//        user_city_name.setText(user_city_name);
//        chat_with_picture.setImageResource(R.drawable.user_icon);
//        if(!user_profile_pic.equals("")){
//        new DownLoadImageTask(Glide.with(getApplicationContext())chat_with_picture).execute(user_profile_pic);
//        }

//        Bitmap bitmap = BitmapFactory(user_profile_pic.openConnection().getInputStream());
//        chat_with_picture.setImageBitmap(bitmap);

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
            @RequiresApi(api = Build.VERSION_CODES.M)
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



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addMessageBox(String message, int type){
        TextView textView = new TextView(UserChatWith.this);

//        textView

        textView.setText(message);
        textView.setTextColor(textView.getContext().getColor(R.color.black));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

//        lp.setMargins(5, 10, 5, 10);
//        lp.gravity = Gravity.RIGHT;
//        textView.setLayoutParams(lp);
//        textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;

        if(type == 2) {


            lp.setMargins(5, 10, 5, 10);

            lp.gravity = Gravity.LEFT;

            textView.setPadding(20 , 10,20,10);
            textView.setLayoutParams(lp);
//            textView.setPadding(20 , 10,20,10);
            textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            textView.setBackgroundResource(R.drawable.rounded_corner2);

//            layout.setGravity(Gravity.END);
//            textView.setGravity(Gravity.END);
        }
        else{
            lp.setMargins(5, 10, 5, 10);
            lp.gravity = Gravity.RIGHT;

            textView.setPadding(20 , 10,20,10);
            textView.setLayoutParams(lp);

//            textView.setPadding(20 , 10,20,10);
            textView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            textView.setBackgroundResource(R.drawable.rounded_corner1);
//            layout.setGravity(Gravity.START);
        }
        layout.addView(textView);
        scrollView.fullScroll(View.FOCUS_DOWN);
    }


}

class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
    ImageView imageView;

    public DownLoadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
