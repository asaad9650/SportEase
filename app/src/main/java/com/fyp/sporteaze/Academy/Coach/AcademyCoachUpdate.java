package com.fyp.sporteaze.Academy.Coach;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fyp.sporteaze.Admin.AdminAcademyUpdate;
import com.fyp.sporteaze.Model.Coach;
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

public class AcademyCoachUpdate extends AppCompatActivity {


    ImageView img_upload_coach_photo;
    EditText et_coach_name, et_coach_address,et_coach_age,et_coach_expertise,et_coach_phone,et_coach_charges;
    String coach_id, coach_name, coach_address,coach_phone, coach_charges, coach_age, coach_image, coach_expertise;
    Button btn_add_coach;
    Uri filepath;
    Bitmap bitmap;
    String academy_email , academy_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_coach_update);

//        Intent intent = getIntent();
//        Bundle extras = intent.getExtras();
//        academy_email = extras.getString("academy_email");
//        academy_id = extras.getString("academy_id");

        img_upload_coach_photo = findViewById(R.id.img_upload_coach_photo);

        et_coach_name = findViewById(R.id.et_coach_name);
        et_coach_address = findViewById(R.id.et_coach_address);
        et_coach_age = findViewById(R.id.et_coach_age);
        et_coach_expertise = findViewById(R.id.et_coach_expertise);
        et_coach_phone = findViewById(R.id.et_coach_phone);
        et_coach_charges = findViewById(R.id.et_coach_charges);
        btn_add_coach = findViewById(R.id.btn_add_coach);


        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        coach_id = extras.getString("coach_id");
        coach_image = extras.getString("coach_image");
        coach_name = extras.getString("coach_name");
        coach_address = extras.getString("coach_address");
        coach_age = extras.getString("coach_age");
        coach_charges = extras.getString("coach_charges");
        coach_expertise = extras.getString("coach_expertise");
        coach_phone = extras.getString("coach_phone");

        if(coach_image.matches("")){
            img_upload_coach_photo.setImageResource(R.drawable.upload_academy_photo_icon);
        }
        else{
            Glide.with(getApplicationContext()).load(coach_image).into(img_upload_coach_photo);
        }

        et_coach_address.setText(coach_address);
        et_coach_name.setText(coach_name);
        et_coach_phone.setText(coach_phone);
        et_coach_charges.setText(coach_charges);
        et_coach_expertise.setText(coach_expertise);
        et_coach_age.setText(coach_age);

        btn_add_coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_coach_name.getText().toString().matches("") || et_coach_address.getText().toString().matches("")|| et_coach_charges.getText().toString().matches("")|| et_coach_phone.getText().toString().matches("")|| et_coach_age.getText().toString().matches("")|| et_coach_expertise.getText().toString().matches(""))
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





        img_upload_coach_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AcademyCoachUpdate.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
                        .into(img_upload_coach_photo);

            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload_to_firebase(){
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Updating Coach, please wait");
        dialog.show();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference().child("Coach").child(et_coach_name.getText().toString().trim());
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
                                Coach obj = new Coach(academy_id,uri.toString(), et_coach_name.getText().toString(), et_coach_address.getText().toString(), et_coach_age.getText().toString(), et_coach_expertise.getText().toString(), et_coach_phone.getText().toString(), et_coach_charges.getText().toString() , coach_id);
                                root.child("coaches").child(coach_id).setValue(obj);
                                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                et_coach_name.setText("");
                                et_coach_phone.setText("");
                                et_coach_expertise.setText("");
                                et_coach_address.setText("");
                                et_coach_charges.setText("");
                                et_coach_age.setText("");
                                img_upload_coach_photo.setImageResource(R.drawable.upload_profile_photo);
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
            Coach obj = new Coach(academy_id,coach_image,et_coach_name.getText().toString(), et_coach_address.getText().toString(),et_coach_age.getText().toString(),et_coach_expertise.getText().toString(), et_coach_phone.getText().toString(),et_coach_charges.getText().toString() , coach_id);
            root.child("coaches").child(coach_id).setValue(obj);
            Toast.makeText(getApplicationContext() , "Updated Successfully" , Toast.LENGTH_SHORT).show();
            et_coach_name.setText("");
            et_coach_phone.setText("");
            et_coach_charges.setText("");
            et_coach_age.setText("");
            et_coach_expertise.setText("");
            et_coach_address.setText("");
            img_upload_coach_photo.setImageResource(R.drawable.upload_profile_photo);
        }

    }





}