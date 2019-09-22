package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;

import java.io.ByteArrayOutputStream;

public class SellerAddProductActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private EditText name,des,price,count;
    private ImageView img;
    private Spinner spinner;
    private Button back,add_product;
    private Bitmap bp=null;
    private boolean ExceptionFound=false;
    private static final int REQUEST_CODE_GALLERY=2;
    private byte[] photo=null;
    private String num_value="0";
    private String[] seller_cat_num;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_product);
        name=findViewById(R.id.S_product_name);
        des=findViewById(R.id.S_product_description);
        price=findViewById(R.id.S_product_price);
        count=findViewById(R.id.S_product_counts);
        spinner=findViewById(R.id.seller_select_category);
        back=findViewById(R.id.seller_add_product_back_btn);
        add_product=findViewById(R.id.seller_add_new_product);
        img=findViewById(R.id.S_select_product_image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        db=new DBHelper(this);


        Cursor cu=db.retrive_seller_details();

        int j = 0;
        while(cu.moveToNext()){
            j++;
        }
        cu.close();
         String[] seller_category=new String[j];
         seller_cat_num=new String[j];


        int i = 0;
        cu=db.retrive_seller_details();
        while(cu.moveToNext()){
            seller_cat_num[i]=cu.getString(0);
            seller_category[i]=cu.getString(2);
           i++;
        }


        spinner.setOnItemSelectedListener(SellerAddProductActivity.this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,seller_category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerAddProductActivity.this,AppHomeActivity.class);
                startActivity(intent);
            }
        });

        add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_admin_Category_Details();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(this,String.valueOf(i),Toast.LENGTH_SHORT).show();
        num_value=seller_cat_num[i];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                        img.setImageBitmap(bp);
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
    private void Add_admin_Category_Details() {
        getValues();
        if (ExceptionFound) {
            Toast.makeText(SellerAddProductActivity.this, "Please select an Image...", Toast.LENGTH_SHORT).show();
        } else {
            if (name.getText().toString().isEmpty()) {
                Toast.makeText(SellerAddProductActivity.this, "Please Enter Product Name..", Toast.LENGTH_SHORT).show();
            } else if (des.getText().toString().isEmpty()) {
                Toast.makeText(SellerAddProductActivity.this, "Please give Product Description..", Toast.LENGTH_SHORT).show();
            } else if (price.getText().toString().isEmpty()) {
                Toast.makeText(SellerAddProductActivity.this, "Please Enter Product Price..", Toast.LENGTH_SHORT).show();
            } else if (count.getText().toString().isEmpty()) {
                Toast.makeText(SellerAddProductActivity.this, "Please give Product Count..", Toast.LENGTH_SHORT).show();
            } else {
                boolean isAdd = db.Admin_add_product_Details(name.getText().toString(),des.getText().toString(),
                        price.getText().toString(),count.getText().toString(),photo,num_value,spinner.getSelectedItem().toString());
                if(isAdd){
                    Toast.makeText(SellerAddProductActivity.this, "sucessfully added..", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SellerAddProductActivity.this, "cannot added..", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}
