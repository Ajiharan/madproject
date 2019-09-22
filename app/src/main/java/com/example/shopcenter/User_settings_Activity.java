package com.example.shopcenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_settings_Activity extends AppCompatActivity {
    private static final int REQUEST_CODE_GALLERY=2;

    boolean ExceptionFound=false;
    FloatingActionButton get_image_gallery;
    private CircleImageView current_user_image;
    private TextView change_profile,closebtn;
    private byte[] photo=null;
    private EditText user_mail,user_name,user_password;
    Button deactivaBTN,UpdateDetails;
    DBHelper db;

    private Bitmap bp=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings_);
        db=new DBHelper(this);
        current_user_image=findViewById(R.id.settings_profile_image);

        get_image_gallery=findViewById(R.id.user_profile_chane_btn);
        closebtn=findViewById(R.id.close_settings_button);
        change_profile=findViewById(R.id.profile_image_change_btn);
        user_mail=findViewById(R.id.settings_mail_id);
        user_name=findViewById(R.id.settings_name);
        user_password=findViewById(R.id.settings_password);

        UpdateDetails=findViewById(R.id.update_account);
        deactivaBTN=findViewById(R.id.delete_account);






        user_mail.setText(Prevelent.currentOnlineUser.getMail());
        user_mail.setKeyListener(null);


        user_password.setText(Prevelent.currentOnlineUser.getPassword());
        user_password.setKeyListener(null);


        user_name.setText(Prevelent.currentOnlineUser.getName());
        //user_name.setKeyListener(null);





        UpdateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean update = db.updateUser(Prevelent.currentOnlineUser.getId(), user_name.getText().toString());
                if(update){
                    Prevelent.currentOnlineUser.setName(user_name.getText().toString());
                    Intent intent=new Intent(User_settings_Activity.this,AppHomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Username Update success full", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deactivaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean delData = db.deleteData(user_mail.getText().toString());
                if(delData){
                    Intent intent=new Intent(User_settings_Activity.this,LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Deactivate Success full", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Deactivate Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });







        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_settings_Activity.this,AppHomeActivity.class);
                startActivity(intent);
            }
        });


        change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change_profile.setPaintFlags(change_profile.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                //  Add_user_profilr_image();
                Add_user_profilr_image();
            }
        });

        get_image_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
                //ActivityCompat.requestPermissions(User_settings_Activity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);

                // byte[] NewEntryImg=ImageViewTobyte(current_user_image);
                //  newEntryImages=NewEntryImg;
            }
        });

        retrive_profile_image();

    }

    public void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case REQUEST_CODE_GALLERY:
                if(resultCode == RESULT_OK){
                    Uri choosenImage = data.getData();

                    if(choosenImage !=null){

                        bp=decodeUri(choosenImage, 400);
                        current_user_image.setImageBitmap(bp);
                    }
                }
        }
    }
    //COnvert and resize our image to 400dp for faster uploading our images to DB
    protected Bitmap decodeUri(Uri selectedImage, int REQUIRED_SIZE) {

        try {

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);

            // The new size we want to scale to
            // final int REQUIRED_SIZE =  size;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE
                        || height_tmp / 2 < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private void getValues(){
         /*
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

    */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try{

            bp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            ExceptionFound=false;
        }
        catch (Exception e){
            e.printStackTrace();
            ExceptionFound=true;
        }
        finally {
            photo =  bos.toByteArray();
        }




    }




    public void retrive_profile_image(){
        Bitmap bitmapss=db.getImage(Prevelent.currentOnlineUser.getId());

        if(bitmapss != null){
            System.out.println("Bit :"+bitmapss);
            current_user_image.setImageBitmap(bitmapss);
        }

    }


   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_GALLERY){
            if(grantResults.length > 0&& grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(),"You  have permission to access file",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"You don't have permission to access file",Toast.LENGTH_SHORT).show();
            }
        }

    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data !=null && requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK ) {
           Imageuri=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(Imageuri);
               Bitmap bitmaps = BitmapFactory.decodeStream(inputStream);
                current_user_image.setImageBitmap(bitmaps);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            Toast.makeText(getApplicationContext(),"Something wrong",Toast.LENGTH_SHORT).show();
        }
    }*/

    public void Add_user_profilr_image(){

        getValues();
        if(ExceptionFound){
            Toast.makeText(User_settings_Activity.this,"Please select an Image",Toast.LENGTH_SHORT).show();
        }
        else {
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Add New Profile").setMessage("Are you sure to change!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean isAdd = db.Customer_Profile_insert(Prevelent.currentOnlineUser.getId(), photo);

                    if (isAdd) {
                        Toast.makeText(User_settings_Activity.this, "Profile Image Added", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(User_settings_Activity.this,AppHomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(User_settings_Activity.this, "Error!! cannnot add profile", Toast.LENGTH_SHORT).show();
                    }
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(User_settings_Activity.this, "Profile not change", Toast.LENGTH_SHORT).show();
                }
            }).setCancelable(false).show();

            /* */
        }

    }
}


