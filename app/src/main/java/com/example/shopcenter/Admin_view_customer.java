package com.example.shopcenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.CustomerAdapter;
import com.example.shopcenter.model.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_view_customer extends AppCompatActivity {
   // private CircleImageView myimage;
    private DBHelper db;
    private RecyclerView recyclerView;
    private CustomerAdapter customerAdapter;
    private Button back_button;
    private ArrayList<User> userlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_customer);
        db=new DBHelper(this);
        userlists=new ArrayList<>();
        recyclerView=findViewById(R.id.admin_view_customer_details);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
            back_button=findViewById(R.id.admin_view_customer_back_btn);
            back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(Admin_view_customer.this,AdminHomeActivity.class);
                    startActivity(intent);
                }
            });
        retrive_customer_details();
    }

    private void retrive_customer_details(){
        userlists=db.Retrive_customer_allDetails();
        customerAdapter=new CustomerAdapter(this,userlists);
        recyclerView.setAdapter(customerAdapter);

        customerAdapter.setOnItemClickListener(new CustomerAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(final int position, View v) {
                Prevelent.currentOnlineUser=userlists.get(position);
                AlertDialog.Builder builder=new AlertDialog.Builder(Admin_view_customer.this);
                builder.setTitle("Delete Current Customer").setMessage("Are you sure want to delete current customer?").setPositiveButton("yes,delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isDeleted=db.admin_delete_current_customer(Prevelent.currentOnlineUser.getId());
                        if(isDeleted){
                            Toast.makeText(Admin_view_customer.this,"Sucessfully deleted",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Admin_view_customer.this,Admin_view_customer.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Admin_view_customer.this,"Error cannot delete..",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Admin_view_customer.this,"delete cancel",Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(false).show();

            }
        });

    }
}
