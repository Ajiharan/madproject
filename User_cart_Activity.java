package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class User_cart_Activity extends AppCompatActivity {
    private Button user_placeOrder_btn;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart_);
        user_placeOrder_btn=(Button)findViewById(R.id.User_cart_next_button) ;

        user_placeOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_cart_Activity.this,User_payment_Activity.class);
                startActivity(intent);
            }
        });
    }
}
