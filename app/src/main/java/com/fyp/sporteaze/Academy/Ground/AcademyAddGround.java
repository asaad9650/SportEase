package com.fyp.sporteaze.Academy.Ground;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fyp.sporteaze.Model.Ground;
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

public class AcademyAddGround extends AppCompatActivity {
    ImageView img_upload_ground_photos;
    EditText et_ground_name,et_ground_location,et_ground_charges, et_ground_size,et_ground_timings,et_ground_medical_training_availability,et_ground_analyst_availability;
    Button btn_add_ground;
    Uri filepath;
    Bitmap bitmap;
    String academy_email , academy_id, academy_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academy_add_ground);

        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        academy_email = extras.getString("academy_email");
        academy_id = extras.getString("academy_id");
        academy_name = extras.getString("academy_name");


        img_upload_ground_photos = findViewById(R.id.img_upload_ground_photos);

        et_ground_name = findViewById(R.id.et_ground_name);
        et_ground_location = findViewById(R.id.et_ground_location);
        et_ground_size = findViewById(R.id.et_ground_size);
        et_ground_charges = findViewById(R.id.et_ground_charges);
        et_ground_timings = findViewById(R.id.et_ground_timings);
        et_ground_medical_training_availability = findViewById(R.id.et_ground_medical_training_availability);
        et_ground_analyst_availability = findViewById(R.id.et_ground_analyst_availability);

        btn_add_ground = findViewById(R.id.btn_add_ground);

        img_upload_ground_photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(AcademyAddGround.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
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
        btn_add_ground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_ground_name.getText().toString().matches("") || et_ground_location.getText().toString().matches("")|| et_ground_size.getText().toString().matches("")|| et_ground_timings.getText().toString().matches("")|| et_ground_medical_training_availability.getText().toString().matches("")|| et_ground_analyst_availability.getText().toString().matches("")|| filepath == null)
                {
                    Toast.makeText(getApplicationContext() , "Please fill all fields." , Toast.LENGTH_SHORT).show();
                }
                else{

                    upload_to_firebase();

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
                        .into(img_upload_ground_photos);

            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upload_to_firebase(){
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("Registering ground, please wait");
        dialog.show();

        FirebaseStorage storage=FirebaseStorage.getInstance();
        final StorageReference uploader=storage.getReference().child("Ground").child(et_ground_name.getText().toString().trim());
//        if(filepath != null) {
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
                                Ground obj = new Ground(academy_id,key, et_ground_name.getText().toString(), et_ground_location.getText().toString(), et_ground_size.getText().toString(), et_ground_timings.getText().toString(), et_ground_medical_training_availability.getText().toString(), et_ground_analyst_availability.getText().toString() , uri.toString(), et_ground_charges.getText().toString() + " Rs");
                                root.child("grounds").child(key).setValue(obj);
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                et_ground_name.setText("");
                                et_ground_size.setText("");
                                et_ground_analyst_availability.setText("");
                                et_ground_timings.setText("");
                                et_ground_medical_training_availability.setText("");
                                et_ground_location.setText("");
                                img_upload_ground_photos.setImageResource(R.drawable.add_ground_photos);
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

//        }

//        else{
//            dialog.dismiss();
//            FirebaseDatabase db = FirebaseDatabase.getInstance();
//            DatabaseReference root = db.getReference();
//            String key = root.push().getKey();
//            DataHolder obj = new DataHolder(et_academy_name.getText().toString(), et_academy_email.getText().toString(),et_academy_password.getText().toString(),et_academy_address.getText().toString(), et_academy_phone.getText().toString(),"" , key);
//            root.child("academies").child(key).setValue(obj);
//            Toast.makeText(getApplicationContext() , "Registered Successfully" , Toast.LENGTH_SHORT).show();
//            et_academy_name.setText("");
//            et_academy_email.setText("");
//            et_academy_password.setText("");
//            et_academy_address.setText("");
//            et_academy_phone.setText("");
//            img_academy_photo.setImageResource(R.drawable.upload_academy_photo_icon);
//        }

    }


}