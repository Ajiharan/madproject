package com.example.shopcenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Products;
import com.example.shopcenter.Adapters.ProductsAdapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private   ImageView admin_edit_current_product;
        private CircleImageView admin_profile;
        private DBHelper db;
        private TextView notification_count;
        private boolean isSearchAvailable=false;
       // private String AdminName;
        //private String AdminId;
       private RecyclerView recyclerView;
       private ProductsAdapter itemAdapter;
        private TextView admin_name;
        private EditText admin_search_products;
        private ArrayList<Products> productLists;
        private ImageView admin_view_noti_icon;
    private Bitmap bp=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent=getIntent();
        db=new DBHelper(this);
        productLists=new ArrayList<>();
        recyclerView=findViewById(R.id.Admin_recycler_view2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
       // AdminName=intent.getStringExtra(Prevelent.INTENT_USER_NAME);
        //AdminId=intent.getStringExtra(Prevelent.INTENT_USER_ID);
        //Toast.makeText(this,Prevelent.currentUser.getId(),Toast.LENGTH_SHORT).show();
        admin_search_products=findViewById(R.id.admin_search_product_name);
        notification_count=findViewById(R.id.admin_count_notification);

        admin_search_products.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                search_current_products();
            }

        });

        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab1);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent=new Intent(AdminHomeActivity.this,Admin_notification_Activity.class);
//               startActivity(intent);
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View headerView=navigationView.getHeaderView(0);
       admin_name=headerView.findViewById(R.id.admin_profile_name);
        admin_name.setText(Prevelent.currentOnlineUser.getName());
        admin_profile=headerView.findViewById(R.id.admin_profile_image);
        admin_view_noti_icon= findViewById(R.id.admin_notification_icons);
        admin_view_noti_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this,Admin_notification_Activity.class);
                startActivity(intent);
            }
        });

        set_profile();
        check_notification();
        retrive_Admin_Products_Details();

    }

    private void check_notification() {

        notification_count.setText(db.retrive_admin_noti_count_number());
        if(notification_count.getText().toString().equals("0")){
            notification_count.setVisibility(View.GONE);
        }
    }

    private void search_current_products() {
        productLists=db.Retrive_admin_search_product_details(admin_search_products.getText().toString());
        if(productLists.size() != 0) {
            isSearchAvailable=true;
            itemAdapter = new ProductsAdapter(this, productLists);
            recyclerView.setAdapter(itemAdapter);

            itemAdapter.setOnItemClickListener(new ProductsAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Prevelent.current_admin_products = productLists.get(position);
                    Intent intent = new Intent(AdminHomeActivity.this, Admin_edit_products.class);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position, View v) {
                    //Toast.makeText(AdminHomeActivity.this,"Check long pressed",Toast.LENGTH_LONG).show();
                }
            });

        }
        else{
            isSearchAvailable=false;
        }
    }
    private void retrive_Admin_Products_Details(){
        if(!isSearchAvailable) {
            productLists = db.Retrive_admin_product_details();
            itemAdapter = new ProductsAdapter(this, productLists);
            recyclerView.setAdapter(itemAdapter);

            itemAdapter.setOnItemClickListener(new ProductsAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Prevelent.current_admin_products = productLists.get(position);
                    Intent intent = new Intent(AdminHomeActivity.this, Admin_edit_products.class);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position, View v) {
                    //Toast.makeText(AdminHomeActivity.this,"Check long pressed",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
    private void set_profile(){
        Bitmap bitmap=db.getImage(Prevelent.currentOnlineUser.getId());

        if(bitmap != null){
            System.out.println("Bitss :"+bitmap);
            admin_profile.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.admin_nav_categories) {
            Intent intent=new Intent(AdminHomeActivity.this,Admin_add_new_category.class);
            startActivity(intent);

        } else if (id == R.id.admin_nav_logout) {
            Paper.book().destroy();
            Intent intent=new Intent(AdminHomeActivity.this,LoginActivity.class);
            startActivity(intent);

        } else if (id == R.id.admin_nav_Userhome) {

            Intent intent=new Intent(AdminHomeActivity.this,AppHomeActivity.class);
            //intent.putExtra(Prevelent.INTENT_USER_NAME,AdminName);
            startActivity(intent);

        } else if (id == R.id.admin_nav_customerDetails) {
            Intent intent=new Intent(AdminHomeActivity.this,Admin_view_customer.class);
            startActivity(intent);

        } else if (id == R.id.admin_nav_selle_Details) {
            Intent intent=new Intent(AdminHomeActivity.this,Seller_approval.class);
            startActivity(intent);
        } /*else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
