package com.example.shopcenter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.example.shopcenter.Adapters.cuslistAdapter;
import com.example.shopcenter.Adapters.sellerAdapter;
import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.model.User;
import com.example.shopcenter.model.sellers;

import java.util.ArrayList;

public class Seller_approval extends AppCompatActivity {
    private DBHelper db;

    private SwipeMenuListView listViews;
    private ArrayList<sellers> dataArrayList;
    private sellerAdapter listAdapter;
    private sellers datas;
    private Button back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_approval);
        db=new DBHelper(this);
        back_btn=findViewById(R.id.R_admin_view_customer_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Seller_approval.this,AdminHomeActivity.class);
                startActivity(intent);
            }
        });
        listViews = (SwipeMenuListView) findViewById(R.id.R_admin_view_customer_details1);
        dataArrayList = new ArrayList<>();

        retrive_customer_details();
    }

    private void retrive_customer_details() {

        dataArrayList=db.Retrive_seller_allDetails();
        listAdapter = new sellerAdapter(this, dataArrayList);
        listViews.setAdapter(listAdapter);

        listViews.setMenuCreator(creator);

        listViews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View view, final int position, long l) {
                AlertDialog.Builder builder =new AlertDialog.Builder(Seller_approval.this);
                builder.setTitle("Approval").setMessage("Do you want to approve current seller").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isUpdated=db.update_seller_approval(dataArrayList.get(position).getId());
                        if(isUpdated){
                            Toast.makeText(Seller_approval.this,"Seller approved",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Seller_approval.this,AdminHomeActivity.class);
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setCancelable(false).show();

            }
        });

        listViews.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(Seller_approval.this,"Sucessfully deleted",Toast.LENGTH_SHORT).show();
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

    private void delete_cur_customer(int position) {
        boolean isDeleted=db.admin_delete_seller(dataArrayList.get(position).getId());

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
