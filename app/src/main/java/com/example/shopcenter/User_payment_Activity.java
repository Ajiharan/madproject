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
import com.example.shopcenter.model.payments;

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
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(cus_name.getText().toString().isEmpty()){
            Toast.makeText(this,"Name is empty",Toast.LENGTH_SHORT).show();
        }
        else if(cus_email.getText().toString().isEmpty()){
            Toast.makeText(this,"Mail_id is empty",Toast.LENGTH_SHORT).show();

        }
        else if(cus_zip.getText().toString().isEmpty()){
            Toast.makeText(this,"Zip id is empty",Toast.LENGTH_SHORT).show();
        }
        else if(cus_card.getText().toString().isEmpty())
        {
            Toast.makeText(this,"card id is empty",Toast.LENGTH_SHORT).show();
        }
        else if(cus_city.getText().toString().isEmpty()){
            Toast.makeText(this," city is empty",Toast.LENGTH_SHORT).show();
        }
        else {
            if(!cus_email.getText().toString().trim().matches(emailPattern)) {
                Toast.makeText(this,"Invalid email_id",Toast.LENGTH_SHORT).show();
            }
            else {
                boolean isUpdated = db.update_carts_details(Prevelent.currentUser.getId());
                boolean isAdded = db.Customer_insert_order_details(payment_view.getText().toString(), Prevelent.currentUser.getId());
                payments mypay = new payments();
                mypay.setName(cus_name.getText().toString());
                mypay.setEmail_id(cus_email.getText().toString());
                mypay.setZipcode(cus_zip.getText().toString());
                mypay.setCardNo(cus_card.getText().toString());
                mypay.setCity(cus_city.getText().toString());
                mypay.setTotal(payment_view.getText().toString());
                mypay.setOrder_id(db.get_order_id());

                boolean isPaid=db.Customer_insert_payment_details(mypay);
                boolean isNotified=db.insert_user_order_notification_details(Prevelent.currentUser.getId());

                if (isUpdated && isAdded && isPaid && isNotified) {
                    boolean isDeleted= db.Delete_admin_notification_counts(Prevelent.currentUser.getId());
                    Toast.makeText(this, "Paid Sucessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(User_payment_Activity.this, AppHomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Cannot update", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
