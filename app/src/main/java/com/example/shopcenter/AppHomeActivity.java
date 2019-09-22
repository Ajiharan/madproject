package com.example.shopcenter;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopcenter.Adapters.ProductsAdapter;
import com.example.shopcenter.Database.DBHelper;
import com.example.shopcenter.Prevelent.Prevelent;
import com.example.shopcenter.model.Cart;
import com.example.shopcenter.model.Products;

import com.example.shopcenter.Adapters.UserProductsAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class AppHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ViewFlipper imgBanner;
    private FloatingActionButton fab_btn;
    private String UserName;
    private CircleImageView user_profile_img;
    private TextView user_name;
    private boolean isSearchAvailable=false;
    private DBHelper db;
    private TextView cart_number;
    private EditText search_result;
    private RecyclerView recyclerView;
    private UserProductsAdapter itemAdapter;
    private TextView admin_name;

    private ImageView admin_cart_view;
    private ArrayList<Products> productLists;
    private ArrayList<Cart> carttLists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        Intent intent=getIntent();
        UserName=intent.getStringExtra(Prevelent.INTENT_USER_NAME);
        search_result=findViewById(R.id.user_search_products);
        search_result.addTextChangedListener(new TextWatcher() {
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
        imgBanner=findViewById(R.id.img_banner);
        int sliders[]={R.drawable.slider2,R.drawable.slider3,R.drawable.slider,R.drawable.slider4, R.drawable.slider5};
        for(int slide:sliders){
            bannerFlipper(slide);
        }
        carttLists=new ArrayList<>();
        db=new DBHelper(this);


        productLists=new ArrayList<>();
        recyclerView=findViewById(R.id.user_recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //.init(this);
        admin_cart_view=findViewById(R.id.admin_cart_icons);
        admin_cart_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(carttLists.size()==0){
                    Toast.makeText(AppHomeActivity.this,"Cart is empty",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(AppHomeActivity.this, User_cart_Activity.class);
                    startActivity(intent);
                }
            }
        });
//        fab_btn =(FloatingActionButton) findViewById(R.id.fab);
//        fab_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(AppHomeActivity.this,User_cart_Activity.class);
//                startActivity(intent);
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
        user_name=headerView.findViewById(R.id.user_profile_name);
        user_name.setText(Prevelent.currentOnlineUser.getName());
        user_profile_img=headerView.findViewById(R.id.user_profile_images);

        if(db.getImage(Prevelent.currentOnlineUser.getId()) !=null){
            user_profile_img.setImageBitmap(db.getImage(Prevelent.currentOnlineUser.getId()));
        }

        retrive_Admin_Products_Details();
        check_notification();

    }

    private void search_current_products() {
        productLists=db.Retrive_admin_search_product_details(search_result.getText().toString());
        if(productLists.size() != 0) {
            isSearchAvailable=true;
            itemAdapter = new UserProductsAdapter(this, productLists);
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
                    //Toast.makeText(AdminHomeActivity.this,"Check long pressed",Toast.LENGTH_LONG).show();
                }
            });

        }
        else{
            isSearchAvailable=false;
        }
    }

    private void check_notification() {
        carttLists = db.retrive_user_cart_details(Prevelent.currentUser.getId());
        if(carttLists.size()==0){
            boolean isDeleted= db.Delete_admin_notification_counts(Prevelent.currentUser.getId());
        }

        String not_count=db.retrive_cus_noti_count_number(Prevelent.currentUser.getId());

        cart_number=findViewById(R.id.cart_count_notification);
        cart_number.setText(not_count);
        if(cart_number.getText().toString().equals("0")){
            cart_number.setVisibility(View.GONE);
        }

    }

    private void bannerFlipper(int image){
        ImageView imgView=new ImageView(this);
        imgView.setImageResource(image);
        imgBanner.addView(imgView);
        imgBanner.setFlipInterval(6000);
        imgBanner.setAutoStart(true);
        imgBanner.setInAnimation(this,android.R.anim.fade_in);
        imgBanner.setOutAnimation(this,android.R.anim.fade_out);
     }
    private void retrive_Admin_Products_Details(){
        if(!isSearchAvailable) {
            productLists = db.Retrive_admin_product_details();
            itemAdapter = new UserProductsAdapter(this, productLists);
            recyclerView.setAdapter(itemAdapter);

            itemAdapter.setOnItemClickListener(new UserProductsAdapter.ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Prevelent.current_user_products = productLists.get(position);
                    Intent intent = new Intent(AppHomeActivity.this, ProductDetailActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(int position, View v) {

                }
            });
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
            Intent intent=new Intent(AppHomeActivity.this,User_cart_Activity.class);
            startActivity(intent);
        }else if (id == R.id.nav_settings) {
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

            boolean isTrue=false;
            if (Prevelent.currentUser .getId().equals("1")) {
                isTrue=true;
            }
            else{
                isTrue=false;
            }
            if(isTrue) {
                Intent intent = new Intent(AppHomeActivity.this, AdminHomeActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"You are not allowed",Toast.LENGTH_SHORT).show();
            }
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
