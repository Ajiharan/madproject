package com.example.shopcenter;

import android.content.Intent;
import android.os.Bundle;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Products;

import com.example.shopcenter.model.ProductsAdapter;
import com.example.shopcenter.model.UserProductsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AppHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab_btn;
    private String UserName;
    private CircleImageView user_profile_img;
    private TextView user_name;
    private DBHelper db;
    private RecyclerView recyclerView;
    private UserProductsAdapter itemAdapter;
    private TextView admin_name;
    private ArrayList<Products> productLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent=getIntent();
        UserName=intent.getStringExtra(Prevelent.INTENT_USER_NAME);
        setSupportActionBar(toolbar);

        db=new DBHelper(this);
        productLists=new ArrayList<>();
        recyclerView=findViewById(R.id.user_recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //.init(this);

        fab_btn =(FloatingActionButton) findViewById(R.id.fab);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AppHomeActivity.this,User_cart_Activity.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView=navigationView.getHeaderView(0);
        user_name=headerView.findViewById(R.id.user_profile_name);
        user_name.setText(Prevelent.currentOnlineUser.getName());
        user_profile_img=headerView.findViewById(R.id.user_profile_images);
        if(db.getImage(Prevelent.currentOnlineUser.getId()) !=null){
            user_profile_img.setImageBitmap(db.getImage(Prevelent.currentOnlineUser.getId()));
        }

        retrive_Admin_Products_Details();

    }
    private void retrive_Admin_Products_Details(){
        productLists=db.Retrive_admin_product_details();
        itemAdapter =new UserProductsAdapter(this,productLists);
        recyclerView.setAdapter(itemAdapter);

        itemAdapter.setOnItemClickListener(new UserProductsAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Prevelent.current_user_products=productLists.get(position);
                Intent intent=new Intent(AppHomeActivity.this,ProductDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_carts) {

        } else if (id == R.id.nav_search) {
            Intent intent=new Intent(AppHomeActivity.this,User_search_products.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_settings) {
            Intent intent=new Intent(AppHomeActivity.this,User_settings_Activity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_seller_btn){
            Intent intent=new Intent(AppHomeActivity.this,SellerRegisterActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_logout){
            Paper.book().destroy();
            Intent intent=new Intent(AppHomeActivity.this,LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_admin_home) {
            Intent intent=new Intent(AppHomeActivity.this,AdminHomeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
