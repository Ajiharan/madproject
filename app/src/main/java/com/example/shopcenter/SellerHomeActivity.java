package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SellerHomeActivity extends AppCompatActivity {

    Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);

//        btn1.findViewById(R.id.btn_view);
//        btn2.findViewById(R.id.btn_add);
//        btn3.findViewById(R.id.btn_notification);
//        btn4.findViewById(R.id.btn_back);
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SellerHomeActivity.this,SellerAddProductActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SellerHomeActivity.this,SellerViewProductActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Intent intent = new Intent(SellerHomeActivity.this,SellerAddProductActivity.class);
//               // startActivity(intent);
//            }
//        });
//
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(SellerHomeActivity.this,AppHomeActivity.class);
//                startActivity(intent);
//            }
//        });
  }
}
