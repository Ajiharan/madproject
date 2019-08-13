package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;

import org.w3c.dom.Text;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {
    private DBHelper db;
   // private  AlertDialog dialog;
    private TextView login_txt;
    private Button user_register_btn;
    private EditText user_name,user_email,user_password;
    private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db=new DBHelper(this);
        user_name=findViewById(R.id.register_username_txt);
        user_email=findViewById(R.id.register_email_txt);
        user_password=findViewById(R.id.register_password_txt);
        user_register_btn=findViewById(R.id.register_btn);
        login_txt=(TextView)findViewById(R.id.goto_login);
        loadingBar=new ProgressDialog(this);
         //dialog = new SpotsDialog(this);


        login_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login_txt.setPaintFlags(login_txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        user_register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert_Data(user_name.getText().toString(),user_email.getText().toString(),user_password.getText().toString());
            }
        });
    }

    public void insert_Data(String name,String email,String password){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(name.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please Enter Your Name",Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please Enter Your EmailId",Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
        }

        else {
            if(email.trim().matches(emailPattern)) {
                loadingBar.setTitle("Create Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
                // dialog.show();


                checkMailId(name, email, password);
            }
            else{
                Toast.makeText(RegisterActivity.this,"Invalid emailId",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void checkMailId(String name,String email,String password){
        Cursor cu=db.Customer_mail_check(email);
        boolean IsMailAvailable=true;
        while(cu.moveToNext()){
            if(email.equals(cu.getString(2))){
                IsMailAvailable=false;

            }
        }
        if(IsMailAvailable){

            boolean isDataInserted = db.Customer_insert_data(name,email,password);
            if (isDataInserted) {

                Toast.makeText(RegisterActivity.this, "Congraulation Your Account has been Created", Toast.LENGTH_SHORT).show();
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        loadingBar.dismiss();
                    }
                };
                mThread.start();

                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            } else {

                Toast.makeText(RegisterActivity.this, "Cannot Create Account", Toast.LENGTH_SHORT).show();
                Thread mThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        loadingBar.dismiss();
                    }
                };
                mThread.start();
            }
        }
        else{

           // Toast.makeText(RegisterActivity.this, "Sorry!!  Mail id is Already in use.", Toast.LENGTH_SHORT).show();
            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    loadingBar.dismiss();
                }
            };
            mThread.start();

            Toast.makeText(RegisterActivity.this, "Sorry!!  Mail id is Already in use.", Toast.LENGTH_SHORT).show();


        }
    }
}
