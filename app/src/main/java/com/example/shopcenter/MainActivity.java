package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.User;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    DBHelper db;
    private Button register_button,login_button;
   // private ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);
     /*initialize when new DB version Entered*/   // Paper.book().destroy();
        db=new DBHelper(this);
        register_button=findViewById(R.id.main_join_now_btn);
        login_button=findViewById(R.id.main_login_btn);
        Prevelent.currentOnlineUser=new User();
        //loadingBar=new ProgressDialog(this);
        User_Rememberme();
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }
    public void User_Rememberme(){
        String userMailKey=Paper.book().read(Prevelent.USER_MAIL_ID);
        String userPasswordKey=Paper.book().read(Prevelent.USER_PASSWORD);
        if(userMailKey !="" && userPasswordKey !="") {
            if (!TextUtils.isEmpty(userMailKey) && !TextUtils.isEmpty(userPasswordKey)) {



                check_user(userMailKey);

            }
        }
    }

    public void check_user(String mailid){

        Cursor cu=db.Customer_mail_check(mailid);
        String Retrivemail_id="";
        String admin_mail_id=null;
        String user_name=null;
        String user_id=null;
        String user_password=null;
          while( cu.moveToNext()){
              if(mailid.equals(cu.getString(2))) {
                  user_id = cu.getString(0);
                  user_name = cu.getString(1);
                  Retrivemail_id = cu.getString(2);
                  user_password = cu.getString(3);
                  break;
              }
          }
          if(cu.moveToFirst()){
              admin_mail_id=cu.getString(2);
          }


          Prevelent.currentOnlineUser.setId(user_id);
          Prevelent.currentOnlineUser.setName(user_name);
          Prevelent.currentOnlineUser.setMail( Retrivemail_id);
          Prevelent.currentOnlineUser.setPassword(user_password);
          Prevelent.currentUser=Prevelent.currentOnlineUser;

        if(mailid.equals(admin_mail_id)) {

            Toast.makeText(MainActivity.this," You are Already  login "+user_name+"...",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, AdminHomeActivity.class);
            intent.putExtra(Prevelent.INTENT_USER_ID,user_id);
            intent.putExtra(Prevelent.INTENT_USER_NAME,user_name);

            startActivity(intent);

        }
        else {

            Toast.makeText(MainActivity.this,"You are Already login "+user_name+"...",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, AppHomeActivity.class);
            intent.putExtra(Prevelent.INTENT_USER_ID,user_id);
            intent.putExtra(Prevelent.INTENT_USER_NAME,user_name);
            startActivity(intent);
        }
    }
}
