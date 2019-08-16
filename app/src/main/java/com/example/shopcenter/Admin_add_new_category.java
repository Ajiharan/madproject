package com.example.shopcenter;
import android.app.AlertDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import dmax.dialog.SpotsDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.RatingCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.CategoryItems;
import com.example.shopcenter.model.CategoryItemsAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class Admin_add_new_category extends AppCompatActivity {

    private  AlertDialog dialog;
    private static final int REQUEST_CODE_GALLERY=2;
    private boolean ExceptionFound=false;
    private byte[] photo=null;
    private ImageView admin_category_image;
    private EditText  admin_category_name;
    private Button add_new_category,admin_category_back_button;
    private DBHelper db;
    private RecyclerView recyclerView;
    CategoryItemsAdapter itemsAdapter;
    ArrayList<CategoryItems> admin_items;
    private Bitmap bp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_category);

        admin_items=new ArrayList<>();
        recyclerView=findViewById(R.id.category_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        admin_category_image=findViewById(R.id.admin_select_category_image);

        db=new DBHelper(this);

        admin_category_name=findViewById(R.id.admin_category_product_name);
        add_new_category=findViewById(R.id.admin_add_new_product_category);
        dialog = new SpotsDialog(this,"Updating");
        add_new_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_user_profilr_image();
            }
        });
        admin_category_back_button=findViewById(R.id.admin_category_back_btn);
        admin_category_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_add_new_category.this,AdminHomeActivity.class);
                startActivity(intent);
            }
        });
        admin_category_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        retrive_Admin_Category_Details();
    }

    private void retrive_Admin_Category_Details(){
         admin_items=db.Retrive_Product_Category_Details();

         itemsAdapter=new CategoryItemsAdapter(this,admin_items);
         recyclerView.setAdapter(itemsAdapter);

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
                        admin_category_image.setImageBitmap(bp);
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
            ExceptionFound=true;
        }
        finally {
            photo =  bos.toByteArray();
        }




    }
    public void Add_user_profilr_image(){

        getValues();
        if(ExceptionFound){
            Toast.makeText(Admin_add_new_category.this,"Please select an Image...",Toast.LENGTH_SHORT).show();
        }
        else {
            if(admin_category_name.getText().toString().isEmpty()){
                Toast.makeText(Admin_add_new_category.this,"Please Enter Category Name..",Toast.LENGTH_SHORT).show();
            }
            else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add New Profile").setMessage("Are you sure to add new category!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    dialog.show();


                   boolean isAdd = db.Admin_update_Category_Details(photo,admin_category_name.getText().toString());

                    if (isAdd) {
                        Toast.makeText(Admin_add_new_category.this, "New Product Category Added", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Admin_add_new_category.this,Admin_add_new_category.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Admin_add_new_category.this, "Error!! cannnot add profile", Toast.LENGTH_SHORT).show();
                    }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Admin_add_new_category.this, "Update Cancel..", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();
            }

            /* */
        }

    }
}
