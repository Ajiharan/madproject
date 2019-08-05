package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminProductCategoryActivity extends AppCompatActivity {
   // private ImageView my_lap_view;
    private  Button add_new_product_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_category);
   //    my_lap_view=(ImageView)findViewById(R.id.admin_category_product_btn);
        add_new_product_btn=(Button)findViewById(R.id.admin_add_category_btn);
     /*   my_lap_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminProductCategoryActivity.this,Admin_add_products.class);
                startActivity(intent);
            }
        });*/

       add_new_product_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminProductCategoryActivity.this,Admin_add_new_category.class);
                startActivity(intent);
            }
        });

    }
}
