package com.fyp.sporteaze.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fyp.sporteaze.Academy.Coach.AcademyCoachUpdate;
import com.fyp.sporteaze.Model.Coach;
import com.fyp.sporteaze.Model.User;
import com.fyp.sporteaze.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class UserUpdateProfile extends AppCompatActivity {

    String enrolled , captain,team_id, user_name,user_email,user_password,user_cnic,user_phone,user_dob,user_address,user_gender,user_id, user_image;
    EditText et_user_name,et_user_email , et_user_password,et_user_cnic,et_user_phone,et_user_dob,et_user_address,et_user_gender;
    ImageView img_upload_user_photo;
    AppCompatButton btn_user_update;
    Uri filepath;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_profile);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        et_user_name = findViewById(R.id.et_user_name);
        et_user_email = findViewById(R.id.et_user_email);
        et_user_password = findViewById(R.id.et_user_password);
        et_user_cnic = findViewById(R.id.et_user_cnic);
        et_user_phone = findViewById(R.id.et_user_phone);
        et_user_dob = findViewById(R.id.et_user_dob);
        et_user_address = findViewById(R.id.et_user_address);
        et_user_gender = findViewById(R.id.et_user_gender);

        img_upload_user_photo = findViewById(R.id.img_upload_user_photo);
        btn_user_update = findViewById(R.id.btn_user_update);


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        user_name = extras.getString("user_name");
        user_email = extras.getString("user_email");
        user_address = extras.getString("user_address");
        user_gender = extras.getString("user_gender");
        user_dob= extras.getString("user_dob");
        user_id= extras.getString("user_id");
        user_phone= extras.getString("user_phone");
        user_password = extras.getString("user_password");
        user_cnic= extras.getString("user_cnic");
        user_image= extras.getString("user_image");
        enrolled = extras.getString("enrolled");
        captain = extras.getString("captain");
        team_id = extras.getString("team_id");


        et_user_name.setText(user_name);
        et_user_address.setText(user_address);
        et_user_cnic.setText(user_cnic);
        et_user_dob.setText(user_dob);
        et_user_email.setText(user_email);
        et_user_gender.setText(user_gender);
        et_user_password.setText(user_password);
        et_user_phone.setText(user_phone);

        if(user_image==null || user_image.matches("")){
            img_upload_user_photo.setImageResource(R.drawable.upload_academy_photo_icon);
        }
        else{
            Glide.with(getApplicationContext()).load(user_image).into(img_upload_user_photo);
        }
        btn_user_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_user_name.getText().toString().matches("") || et_user_address.getText().toString().matches("")|| et_user_cnic.getText().toString().matches("")|| et_user_dob.getText().toString().matches("")|| et_user_email.getText().toString().matches("")|| et_user_gender.getText().toString().matches("")||et_user_password.getText().toString().matches("")||et_user_phone.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext() , "Please fill all fields." , Toast.LENGTH_SHORT).show();
                }
                else{
//                    if(et_academy_email.getText().toString().contains("@"))
//                    {
//                        if(et_academy_password.getText().toString().length()>6){
                    upload_to_firebase();
//                        }
//                        else{
//                            Toast.makeText(getApplicationContext() , "Password should be at least 7 characters" , Toast.LENGTH_SHORT).show();
//                        }
                }
//                    else{
//                        Toast.makeText(getApplicationContext() , "Please enter a valid email address" , Toast.LENGTH_SHORT).show();
//                    }


