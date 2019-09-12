package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.shopcenter.Prevelent.Prevelent;

public class User_payment_Activity extends AppCompatActivity {
    private TextView payment_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment_);
        payment_view=findViewById(R.id.user_payment_total_view);

        payment_view.setText(Prevelent.current_user_payments.getTotal());
    }
}
