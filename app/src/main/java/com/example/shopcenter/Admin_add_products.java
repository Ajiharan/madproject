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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;

import java.io.ByteArrayOutputStream;

import dmax.dialog.SpotsDialog;

public class Admin_add_products extends AppCompatActivity {

    private static final int REQUEST_CODE_GALLERY=2;
    private Bitmap bp=null;
    private boolean ExceptionFound=false;
    private AlertDialog dialog;
    private ImageView insert_image;
    private byte[] photo=null;
    private TextView admin_item_name;
    private DBHelper db;
    private Button addproduct_btn,back_button;
    private EditText product_Name,product_pri,product_desc,product_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_products);
        db=new DBHelper(this);
        admin_item_name=findViewById(R.id.admin_add_items_name);
        insert_image=findViewById(R.id.select_product_image);
        product_Name=findViewById(R.id.product_name);
        product_pri=findViewById(R.id.product_price);
        product_desc=findViewById(R.id.product_description);
        product_count=findViewById(R.id.product_counts);
        admin_item_name.setText(Prevelent.Currentcategories.getName());
        insert_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        back_button=findViewById(R.id.admin_add_product_back_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin_add_products.this, Admin_add_new_category.class);
                startActivity(intent);
            }
        });
        dialog = new SpotsDialog(this,"Adding..");

        addproduct_btn=findViewById(R.id.admin_add_new_product);
        addproduct_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_admin_Category_Details();
            }
        });

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
                        insert_image.setImageBitmap(bp);
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

    private void getValues() {
         /*
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);

    */
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {

            bp.compress(Bitmap.CompressFormat.PNG, 100, bos);
            ExceptionFound = false;
        } catch (Exception e) {
            e.printStackTrace();
            ExceptionFound = true;
        } finally {
            photo = bos.toByteArray();
        }

    }

    private void Add_admin_Category_Details(){

        getValues();
        if(ExceptionFound){
            Toast.makeText(Admin_add_products.this,"Please select an Image...",Toast.LENGTH_SHORT).show();
        }
        else {
            if( product_Name.getText().toString().isEmpty()){
                Toast.makeText(Admin_add_products.this,"Please Enter Product Name..",Toast.LENGTH_SHORT).show();
            }
            else if(product_desc.getText().toString().isEmpty()){
                Toast.makeText(Admin_add_products.this,"Please give Product Description..",Toast.LENGTH_SHORT).show();
            }
            else if(product_pri.getText().toString().isEmpty()){
                Toast.makeText(Admin_add_products.this,"Please Enter Product Price..",Toast.LENGTH_SHORT).show();
            }
            else if(product_count.getText().toString().isEmpty()){
                Toast.makeText(Admin_add_products.this,"Please give Product Count..",Toast.LENGTH_SHORT).show();
            }
            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add New Product").setMessage("Are you sure to add new category!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isNameAvailable=true;
                        dialog.show();
                        Cursor cu=db.Admin_Item_name_check();
                        while(cu.moveToNext()){
                            if(cu.getString(1).toString().equals(product_Name.getText().toString())){
                                isNameAvailable=false;
                                break;
                            }
                        }
                        if(isNameAvailable) {
                            boolean isAdd = db.Admin_add_product_Details(product_Name.getText().toString(),product_desc.getText().toString(),
                                    product_pri.getText().toString(),product_count.getText().toString(),photo, Prevelent.Currentcategories.getId(),Prevelent.Currentcategories.getName());

                            if (isAdd) {
                                Toast.makeText(Admin_add_products.this, "New Product  Added", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Admin_add_products.this, Admin_add_new_category.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Admin_add_products.this, "Error!! Failed to Add..", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                        else{
                            Toast.makeText(Admin_add_products.this, "Sorry Name Already Exists..", Toast.LENGTH_SHORT).show();

                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Admin_add_products.this, "Update Cancel..", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
            }


        }

    }


    }