//                }

            }
        });

        img_upload_user_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(UserUpdateProfile.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/");
                                startActivityForResult(Intent.createChooser(intent , "Select Image File"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.cancelPermissionRequest();
                            }
                        }).check();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1  && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                //img_academy_photo.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                Glide.with(this)
                        .load(stream.toByteArray())
                        .apply(RequestOptions.circleCropTransform())
                        .into(img_upload_user_photo);

            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload_to_firebase(){
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Updating user, please wait");
        dialog.show();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference().child("users").child(et_user_name.getText().toString().trim());
        if(filepath != null) {
            uploader.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    dialog.dismiss();
                                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                                    DatabaseReference root = db.getReference();
                                    String key = root.push().getKey();
                                    Map<String, Object> updates = new HashMap<String,Object>();
                                    updates.put("name" , et_user_name.getText().toString().trim());
                                    updates.put("email" , et_user_email.getText().toString().trim());
                                    updates.put("password" ,et_user_password.getText().toString().trim());
                                    updates.put("cnic" , et_user_cnic.getText().toString().trim());
                                    updates.put("phone" , et_user_phone.getText().toString().trim());
                                    updates.put("dob" , et_user_dob.getText().toString().trim());
                                    updates.put("address" , et_user_address.getText().toString().trim());
                                    updates.put("gender" , et_user_gender.getText().toString().trim());
                                    updates.put("team_id" , team_id);
                                    updates.put("captain" , captain);
                                    updates.put("image" , user_image);
                                    updates.put("enrolled" , enrolled);
                                    root.child("Users").child(user_id).updateChildren(updates);
//                                    User obj = new User(et_user_name.getText().toString(), et_user_email.getText().toString(), et_user_password.getText().toString(), et_user_cnic.getText().toString(), et_user_phone.getText().toString(),et_user_dob.getText().toString(), et_user_address.getText().toString(), et_user_gender.getText().toString() , user_id , uri.toString() , enrolled , captain , team_id);
//                                    root.child("Users").child(user_id).setValue(obj);
//                                    root.child("Users").child(user_id)
                                    Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(UserUpdateProfile.this , DashboardActivity.class);
//                                    intent.putExtra("user_name" ,et_user_name.getText().toString().trim());
//                                    intent.putExtra("user_email", et_user_email.getText().toString().trim());
//                                    startActivity(intent);
//                                    finish();
                                    et_user_name.setText("");
                                    et_user_email.setText("");
                                    et_user_gender.setText("");
                                    et_user_dob.setText("");
                                    et_user_address.setText("");
                                    et_user_password.setText("");
                                    et_user_cnic.setText("");
                                    et_user_phone.setText("");
                                    img_upload_user_photo.setImageResource(R.drawable.upload_profile_photo);
                                }
                            });

                        }


                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    dialog.setMessage("Updated : " + (int) percent + "%");
                }
            });

        }

        else{
            dialog.dismiss();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference root = db.getReference();
            String key = root.push().getKey();

//            User obj = new User(et_user_name.getText().toString(), et_user_email.getText().toString(), et_user_password.getText().toString(), et_user_cnic.getText().toString(), et_user_phone.getText().toString(),et_user_dob.getText().toString(), et_user_address.getText().toString(), et_user_gender.getText().toString() , user_id , user_image , enrolled , captain , team_id);
            Map<String, Object> updates = new HashMap<String,Object>();
            updates.put("name" , et_user_name.getText().toString().trim());
            updates.put("email" , et_user_email.getText().toString().trim());
            updates.put("password" ,et_user_password.getText().toString().trim());
            updates.put("cnic" , et_user_cnic.getText().toString().trim());
            updates.put("phone" , et_user_phone.getText().toString().trim());
            updates.put("dob" , et_user_dob.getText().toString().trim());
            updates.put("address" , et_user_address.getText().toString().trim());
            updates.put("gender" , et_user_gender.getText().toString().trim());
            updates.put("team_id" , team_id);
            updates.put("captain" , captain);
            updates.put("image" , user_image);
            updates.put("enrolled" , enrolled);
            root.child("Users").child(user_id).updateChildren(updates);
//            root.child("Users").child(user_id).setValue(obj);
            Toast.makeText(getApplicationContext() , "Updated Successfully" , Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(UserUpdateProfile.this , DashboardActivity.class);
//            intent.putExtra("user_name" ,et_user_name.getText().toString().trim());
//            intent.putExtra("user_email", et_user_email.getText().toString().trim());
//            startActivity(intent);
//            finish();
            et_user_name.setText("");
            et_user_email.setText("");
            et_user_gender.setText("");
            et_user_dob.setText("");
            et_user_address.setText("");
            et_user_password.setText("");
            et_user_cnic.setText("");
            et_user_phone.setText("");
            img_upload_user_photo.setImageResource(R.drawable.upload_profile_photo);
        }

    }

}