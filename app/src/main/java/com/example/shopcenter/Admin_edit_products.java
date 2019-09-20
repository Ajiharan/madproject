package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.shopcenter.model.Products;
import com.example.shopcenter.Adapters.ProductsAdapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class Admin_edit_products extends AppCompatActivity {

    private AlertDialog dialog;
    private static final int REQUEST_CODE_GALLERY=2;
    private boolean ExceptionFound=false;
    private byte[] photo=null;
    private ImageView admin_product_image;
    private EditText admin_product_name,admin_product_des,admin_product_price,admin_product_counts;
    private Button delete_new_product_button,admin_product_back_button,admin_update_button;
    private DBHelper db;
    private TextView admin_category_edit_heading;
    private RecyclerView recyclerView;
    private ProductsAdapter itemsAdapter;
    private ArrayList<Products> admin_items;

    private Bitmap bp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_products);
        admin_product_image=findViewById(R.id.edit_product_image);
        admin_product_back_button=findViewById(R.id.admin_edit_product_back_btn);
        admin_product_name=findViewById(R.id.edit_product_name);
        admin_product_des=findViewById(R.id.edit_product_description);
        admin_product_price=findViewById(R.id.edit_product_price);
        admin_update_button=findViewById(R.id.admin_update_current_product);
        admin_product_counts=findViewById(R.id.edit_product_count);
        dialog = new SpotsDialog(this,"Updating..");
        admin_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(Admin_edit_products.this,"Product!",Toast.LENGTH_SHORT).show();
                Update_Admin_product_Details();
            }
        });

        admin_product_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_edit_products.this,AdminHomeActivity.class);
                startActivity(intent);
            }
        });

       delete_new_product_button=findViewById(R.id.admin_edit_product_delete);
        admin_product_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        db=new DBHelper(this);

        delete_new_product_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_admin_product();
            }
        });

        initializeComponents();

    }

    private void initializeComponents(){
        admin_product_image.setImageBitmap(Prevelent.current_admin_products.getBitmap());
        admin_product_name.setText(Prevelent.current_admin_products.getProduct_name());
        admin_product_price.setText(Prevelent.current_admin_products.getProduct_price());
        admin_product_des.setText(Prevelent.current_admin_products.getProduct_desc());
        admin_product_counts.setText(Prevelent.current_admin_products.getCount());

    }


    private void delete_admin_product(){

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add new Products").setMessage("Are you sure want to delete this Item?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean isDeleted=db.Admin_delete_current_product(Prevelent.current_admin_products.getProduct_id());
                if(isDeleted){
                    Toast.makeText(Admin_edit_products.this,"Product deleted Sucessfully!!",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Admin_edit_products.this,AdminHomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(Admin_edit_products.this,"Error Occur Cannot Delete",Toast.LENGTH_SHORT).show();
                }
            }

        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Admin_edit_products.this, "Delete Cancel..", Toast.LENGTH_SHORT).show();
            }
        }).setCancelable(false).show();





    }

    private void selectImage(){
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
                        admin_product_image.setImageBitmap(bp);
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
            e.getMessage();
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
           // e.printStackTrace();
            bp = Prevelent.current_admin_products.getBitmap();

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
    private void Update_Admin_product_Details(){


            getValues();
            if (ExceptionFound) {
                Toast.makeText(Admin_edit_products.this, "Please select an Image...", Toast.LENGTH_SHORT).show();
            } else {
                if (admin_product_name.getText().toString().isEmpty()) {
                    Toast.makeText(Admin_edit_products.this, "Please Enter product Name..", Toast.LENGTH_SHORT).show();
                } else if (admin_product_des.getText().toString().isEmpty()) {
                    Toast.makeText(Admin_edit_products.this, "Please Enter product description..", Toast.LENGTH_SHORT).show();
                } else if (admin_product_price.getText().toString().isEmpty()) {
                    Toast.makeText(Admin_edit_products.this, "Please Enter product Price..", Toast.LENGTH_SHORT).show();
                } else if (admin_product_counts.getText().toString().isEmpty()) {
                    Toast.makeText(Admin_edit_products.this, "Please Enter product Count..", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Add New Category Image").setMessage("Are you sure to add new category!").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean isNameAvailable = true;
                            dialog.show();
                            Cursor cu = db.Admin_Item_name_check();
                            while (cu.moveToNext()) {
                                if (cu.getString(1).toString().equals(admin_product_name.getText().toString())) {
                                    if (!cu.getString(0).toString().equals(Prevelent.current_admin_products.getProduct_id())) {
                                        isNameAvailable = false;
                                        break;
                                    }

                                }
                            }
                            if (isNameAvailable) {
                               boolean isAdd = db.Admin_update_product_info(Prevelent.current_admin_products.getProduct_id(),
                                    admin_product_name.getText().toString(), admin_product_des.getText().toString(),
                                       admin_product_price.getText().toString(), photo, admin_product_counts.getText().toString());


                                if (isAdd) {
                                    Toast.makeText(Admin_edit_products.this, "Item sucessfully Updated", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Admin_edit_products.this, AdminHomeActivity.class);
                                   startActivity(intent);
                                } else {
                                    Toast.makeText(Admin_edit_products.this, "Error!! Failed to Update...", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } else {
                                Toast.makeText(Admin_edit_products.this, "Sorry Name Already Exists..", Toast.LENGTH_SHORT).show();

                            }
                            dialog.dismiss();
                        }
                    }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(Admin_edit_products.this, "Update Cancel..", Toast.LENGTH_SHORT).show();
                        }
                    }).setCancelable(false).show();
                }

                /* */
            }


    }

}
