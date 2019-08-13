package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;

public class LoginActivity extends AppCompatActivity {
    private  TextView create_account_txt;
    private ProgressDialog loadingBar;
    private DBHelper db;
    private  CheckBox admin_chk;
    private  Button login_btn;
    private EditText user_mailid,user_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=new DBHelper(this);
        create_account_txt=(TextView) findViewById(R.id.login_create_link);
        admin_chk=(CheckBox)findViewById(R.id.admin_panel_chk);
        login_btn=(Button)findViewById(R.id.login_btn);
        loadingBar=new ProgressDialog(this);
        user_mailid=findViewById(R.id.login_mail_id_txt);
        user_password=findViewById(R.id.login_password_input);

        create_account_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create_account_txt.setPaintFlags(create_account_txt.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(admin_chk.isChecked()){
                    Intent intent=new Intent(LoginActivity.this,AdminHomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Check_user_login(user_mailid.getText().toString(),user_password.getText().toString());

                }
            }
        });

    }

    public void Check_user_login(String mailid,String password){

        if(mailid.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Some fields are empty",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait.....");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Cursor cu=db.Customer_mail_check(mailid);
            boolean IsLogin=false;
            while(cu.moveToNext()){
                if(mailid.equals(cu.getString(2)) && password.equals(cu.getString(3))){
                    IsLogin=true;
                    break;
                }
            }

            if(IsLogin){
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
                Toast.makeText(LoginActivity.this,"Sucessfully login...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginActivity.this,AppHomeActivity.class);
                startActivity(intent);
            }
            else{
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
                Toast.makeText(LoginActivity.this,"Incorrect email_id or password",Toast.LENGTH_SHORT).show();
                mThread.start();


            }
        }



    }
}
