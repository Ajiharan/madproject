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
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Cart;
import com.example.shopcenter.Adapters.cartAdapter;
import com.example.shopcenter.model.payments;

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
                carttLists = db.retrive_user_cart_details(Prevelent.currentUser.getId());
                if(carttLists.size() > 0) {
                    payments cpay = new payments();

                    cpay.setTotal(view_total.getText().toString());

                    Prevelent.current_user_payments = cpay;

                    Intent intent = new Intent(User_cart_Activity.this, User_payment_Activity.class);
                    startActivity(intent);
                }
                else{

                    Toast.makeText(User_cart_Activity.this,"Your current cart is empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        retrive_user_cart_Details();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),AppHomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
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
            public void onItemClick(final int position, View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(User_cart_Activity.this);
                builder.setTitle("Delete Item").setMessage("do you want to delete this item?").setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean isDeleted=db.user_delete_cart(carttLists.get(position).getId());
                        if(isDeleted){
                            String nid= db.retrive_cus_notification_id_count_number(Prevelent.currentUser.getId());
                            db.Delete_admin_one_notification_counts(Prevelent.currentUser.getId(),nid);
                            Toast.makeText(User_cart_Activity.this,"item removed",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(User_cart_Activity.this,User_cart_Activity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(User_cart_Activity.this,"Error cannot remove",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setCancelable(false).show();

            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });
    }
}
