package com.example.shopcenter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Cart;
import com.example.shopcenter.model.cartAdapter;

import java.util.ArrayList;

public class User_cart_Activity extends AppCompatActivity {
    private Button user_placeOrder_btn;
    private RecyclerView recyclerView;
    private cartAdapter itemAdapter;
    private DBHelper db;
    private TextView view_total;
    private ArrayList<Cart> carttLists;
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart_);
        user_placeOrder_btn=(Button)findViewById(R.id.User_cart_next_button) ;
        db=new DBHelper(this);
        carttLists=new ArrayList<>();
        recyclerView=findViewById(R.id.user_cart_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        view_total=findViewById(R.id.total_amount_display);

        user_placeOrder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(User_cart_Activity.this,User_payment_Activity.class);
                startActivity(intent);
            }
        });

        retrive_user_cart_Details();
    }
    //    @Override
//    public void onBackPressed() {
//        /*Intent a = new Intent(Intent.ACTION_MAIN);
//        a.addCategory(Intent.CATEGORY_HOME);
//        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(a);*/
//    }
    private void retrive_user_cart_Details() {
        carttLists = db.retrive_user_cart_details(Prevelent.currentUser.getId());
        itemAdapter = new cartAdapter(this, carttLists);
        recyclerView.setAdapter(itemAdapter);

        int total=0;
        for(int i =0; i < carttLists.size(); i ++){
            total += Integer.parseInt(carttLists.get(i).getTotal());
        }
        view_total.setText("Total: $"+String.valueOf(total));

        itemAdapter.setOnItemClickListener(new cartAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                boolean isDeleted=db.user_delete_cart(carttLists.get(position).getId());
                if(isDeleted){
                    Toast.makeText(User_cart_Activity.this,"item removed",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(User_cart_Activity.this,User_cart_Activity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(User_cart_Activity.this,"Error cannot remove",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }
}
