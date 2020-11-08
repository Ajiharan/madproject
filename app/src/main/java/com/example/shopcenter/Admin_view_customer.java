package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.model.User;
import com.example.shopcenter.Adapters.cuslistAdapter;

import java.util.ArrayList;

public class Admin_view_customer extends AppCompatActivity {
    private Button back_button;
    private DBHelper db;
    private SwipeMenuListView listViews;
    private ArrayList<User> dataArrayList;
    private cuslistAdapter listAdapter;
    private User datas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_customer);
        db=new DBHelper(this);

        listViews = (SwipeMenuListView) findViewById(R.id.admin_view_customer_details1);
        dataArrayList = new ArrayList<>();


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


        dataArrayList=db.Retrive_customer_allDetails();
        listAdapter = new cuslistAdapter(this, dataArrayList);
        listViews.setAdapter(listAdapter);

        listViews.setMenuCreator(creator);

        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {
                Intent intent=new Intent(Admin_view_customer.this,Admin_view_customer_orders.class);

                User curr_user=dataArrayList.get(i);
                intent.putExtra("customer_id",curr_user.getId());

                //Prevelent.currentUser=curr_user;
                startActivity(intent);
            }
        });

        listViews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(Admin_view_customer.this,"Sucessfully deleted",Toast.LENGTH_SHORT).show();
                        delete_cur_customer(position);
                        dataArrayList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });



    }

    private void delete_cur_customer(int position){
        User user=new User();
        user=dataArrayList.get(position);
        db.admin_delete_current_customer(user.getId());

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
            deleteItem.setIcon(R.drawable.user_admin_delete_icon);
            deleteItem.setTitleColor(Color.WHITE);

            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}
