package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private  TextView create_account_txt;
    private  CheckBox admin_chk;
    private  Button login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        create_account_txt=(TextView) findViewById(R.id.login_create_link);
        admin_chk=(CheckBox)findViewById(R.id.admin_panel_chk);
        login_btn=(Button)findViewById(R.id.login_btn);
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
                    Intent intent=new Intent(LoginActivity.this,AppHomeActivity.class);
                    startActivity(intent);
                }
            }
        });

    }
}
