package com.example.shopcenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Products;
import com.example.shopcenter.model.ProductsAdapter;
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

public class AdminHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private   ImageView admin_edit_current_product;
        private CircleImageView admin_profile;
        private DBHelper db;
       // private String AdminName;
        //private String AdminId;
       private RecyclerView recyclerView;
       private ProductsAdapter itemAdapter;
        private TextView admin_name;
        private ArrayList<Products> productLists;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
       // AdminName=intent.getStringExtra(Prevelent.INTENT_USER_NAME);
        //AdminId=intent.getStringExtra(Prevelent.INTENT_USER_ID);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(AdminHomeActivity.this,Admin_notification_Activity.class);
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
       admin_name=headerView.findViewById(R.id.admin_profile_name);
        admin_name.setText(Prevelent.currentOnlineUser.getName());
        admin_profile=headerView.findViewById(R.id.admin_profile_image);

        set_profile();

        retrive_Admin_Products_Details();

    }

    private void retrive_Admin_Products_Details(){
        productLists=db.Retrive_admin_product_details();
        itemAdapter =new ProductsAdapter(this,productLists);
        recyclerView.setAdapter(itemAdapter);

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

        } /*else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
