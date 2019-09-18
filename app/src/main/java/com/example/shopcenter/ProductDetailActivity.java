package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Cart;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView product_image;
    private TextView product_name,product_des,product_price;
    private ElegantNumberButton cart_button;
    private Button add_cart_btn;
    private DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        db=new DBHelper(this);
        product_image=findViewById(R.id.product_detail_image);
        product_name=findViewById(R.id.product_detail_name);
        product_des=findViewById(R.id.product_detail_des);
        product_price=findViewById(R.id.product_detail_price);
        cart_button=findViewById(R.id.user_cart_count);
        add_cart_btn=findViewById(R.id.btn_addtocart);
        add_cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_cart_details();
            }
        });
        initialize_details();
    }

    private void insert_cart_details() {
        int total=Integer.parseInt(product_price.getText().toString())*Integer.parseInt(cart_button.getNumber());

        Cart cart=new Cart(product_name.getText().toString(),product_des.getText().toString(),product_price.getText().toString(),
                String.valueOf(total),Prevelent.currentUser.getId(),Prevelent.current_user_products.getBitmap(),cart_button.getNumber().toString(),Prevelent.current_user_products.getProduct_id());
       // Toast.makeText(ProductDetailActivity.this,Prevelent.currentUser.getId(),Toast.LENGTH_SHORT).show();

        boolean isInserted=db.User_insert_cart_details(cart);
        boolean isAdd=db.insert_user_notification_details(Prevelent.currentUser.getId());

        if(isInserted && isAdd){
             Toast.makeText(ProductDetailActivity.this,"sucessfully inserted",Toast.LENGTH_SHORT).show();
             Intent intent=new Intent(ProductDetailActivity.this,AppHomeActivity.class);
             startActivity(intent);

        }
        else{
            Toast.makeText(ProductDetailActivity.this,"Error cannot insert!!",Toast.LENGTH_SHORT).show();
        }

    }

    private void initialize_details() {

        try {
            product_image.setImageBitmap(Prevelent.current_user_products.getBitmap());
            product_price.setText(Prevelent.current_user_products.getProduct_price());
            product_name.setText(Prevelent.current_user_products.getProduct_name());
            product_des.setText(Prevelent.current_user_products.getProduct_desc());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}
