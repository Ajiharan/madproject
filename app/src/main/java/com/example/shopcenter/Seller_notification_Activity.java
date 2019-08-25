package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.shopcenter.adapter.Product_Approval_Notification_List_Adapter;
import com.example.shopcenter.model.Product;

import java.util.ArrayList;

public class Seller_notification_Activity extends AppCompatActivity {
    ArrayList<Product> dataModels;
    ListView listView;
    private static Product_Approval_Notification_List_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_notification_);

        listView=(ListView)findViewById(R.id.product_approval_list);

        dataModels= new ArrayList<>();

        dataModels.add(new Product("Apple Pie", "Android 1.0", "1","September 23, Approved"));
        dataModels.add(new Product("Banana Bread", "Android 1.1", "2","February 9, Reject"));
        dataModels.add(new Product("Cupcake", "Android 1.5", "3","April 27, Approved"));
        dataModels.add(new Product("Donut","Android 1.6","4","September 15, Reject"));
        dataModels.add(new Product("Eclair", "Android 2.0", "5","October 26, Reject"));
        dataModels.add(new Product("Froyo", "Android 2.2", "8","May 20, Approved"));
        dataModels.add(new Product("Gingerbread", "Android 2.3", "9","December 6, Reject"));
        dataModels.add(new Product("Honeycomb","Android 3.0","11","February 22, Approved"));
        dataModels.add(new Product("Ice Cream Sandwich", "Android 4.0", "14","October 18, Approved"));
        dataModels.add(new Product("Jelly Bean", "Android 4.2", "16","July 9, Approved"));
        dataModels.add(new Product("Kitkat", "Android 4.4", "19","October 31, Reject"));
        dataModels.add(new Product("Lollipop","Android 5.0","21","November 12, Approved"));
        dataModels.add(new Product("Marshmallow", "Android 6.0", "23","October 5, Approved"));

        adapter= new Product_Approval_Notification_List_Adapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product dataModel= dataModels.get(position);

            }
        });
    }
}
