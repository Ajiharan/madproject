package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;

import dmax.dialog.SpotsDialog;

public class Admin_edit_categories extends AppCompatActivity {
    private AlertDialog dialog;
    private ImageView admin_category_edit_image;
    private EditText admin_edit_category_product_names;
    private DBHelper db;
    private Button admin_back,admin_delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_categories);
        admin_category_edit_image=findViewById(R.id.admin_edit_category_image);
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
        Toast.makeText(Admin_edit_categories.this,Prevelent.Currentcategories.getId(),Toast.LENGTH_LONG).show();
        admin_category_edit_image.setImageBitmap(Prevelent.Currentcategories.getBitmap());
        admin_edit_category_product_names.setText(Prevelent.Currentcategories.getName());
    }
}
