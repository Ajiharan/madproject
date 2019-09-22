package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;

import java.io.ByteArrayOutputStream;

import dmax.dialog.SpotsDialog;

public class Admin_edit_categories extends AppCompatActivity {
    private AlertDialog dialog;
    private static final int REQUEST_CODE_GALLERY=2;
    private ImageView admin_category_edit_image;
    private EditText admin_edit_category_product_names;
    private DBHelper db;
    private Bitmap bp=null;
    private boolean ExceptionFound=false;
    private byte[] photo=null;
    private Button admin_back,admin_delete,admin_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_categories);
        admin_category_edit_image=findViewById(R.id.admin_edit_category_image);
        admin_category_edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        admin_update=findViewById(R.id.Admin_update_current_category);
        admin_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Update_Admin_Category_Details();
            }
        });
        admin_edit_category_product_names=findViewById(R.id.admin_edit_category_product_name);
        admin_delete=findViewById(R.id.Admin_delete_current_category);
        dialog = new SpotsDialog(this,"Deleting..");
        db=new DBHelper(this);
        admin_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete_Details();
            }
        });
        admin_back=findViewById(R.id.admin_edit_category_back_btn);
        admin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_edit_categories.this,Admin_add_new_category.class);
                startActivity(intent);
            }
        });
        setDetails();
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
                        admin_category_edit_image.setImageBitmap(bp);
                    }
                }
        }
    }
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
            bp = Prevelent.Currentcategories.getBitmap();

            bp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            photo = bos.toByteArray();
            if(photo.length <= 0 || photo == null){
                ExceptionFound=true;
            }
        }
        finally {
            photo =  bos.toByteArray();
        }




    }

    private void Delete_Details() {

        dialog.show();
        boolean isDelete=db.Admin_delete_category(Prevelent.Currentcategories.getId());

        if(isDelete){
            Intent intent=new Intent(Admin_edit_categories.this,Admin_add_new_category.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(Admin_edit_categories.this,"Error something Wrong...",Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }

    }


    private void setDetails() {
        //Toast.makeText(Admin_edit_categories.this,Prevelent.Currentcategories.getId(),Toast.LENGTH_LONG).show();
        admin_category_edit_image.setImageBitmap(Prevelent.Currentcategories.getBitmap());
        admin_edit_category_product_names.setText(Prevelent.Currentcategories.getName());
    }
   private void Update_Admin_Category_Details(){

        getValues();
        if(ExceptionFound){
            Toast.makeText(Admin_edit_categories.this,"Please select an Image...",Toast.LENGTH_SHORT).show();
        }
        else {
            if(admin_edit_category_product_names.getText().toString().isEmpty()){
                Toast.makeText(Admin_edit_categories.this,"Please Enter Category Name..",Toast.LENGTH_SHORT).show();
            }
            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add New Category Image").setMessage("Are you sure to add new category!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isNameAvailable=true;
                        dialog.show();
                        Cursor cu=db.Admin_product_name_check();
                        while(cu.moveToNext()){
                            if(cu.getString(2).toString().equals(admin_edit_category_product_names.getText().toString())){
                                if(!cu.getString(0).toString().equals(Prevelent.Currentcategories.getId())){
                                    isNameAvailable=false;
                                    break;
                                }

                            }
                        }
                        if(isNameAvailable) {
                            boolean isAdd = db.Admin_update_category_Info(Prevelent.Currentcategories.getId(),admin_edit_category_product_names.getText().toString(),photo);

                            if (isAdd) {
                                Toast.makeText(Admin_edit_categories.this, "New Product Category Updated", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Admin_edit_categories.this, Admin_add_new_category.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Admin_edit_categories.this, "Error!! Failed to Update...", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                        else{
                            Toast.makeText(Admin_edit_categories.this, "Sorry Name Already Exists..", Toast.LENGTH_SHORT).show();

                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Admin_edit_categories.this, "Update Cancel..", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
            }


        }

    }
}
