package com.fyp.sporteaze.Admin;

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
import java.util.Random;

public class AdminCreateAcademy extends AppCompatActivity {
ImageView img_academy_photo;
AppCompatButton register_academy;
    EditText et_academy_name,et_academy_password, et_academy_email, et_academy_address, et_academy_phone;
    Uri filepath;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_academy);

        register_academy = findViewById(R.id.register_academy);
        et_academy_name = findViewById(R.id.et_academy_name);
        et_academy_email = findViewById(R.id.et_academy_email);
        et_academy_address = findViewById(R.id.et_academy_address);
        et_academy_password = findViewById(R.id.et_academy_password);
        et_academy_phone = findViewById(R.id.et_academy_phone_number);

        img_academy_photo = findViewById(R.id.img_academy_photo);


        img_academy_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AdminCreateAcademy.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        register_academy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_academy_name.getText().toString().matches("") || et_academy_email.getText().toString().matches("")|| et_academy_password.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext() , "Please fill all mandatory fields." , Toast.LENGTH_SHORT).show();
                }
                else{
                    if(et_academy_email.getText().toString().contains("@"))
                    {
                        if(et_academy_password.getText().toString().length()>6){
                            upload_to_firebase();
                        }
                        else{
                            Toast.makeText(getApplicationContext() , "Password should be at least 7 characters" , Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext() , "Please enter a valid email address" , Toast.LENGTH_SHORT).show();
                    }


                }

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
                        .into(img_academy_photo);

            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload_to_firebase(){
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Registering Academy, please wait");
        dialog.show();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference(et_academy_name.getText().toString().trim());
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
                                    DataHolder obj = new DataHolder(et_academy_name.getText().toString(), et_academy_email.getText().toString(), et_academy_password.getText().toString(), et_academy_address.getText().toString(), et_academy_phone.getText().toString(), uri.toString(), key);
                                    root.child("academies").child(key).setValue(obj);
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    et_academy_name.setText("");
                                    et_academy_email.setText("");
                                    et_academy_password.setText("");
                                    et_academy_address.setText("");
                                    et_academy_phone.setText("");
                                    img_academy_photo.setImageResource(R.drawable.upload_academy_photo_icon);
                                }
                            });

                        }


                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    dialog.setMessage("Registered : " + (int) percent + "%");
                }
            });

        }

        else{
            dialog.dismiss();
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference root = db.getReference();
            String key = root.push().getKey();
            DataHolder obj = new DataHolder(et_academy_name.getText().toString(), et_academy_email.getText().toString(),et_academy_password.getText().toString(),et_academy_address.getText().toString(), et_academy_phone.getText().toString(),"" , key);
            root.child("academies").child(key).setValue(obj);
            Toast.makeText(getApplicationContext() , "Registered Successfully" , Toast.LENGTH_SHORT).show();
            et_academy_name.setText("");
            et_academy_email.setText("");
            et_academy_password.setText("");
            et_academy_address.setText("");
            et_academy_phone.setText("");
            img_academy_photo.setImageResource(R.drawable.upload_academy_photo_icon);
        }

    }



}

