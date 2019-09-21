package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.shopcenter.Adapters.cus_order_lis_Adapter;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.model.cus_orders;

import java.util.ArrayList;

public class Admin_view_customer_orders extends AppCompatActivity {

    private SwipeMenuListView listViews;
    private ArrayList<cus_orders> dataArrayList;
    private cus_order_lis_Adapter listAdapter;
    private cus_orders cu_orders;
    private DBHelper db;
    private String cus_id1;


    private Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_customer_orders);
        db=new DBHelper(this);
        listViews = findViewById(R.id.admin_view_customer_order_details1);
        dataArrayList= new ArrayList<>();

        Intent intent=getIntent();
        cus_id1=intent.getStringExtra("customer_id");
        back_button=findViewById(R.id.admin_view_customer_order_back_btn);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_view_customer_orders.this,Admin_view_customer.class);
                startActivity(intent);
            }
        });

        retrive_customer_order_details();
    }

    private void retrive_customer_order_details() {


        dataArrayList = db.retrive_user_order_details(cus_id1);


        if(dataArrayList.size() > 0) {
            listAdapter = new cus_order_lis_Adapter(this, dataArrayList);
            listViews.setAdapter(listAdapter);
        }

//       // listViews.setMenuCreator(creator);



    }

        SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // create "delete" item
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            // set item background
            deleteItem.setBackground(new ColorDrawable(Color.parseColor("#F45557")));
            // set item width
            deleteItem.setWidth(150);
            deleteItem.setTitle("x");
            deleteItem.setTitleColor(Color.WHITE);
            deleteItem.setTitleSize(15);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}
