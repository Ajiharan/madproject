package com.example.shopcenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class User_settings_Activity extends AppCompatActivity {
    private final int REQUEST_CODE_GALLERY=999;
    private ImageView current_user_image;
    private TextView change_profile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings_);

        current_user_image=findViewById(R.id.settings_profile_image);
        change_profile=findViewById(R.id.profile_image_change_btn);

        current_user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(User_settings_Activity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);
               // byte[] NewEntryImg=ImageViewTobyte(current_user_image);
            }

            private byte[] ImageViewTobyte(ImageView myImg){
                Bitmap bitmap=((BitmapDrawable)myImg.getDrawable()).getBitmap();
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                byte[] byteArray=stream.toByteArray();
                return byteArray;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length > 0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"You don't have permission to access file",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri=data.getData();
        try{
            InputStream inputStream=getContentResolver().openInputStream(uri);
            Bitmap bitmaps= BitmapFactory.decodeStream(inputStream);
            current_user_image.setImageBitmap(bitmaps);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
