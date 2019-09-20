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
import com.example.shopcenter.Adapters.NotificationAdapter;
import com.example.shopcenter.model.payments;

import java.util.ArrayList;

public class Admin_notification_Activity extends AppCompatActivity {
    private Button admin_back;

    private SwipeMenuListView listViews;
    private ArrayList<payments> dataArrayList;
    private NotificationAdapter listAdapter;
    private payments pay_noti;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_notification_);

        admin_back=findViewById(R.id.admin_view_notification_back_btn);
        db=new DBHelper(this);
        listViews = findViewById(R.id.admin_admin_notification1);
        dataArrayList= new ArrayList<>();



        admin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Admin_notification_Activity.this,AdminHomeActivity.class);
                startActivity(intent);
            }
        });
        retrive_customer_details();
    }

    private void retrive_customer_details() {
        db.delete_admin_notification();

        dataArrayList=db.retrive_admin_notification_details();
        listAdapter = new NotificationAdapter(this, dataArrayList);
        listViews.setAdapter(listAdapter);

        listViews.setMenuCreator(creator);

        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, int i, long l) {

            }
        });

        listViews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:

                        boolean isDeleted=db.delete_notification(dataArrayList.get(position).getId());
                        boolean isUpdated=db.update_cus_orders(dataArrayList.get(position).getOrder_id());

                        dataArrayList.remove(position);
                        listAdapter.notifyDataSetChanged();
                        if(isUpdated && isDeleted) {
                            Toast.makeText(Admin_notification_Activity.this, "Sucessfully deleted", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        break;
                }
                return false;
            }
        });

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
            deleteItem.setWidth(350);


            deleteItem.setIcon(R.drawable.delete_icon2);
            deleteItem.setTitleColor(Color.WHITE);
            deleteItem.setTitleSize(20);
            // add to menu
            menu.addMenuItem(deleteItem);
        }
    };
}
