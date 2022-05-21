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
import android.text.Editable;
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
import java.nio.charset.StandardCharsets;

public class AdminAcademyUpdate extends AppCompatActivity {
    private DatabaseReference reference;

    String academy_name, academy_image, academy_email, academy_address, academy_phone, academy_password , academy_id;
    ImageView img_academy_photo_update;
    AppCompatButton register_academy_update;
    Uri filepath;
    Bitmap bitmap;
EditText et_academy_phone_number_update,et_academy_email_update, et_academy_name_update,et_academy_address_update,et_academy_password_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_academy_update);

        et_academy_address_update = findViewById(R.id.et_academy_address_update);
        et_academy_name_update = findViewById(R.id.et_academy_name_update);
        et_academy_password_update = findViewById(R.id.et_academy_password_update);
        et_academy_phone_number_update = findViewById(R.id.et_academy_phone_number_update);
        et_academy_email_update = findViewById(R.id.et_academy_email_update);
        register_academy_update = findViewById(R.id.register_academy_update);
        img_academy_photo_update = findViewById(R.id.img_academy_photo_update);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        academy_id = extras.getString("academy_id");
        academy_image = extras.getString("academy_image");
        academy_name = extras.getString("academy_name");
        academy_email = extras.getString("academy_email");
        academy_address = extras.getString("academy_address");
        academy_phone = extras.getString("academy_phone");
        academy_password = extras.getString("academy_password");

        if(academy_image.matches("")){
            img_academy_photo_update.setImageResource(R.drawable.upload_academy_photo_icon);
        }
        else{
            Glide.with(getApplicationContext()).load(academy_image).into(img_academy_photo_update);
        }

        et_academy_email_update.setText(academy_email);
        et_academy_name_update.setText(academy_name);
        et_academy_phone_number_update.setText(academy_phone);
        et_academy_address_update.setText(academy_address);
        et_academy_password_update.setText(academy_password);

        img_academy_photo_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AdminAcademyUpdate.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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

        register_academy_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_academy_name_update.getText().toString().matches("") || et_academy_email_update.getText().toString().matches("")|| et_academy_password_update.getText().toString().matches(""))
                {
                    Toast.makeText(getApplicationContext() , "Please fill all mandatory fields." , Toast.LENGTH_SHORT).show();
                }
                else{
                    if(et_academy_email_update.getText().toString().contains("@"))
                    {
                        if(et_academy_password_update.getText().toString().length()>6){
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
                        .into(img_academy_photo_update);

            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload_to_firebase(){
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Updating Academy, please wait");
        dialog.show();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference(et_academy_name_update.getText().toString().trim());

        if (filepath!= null){
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
                                    DataHolder obj;
                                    //if(uri != null){
                                    obj = new DataHolder(et_academy_name_update.getText().toString(), et_academy_email_update.getText().toString(),et_academy_password_update.getText().toString(),et_academy_address_update.getText().toString(), et_academy_phone_number_update.getText().toString(),uri.toString() , academy_id);

                                    //}

                                    root.child("academies").child(academy_id).setValue(obj);
                                    Toast.makeText(getApplicationContext() , "Updated Successfully" , Toast.LENGTH_LONG).show();
                                    et_academy_name_update.setText("");
                                    et_academy_email_update.setText("");
                                    et_academy_password_update.setText("");
                                    et_academy_address_update.setText("");
                                    et_academy_phone_number_update.setText("");
                                    img_academy_photo_update.setImageResource(R.drawable.upload_academy_photo_icon);
                                }
                            });

                        }


                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    float percent = (100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    dialog.setMessage("Updated : " + (int)percent + "%");
                }
            });
        }
        else{

            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference root = db.getReference();
            DataHolder obj;
            //if(uri != null){
            obj = new DataHolder(et_academy_name_update.getText().toString(), et_academy_email_update.getText().toString(),et_academy_password_update.getText().toString(),et_academy_address_update.getText().toString(), et_academy_phone_number_update.getText().toString(),academy_image , academy_id);

            //}

            root.child("academies").child(academy_id).setValue(obj);
            dialog.dismiss();
            Toast.makeText(getApplicationContext() , "Updated Successfully" , Toast.LENGTH_LONG).show();
            et_academy_name_update.setText("");
            et_academy_email_update.setText("");
            et_academy_password_update.setText("");
            et_academy_address_update.setText("");
            et_academy_phone_number_update.setText("");
            img_academy_photo_update.setImageResource(R.drawable.upload_academy_photo_icon);

        }

    }

}