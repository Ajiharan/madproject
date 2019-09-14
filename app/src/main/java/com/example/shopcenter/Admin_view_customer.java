package com.example.shopcenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.CustomerAdapter;
import com.example.shopcenter.model.User;
import com.example.shopcenter.model.cuslistAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_view_customer extends AppCompatActivity {

    private DBHelper db;


    private Button back_button;


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

        listViews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        delete_cur_customer(position);
                        dataArrayList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        Toast.makeText(Admin_view_customer.this,"Sucessfully deleted",Toast.LENGTH_SHORT).show();

                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });



    }

    private void delete_cur_customer(int position){
        Prevelent.currentOnlineUser=dataArrayList.get(position);
        db.admin_delete_current_customer(Prevelent.currentOnlineUser.getId());

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
            deleteItem.setTitleSize(25);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}
