package com.example.shopcenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;

public class SellerRegisterActivity extends AppCompatActivity {

    DBHelper db;

    EditText userName, eMail, password;
    Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_register);

        userName = findViewById(R.id.seller_username);
        eMail = findViewById(R.id.seller_email);
        password = findViewById(R.id.seller_password);
        Register = findViewById(R.id.seller_register);

        db = new DBHelper(this);

        check_seller_register();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (emptyCheck()) {
                    boolean check = db.check_insert_Seller(userName.getText().toString(), eMail.getText().toString());
                    if (check) {
                        boolean insert = db.insert_Seller(userName.getText().toString(), eMail.getText().toString(), password.getText().toString(), Prevelent.currentUser.getId());
                        if (insert) {
                            Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                            clearText();
                            Intent intent = new Intent(SellerRegisterActivity.this, SellerAddProductActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to register. Please try again", Toast.LENGTH_SHORT).show();
                            userName.isFocused();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You are already a seller!!", Toast.LENGTH_SHORT).show();
                        userName.isFocused();
                    }
                }
            }
        });
    }

    private void check_seller_register() {

        String isApproved=db.retrive_seller_info(Prevelent.currentUser.getId());

        if(isApproved.equals("0")){

            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Notification").setMessage("Dear customer please wait for admin approval").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(SellerRegisterActivity.this, AppHomeActivity.class);
                    startActivity(intent);
                }
            }).setCancelable(false).show();


        }
        else if(isApproved.equals("1")){
            Intent intent = new Intent(SellerRegisterActivity.this, SellerAddProductActivity.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "You are already registered ..", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean emptyCheck() {
        if (userName.getText().toString().isEmpty() || eMail.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
            if (userName.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter user name", Toast.LENGTH_SHORT).show();
                userName.isFocused();
            } else if (eMail.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Enter valid email", Toast.LENGTH_SHORT).show();
                eMail.isFocused();
            } else {
                Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
                password.isFocused();
            }
            return false;
        } else {
            return true;
        }
    }

    private void clearText() {
        userName.getText().clear();
        eMail.getText().clear();
        password.getText().clear();
        userName.isFocused();
    }
}
