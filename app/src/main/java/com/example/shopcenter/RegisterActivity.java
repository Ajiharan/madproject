package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.shopcenter.Database.DBHelper;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    private DBHelper db;
    private TextView login_txt;
    private Button user_register_btn;
    private EditText user_name,user_email,user_password;
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

            }
        });
    }
}
