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
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.User;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private  TextView create_account_txt;
    private ProgressDialog loadingBar;
    private DBHelper db;
    private  CheckBox admin_chk,reminder_chk;
    private  Button login_btn;
    private EditText user_mailid,user_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db=new DBHelper(this);
        create_account_txt=(TextView) findViewById(R.id.login_create_link);
        admin_chk=(CheckBox)findViewById(R.id.admin_panel_chk);
        Prevelent.currentOnlineUser=new User();
        reminder_chk=findViewById(R.id.remember_me_chkb);
        login_btn=(Button)findViewById(R.id.login_btn);
        loadingBar=new ProgressDialog(this);
        user_mailid=findViewById(R.id.login_mail_id_txt);
        user_password=findViewById(R.id.login_password_input);
        Paper.init(this);
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
                    Check_admin_login(user_mailid.getText().toString(),user_password.getText().toString());

                }
                else{
                    Check_user_login(user_mailid.getText().toString(),user_password.getText().toString());

                }
            }
        });

    }
    public void Check_admin_login(String mailid,String password){
        if(mailid.isEmpty() || password.isEmpty()){
            Toast.makeText(LoginActivity.this,"Some fields are empty",Toast.LENGTH_SHORT).show();
        }
        else{

            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait.....");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            Cursor cu=db.Customer_mail_check(mailid);
            String admin_mailid=null;
            String admin_password=null;
            String admin_name=null;
            String user_id=null;
            boolean IsLogin=false;

          if(cu.moveToFirst()){
              user_id=cu.getString(0);
              admin_name=cu.getString(1);
              admin_mailid=cu.getString(2);
              admin_password=cu.getString(3);
              Prevelent.currentOnlineUser.setId(user_id);
              Prevelent.currentOnlineUser.setName(admin_name);
              Prevelent.currentOnlineUser.setMail( admin_mailid);
              Prevelent.currentOnlineUser.setPassword(admin_password);
              Prevelent.currentUser=Prevelent.currentOnlineUser;

          }




            if(admin_mailid.equals(mailid) && admin_password.equals(password)){
                IsLogin=true;
                if(reminder_chk.isChecked()){
                    Paper.book().write(Prevelent.USER_MAIL_ID,mailid);
                    Paper.book().write(Prevelent.USER_PASSWORD,password);
                    //Paper.book().write(Prevelent.USER_TYPE,"admin");
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
                Toast.makeText(LoginActivity.this,"Welcome "+admin_name+"...",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginActivity.this,AdminHomeActivity.class);
                intent.putExtra(Prevelent.INTENT_USER_ID,user_id);
                intent.putExtra(Prevelent.INTENT_USER_NAME,admin_name);
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
            String user_name=null;
            String user_id=null;
            String user_mail=null;
            String user_password=null;
            while(cu.moveToNext()){
                if(mailid.equals(cu.getString(2)) && password.equals(cu.getString(3))){
                    user_name=cu.getString(1);
                    user_id=cu.getString(0);
                    user_mail=cu.getString(2);
                    user_password=cu.getString(3);

                    Prevelent.currentOnlineUser.setId(user_id);
                    Prevelent.currentOnlineUser.setName(user_name);
                    Prevelent.currentOnlineUser.setMail(user_mail);
                    Prevelent.currentOnlineUser.setPassword(user_password);

                    Prevelent.currentUser=Prevelent.currentOnlineUser;



                    if(reminder_chk.isChecked()){
                        Paper.book().write(Prevelent.USER_MAIL_ID,mailid);
                        Paper.book().write(Prevelent.USER_PASSWORD,password);
                       // Paper.book().write(Prevelent.USER_TYPE,"user");
                    }
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
                Toast.makeText(LoginActivity.this,"Welcome "+user_name+"....",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LoginActivity.this,AppHomeActivity.class);
                intent.putExtra(Prevelent.INTENT_USER_ID,user_id);
                intent.putExtra(Prevelent.INTENT_USER_NAME,user_name);
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
