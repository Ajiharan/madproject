package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;

public class User_payment_Activity extends AppCompatActivity {
    private TextView payment_view;
    private EditText cus_name,cus_email,cus_zip,cus_card,cus_city;
    private DBHelper db;
    private Button make_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment_);
        payment_view=findViewById(R.id.user_payment_total_view);
        cus_name=findViewById(R.id.payment_full_name);
        cus_email=findViewById(R.id.payment_email_id);
        cus_zip=findViewById(R.id.payment_zipcode_id);
        cus_card=findViewById(R.id.payment_card_id);
        cus_city=findViewById(R.id.payment_city_name);

        make_payment=findViewById(R.id.cus_make_payment);
        payment_view.setText(Prevelent.current_user_payments.getTotal());
        db=new DBHelper(this);
        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_details();
            }
        });
    }

    private void insert_details() {


        if(cus_name.getText().toString().isEmpty()){

        }
        else if(cus_email.getText().toString().isEmpty()){

        }
        else if(cus_zip.getText().toString().isEmpty()){

        }
        else if(cus_card.getText().toString().isEmpty())
        {

        }
        else if(cus_city.getText().toString().isEmpty()){

        }
        else {
            boolean isUpdated = db.update_payment_details(Prevelent.currentUser.getId());
            if (isUpdated) {
                Intent intent = new Intent(User_payment_Activity.this, AppHomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Cannot update", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
