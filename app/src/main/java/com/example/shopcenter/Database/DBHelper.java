package com.example.shopcenter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.example.shopcenter.model.Cart;
import com.example.shopcenter.model.CategoryItems;
import com.example.shopcenter.model.Products;
import com.example.shopcenter.model.User;
import com.example.shopcenter.model.payments;
import com.example.shopcenter.model.cus_orders;
import com.example.shopcenter.model.sellers;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import io.paperdb.Paper;


public class DBHelper extends SQLiteOpenHelper {

    private String num="0";



    public static final String          DATABASE_NAME="OnlineShop.db";
    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 12);


    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Paper.book().destroy();
        String CUSTOMER_CREATE_ENTRIES="CREATE TABLE "+CustomerMaster.Customers.TABLE_NAME+"("+
                CustomerMaster.Customers.COLUMN_NAME_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Customers.COLUMN_NAME_NAME+" TEXT,"+CustomerMaster.Customers.COLUMN_NAME_EMAIL+
                " TEXT,"+CustomerMaster.Customers.COLUMN_NAME_PASSWORD +" TEXT)";

        String CUSTOMER_PROFILE_CREATES_ENTRIES="CREATE TABLE "+CustomerMaster.Profile.TABLE_NAME+"("+
                CustomerMaster.Profile.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Profile.COLUMN_NAME_IMAGE+" BLOB,"+
                CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID+" INTEGER,CONSTRAINT fk_cus FOREIGN KEY ("+
                CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID+") REFERENCES "+ CustomerMaster.Customers.TABLE_NAME+"("+
                CustomerMaster.Customers.COLUMN_NAME_ID+") ON DELETE CASCADE ON UPDATE CASCADE)";

        String ADMIN_CATEGORY_DETAILS_ENTRIES="CREATE TABLE "+ CustomerMaster.ProductCategory.TABLE_NAME+"("+
                CustomerMaster.ProductCategory.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE+" LONGBLOB,"+
                CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME+" TEXT)";

        String ADMIN_PRODUCT_DETAILS_ENTRIES="CREATE TABLE "+ CustomerMaster.ProductItems.TABLE_NAME +"("+
                CustomerMaster.ProductItems.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME + " TEXT,"+
                CustomerMaster.ProductItems.COLUMN_NAME_COUNT +" INTEGER,"+
                CustomerMaster.ProductItems.COLUMN_NAME_DESCRIPTION +" TEXT,"+
                CustomerMaster.ProductItems.COLUMN_NAME_PRICE + " TEXT,"+
                CustomerMaster.ProductItems.COLUMN_NAME_PRODUCTIMAGE + " LONGBLOB,"+
                CustomerMaster.ProductItems.COLUMN_NAME_CATEGORY_NAME +" TEXT," +
                CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY+" INTEGER,CONSTRAINT fk_pro_cat FOREIGN KEY ("+
                CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY + ") REFERENCES "+ CustomerMaster.ProductCategory.TABLE_NAME+"("+
                CustomerMaster.ProductCategory.COLUMN_NAME_ID +") ON DELETE CASCADE ON UPDATE CASCADE)";

        String CUSTOMER_CART_CREATES_ENTRIES="CREATE TABLE "+CustomerMaster.UserCart.TABLE_NAME+"("+
                CustomerMaster.UserCart.COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.UserCart.COLUMN_NAME +" TEXT,"+
                CustomerMaster.UserCart.COLUMN_COUNT +" TEXT,"+
                CustomerMaster.UserCart.COLUMN_DES +" TEXT,"+
                CustomerMaster.UserCart.COLUMN_PRICE +" TEXT,"+
                CustomerMaster.UserCart.COLUMN_TOTAL +" TEXT,"+
                CustomerMaster.UserCart.COLUMN_IMAGE +" LONGBLOB,"+
                CustomerMaster.UserCart.COLUMN_FOREIGN1 + " INTEGER,"+
                CustomerMaster.UserCart.COLUMN_FOREIGN + " INTEGER,"+
                CustomerMaster.UserCart.COLUMN_DEFAULT_CHECK + " TEXT DEFAULT "+num+","+
                " FOREIGN KEY ("+
                CustomerMaster.UserCart.COLUMN_FOREIGN1+") REFERENCES "+ CustomerMaster.ProductItems.TABLE_NAME+"("+
                CustomerMaster.ProductItems.COLUMN_NAME_ID +") ON DELETE CASCADE ON UPDATE CASCADE, "+
                "FOREIGN KEY ("+
                CustomerMaster.UserCart.COLUMN_FOREIGN+") REFERENCES "+ CustomerMaster.Customers.TABLE_NAME+"("+
                CustomerMaster.Customers.COLUMN_NAME_ID+") ON DELETE CASCADE ON UPDATE CASCADE)";

        String ORDER_DETAILS_ENTRIES="CREATE TABLE " + CustomerMaster.Cus_Order.TABLE_NAME +"("+
                CustomerMaster.Cus_Order.COLUMN_NAME_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Cus_Order.COLUMN_NAME_AMOUNT + " TEXT,"+
                CustomerMaster.Cus_Order.COLUMN_NAME_CUS_ID + " INTEGER,"+
                CustomerMaster.Cus_Order.COLUMN_NAME_DELIVERY+ " TEXT DEFAULT "+ num +","+
                " FOREIGN KEY (" +
                CustomerMaster.Cus_Order.COLUMN_NAME_CUS_ID+") REFERENCES "+CustomerMaster.Customers.TABLE_NAME+"("+
                CustomerMaster.Customers.COLUMN_NAME_ID+") ON DELETE CASCADE ON UPDATE CASCADE)";


        String PAYMENT_DETAILS_ENTRIES="CREATE TABLE "+ CustomerMaster.PaymentDetails.TABLE_NAME +"("+

                CustomerMaster.PaymentDetails.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME+ " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_CARD + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_CITY + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_MAIL + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_ZIP +  " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_AMOUNT+ " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_CUS_ID + " INTEGER,"+
                CustomerMaster.PaymentDetails.COLUMN_NAME_ORDER + " INTEGER,"+

                " FOREIGN KEY ("+CustomerMaster.PaymentDetails.COLUMN_NAME_CUS_ID +") REFERENCES " + CustomerMaster.Customers.TABLE_NAME+
                "("+CustomerMaster.Customers.COLUMN_NAME_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE, "+

                " FOREIGN KEY ("+CustomerMaster.PaymentDetails.COLUMN_NAME_ORDER +") REFERENCES " + CustomerMaster.Cus_Order.TABLE_NAME+
                "("+CustomerMaster.Cus_Order.COLUMN_NAME_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE)";


        String USER_CART_NOTIFICATION_ENTRIES="CREATE TABLE "+CustomerMaster.User_cart_notification.TABLE_NAME + "("+
                CustomerMaster.User_cart_notification.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.User_cart_notification.COLUMN_CUS_ID + " INTEGER,"+
                " FOREIGN KEY (" + CustomerMaster.User_cart_notification.COLUMN_CUS_ID +") REFERENCES " + CustomerMaster.Customers.TABLE_NAME+
                "("+CustomerMaster.Customers.COLUMN_NAME_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE)";

        String USER_ORDER_NOTIFICATION_ENTRIES="CREATE TABLE "+CustomerMaster.User_order_notification.TABLE_NAME + "("+
                CustomerMaster.User_order_notification.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.User_order_notification.COLUMN_CUS_ID + " INTEGER,"+
                " FOREIGN KEY (" + CustomerMaster.User_order_notification.COLUMN_CUS_ID +") REFERENCES " + CustomerMaster.Customers.TABLE_NAME+
                "("+CustomerMaster.Customers.COLUMN_NAME_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE)";



        //      String SELLER_PRODUCT_DETAILS_ENTRIES="CREATE TABLE "+ CustomerMaster.SellerProductItems.TABLE_NAME +"("+
//                CustomerMaster.SellerProductItems.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
//                CustomerMaster.SellerProductItems.COLUMN_NAME_PRODUCT_NAME + " TEXT,"+
//                CustomerMaster.SellerProductItems.COLUMN_NAME_COUNT +" INTEGER,"+
//                CustomerMaster.SellerProductItems.COLUMN_NAME_DESCRIPTION +" TEXT,"+
//                CustomerMaster.SellerProductItems.COLUMN_NAME_PRICE + " TEXT,"+
//                CustomerMaster.SellerProductItems.COLUMN_NAME_PRODUCTIMAGE + " LONGBLOB)";

        String SELLER_DETAILS_ENTRIES="CREATE TABLE "+ CustomerMaster.Seller.TABLE_NAME +"("+
                CustomerMaster.Seller.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Seller.COLUMN_NAME_USER_NAME+" TEXT,"+
                CustomerMaster.Seller.COLUMN_NAME_EMAIL + " TEXT,"+
                CustomerMaster.Seller.COLUMN_NAME_PASSWORD + " TEXT," +

                CustomerMaster.Seller.COLUMN_NAME_CHECK_CUSTOMER + " TEXT DEFAULT "+num+","+
                CustomerMaster.Seller.COLUMN_NAME_CUSTOMER + " INTEGER,"+
                " FOREIGN KEY (" + CustomerMaster.Seller.COLUMN_NAME_CUSTOMER +") REFERENCES " + CustomerMaster.Customers.TABLE_NAME+
                "("+CustomerMaster.Customers.COLUMN_NAME_ID+ ") ON DELETE CASCADE ON UPDATE CASCADE)";



        sqLiteDatabase.execSQL(CUSTOMER_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(CUSTOMER_PROFILE_CREATES_ENTRIES);
        sqLiteDatabase.execSQL(ADMIN_CATEGORY_DETAILS_ENTRIES);
        sqLiteDatabase.execSQL(ADMIN_PRODUCT_DETAILS_ENTRIES);
        sqLiteDatabase.execSQL(CUSTOMER_CART_CREATES_ENTRIES);
        sqLiteDatabase.execSQL(ORDER_DETAILS_ENTRIES);
        sqLiteDatabase.execSQL(PAYMENT_DETAILS_ENTRIES);
        sqLiteDatabase.execSQL(USER_CART_NOTIFICATION_ENTRIES);
        sqLiteDatabase.execSQL(USER_ORDER_NOTIFICATION_ENTRIES);
        sqLiteDatabase.execSQL(SELLER_DETAILS_ENTRIES);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CustomerMaster.User_order_notification.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CustomerMaster.User_cart_notification.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CustomerMaster.PaymentDetails.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+CustomerMaster.Cus_Order.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.UserCart.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.ProductItems.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.ProductCategory.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Profile.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Customers.TABLE_NAME);



        onCreate(sqLiteDatabase);
    }


    public boolean insert_Seller(String username,String mail,String passw,String id){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Seller.COLUMN_NAME_USER_NAME,username);
        values.put(CustomerMaster.Seller.COLUMN_NAME_EMAIL,mail);
        values.put(CustomerMaster.Seller.COLUMN_NAME_PASSWORD,passw);
        values.put(CustomerMaster.Seller.COLUMN_NAME_CUSTOMER,id);


        long rowId=db.insert(CustomerMaster.Seller.TABLE_NAME,null,values);
        return rowId != -1;

    }

    public boolean check_insert_Seller(String username,String mail){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from "+CustomerMaster.Seller.TABLE_NAME+" WHERE "+CustomerMaster.Seller.COLUMN_NAME_USER_NAME + "= ? AND " +CustomerMaster.Seller.COLUMN_NAME_EMAIL + " = ?";
        String []selectionArgs={username,mail};
        Cursor cursor = db.rawQuery(sql,selectionArgs);
        if(cursor.getCount()==0){
            return true;
        }else{
            return false;
        }
    }

    public boolean Seller_insert_product(String prodname,String proddesc,String prodprice,Integer prodcounts){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_PRODUCT_NAME,prodname);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_DESCRIPTION,proddesc);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_PRICE,prodprice);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_COUNT,prodcounts);

        long rowId=db.insert(CustomerMaster.SellerProductItems.TABLE_NAME,null,values);
        return rowId != -1;

    }

    public Cursor Seller_view_product(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from "+CustomerMaster.SellerProductItems.TABLE_NAME ;
        Cursor cursor = db.rawQuery(sql,null);
        return cursor;
    }

    public boolean Seller_update_product(String prodname,String proddesc,String prodprice,Integer prodcounts,Integer prodid){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_PRODUCT_NAME,prodname);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_DESCRIPTION,proddesc);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_PRICE,prodprice);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_COUNT,prodcounts);
        values.put(CustomerMaster.SellerProductItems.COLUMN_NAME_ID,prodid);

        long rowId=db.update(CustomerMaster.SellerProductItems.TABLE_NAME, values,  CustomerMaster.SellerProductItems.COLUMN_NAME_ID+"=?",new String[]{String.valueOf(prodid)});
        if(rowId==-1){
            return false;
        }else {
            return  true;
        }
    }



    public String  retrive_seller_info(String id){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.Seller.TABLE_NAME + " WHERE "+
                CustomerMaster.Seller.COLUMN_NAME_CUSTOMER + " = ?";
        String []selectionArgs={id};
        Cursor cu=db.rawQuery(sql,selectionArgs);
        String value="99";
        while(cu.moveToNext()){
            value=cu.getString(4);
        }
        return value;
    }

    public Boolean deleteData(String mail){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(CustomerMaster.Customers.TABLE_NAME,CustomerMaster.Customers.COLUMN_NAME_EMAIL+"=?",new String[]{mail});
        if(result==-1){
            return false;
        }else {
            return  true;
        }
    }

    public boolean updateUser(String id,String fullna){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CustomerMaster.Customers.COLUMN_NAME_NAME,fullna);

        String selection = CustomerMaster.Customers.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {id};


        long result = db.update(CustomerMaster.Customers.TABLE_NAME,contentValues,selection,selectionArgs);
        return(result > 0);
    }





    public boolean insert_user_notification_details(String cus_id){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.User_cart_notification.COLUMN_CUS_ID,cus_id);
        long rowId=db.insert(CustomerMaster.User_cart_notification.TABLE_NAME,null,values);
        return rowId != -1;
    }

    public boolean insert_user_order_notification_details(String cus_id){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.User_order_notification.COLUMN_CUS_ID,cus_id);
        long rowId=db.insert(CustomerMaster.User_order_notification.TABLE_NAME,null,values);
        return rowId != -1;
    }
    public boolean User_insert_cart_details(Cart cart){
        SQLiteDatabase db=getWritableDatabase();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        cart.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] photo = stream.toByteArray();


        ContentValues values=new ContentValues();
        values.put(CustomerMaster.UserCart.COLUMN_NAME,cart.getName());
        values.put(CustomerMaster.UserCart.COLUMN_COUNT,cart.getCount());
        values.put(CustomerMaster.UserCart.COLUMN_DES,cart.getDes());
        values.put(CustomerMaster.UserCart.COLUMN_PRICE,cart.getPrice());
        values.put(CustomerMaster.UserCart.COLUMN_TOTAL,cart.getTotal());
        values.put(CustomerMaster.UserCart.COLUMN_IMAGE,photo);
        values.put(CustomerMaster.UserCart.COLUMN_FOREIGN1,cart.getProduct_id());
        values.put(CustomerMaster.UserCart.COLUMN_FOREIGN,cart.getFid());

        long rowId=db.insert(CustomerMaster.UserCart.TABLE_NAME,null,values);
        return rowId != -1;
    }
    public boolean Customer_insert_order_details(String amount,String cus_id){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Cus_Order.COLUMN_NAME_AMOUNT,amount);
        values.put(CustomerMaster.Cus_Order.COLUMN_NAME_CUS_ID,cus_id);
        long rowId=db.insert(CustomerMaster.Cus_Order.TABLE_NAME,null,values);


        return rowId != -1;
    }

    public boolean Customer_insert_payment_details(payments pay){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME,pay.getName());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_CARD,pay.getCardNo());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_CITY,pay.getCity());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_MAIL,pay.getEmail_id());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_ZIP,pay.getZipcode());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_AMOUNT,pay.getTotal());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_CUS_ID,pay.getCus_id());
        values.put(CustomerMaster.PaymentDetails.COLUMN_NAME_ORDER,pay.getOrder_id());
        long rowId=db.insert(CustomerMaster.PaymentDetails.TABLE_NAME,null,values);


        return rowId != -1;
    }

    public boolean Customer_insert_data(String name,String emailid,String password){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Customers.COLUMN_NAME_NAME,name);
        values.put(CustomerMaster.Customers.COLUMN_NAME_EMAIL,emailid);
        values.put(CustomerMaster.Customers.COLUMN_NAME_PASSWORD,password);

        long rowId=db.insert(CustomerMaster.Customers.TABLE_NAME,null,values);


        return rowId != -1;

    }

    public boolean Customer_Profile_insert(String cus_foreign_id,byte[] image){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Profile.COLUMN_NAME_IMAGE,image);
        values.put(CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID,cus_foreign_id);
        long rowId=db.insert(CustomerMaster.Profile.TABLE_NAME,null,values);
        //System.out.println("Rows:"+rowId);
        return rowId != -1;


    }

    public String retrive_cus_noti_count_number(String cuid){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.User_cart_notification.TABLE_NAME + " WHERE "+
                CustomerMaster.User_cart_notification.COLUMN_CUS_ID + " = ?";
        String[] selectionArgs = {cuid};


        Cursor cursor=db.rawQuery(sql,selectionArgs);
        int count=0;
        while (cursor.moveToNext()){
            count ++;
        }
        cursor.close();
        return String.valueOf(count);
    }

    public String retrive_admin_noti_count_number(){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.User_order_notification.TABLE_NAME;



        Cursor cursor=db.rawQuery(sql,null);
        int count=0;
        while (cursor.moveToNext()){
            count ++;
        }
        cursor.close();
        return String.valueOf(count);
    }

    public String get_order_id(){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.Cus_Order.TABLE_NAME;
        Cursor cu=db.rawQuery(sql,null);
        String id="0";
        while(cu.moveToNext()){
            id=cu.getString(0);
        }
        cu.close();

        return id;
    }

    public boolean delete_notification(String id){
        SQLiteDatabase db=getReadableDatabase();
        String selection = CustomerMaster.PaymentDetails.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {id};
        int rowsAffected= db.delete(CustomerMaster.PaymentDetails.TABLE_NAME,selection,selectionArgs);
        return rowsAffected > 0;


    }

    public Cursor retrive_seller_details(){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.ProductCategory.TABLE_NAME;

        Cursor cursor=db.rawQuery(sql,null);
        return cursor;
    }

    public String retrive_cus_notification_id_count_number(String cuid){
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.User_cart_notification.TABLE_NAME + " WHERE "+
                CustomerMaster.User_cart_notification.COLUMN_CUS_ID + " = ?";
        String[] selectionArgs = {cuid};


        Cursor cursor=db.rawQuery(sql,selectionArgs);
        String count="0";
        while (cursor.moveToNext()){
            count= cursor.getString(0);
        }
        cursor.close();
        return count;
    }

    public ArrayList<payments> retrive_admin_notification_details(){
        ArrayList<payments>  list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String sql="SELECT * FROM "+CustomerMaster.PaymentDetails.TABLE_NAME;
        Cursor cursor=db.rawQuery(sql,null);

        String id;
        String name;
        String zip;
        String email;
        String card;
        String address;
        String amount;
        String or_id;

        while(cursor.moveToNext()){
            id=cursor.getString(0);
            name= cursor.getString(1);
            card=cursor.getString(2);
            address=cursor.getString(3);
            email=cursor.getString(4);
            zip=cursor.getString(5);
            amount=cursor.getString(6);
            or_id=cursor.getString(8);
            payments pay1=new payments();
            pay1.setName(name);
            pay1.setCardNo(card);
            pay1.setCity(address);
            pay1.setEmail_id(email);
            pay1.setZipcode(zip);
            pay1.setTotal(amount);
            pay1.setId(id);
            pay1.setOrder_id(or_id);
            list.add(pay1);

        }

        cursor.close();
        return list;
    }
    public ArrayList<cus_orders>  retrive_user_order_details(String cuid){
        ArrayList<cus_orders> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+ CustomerMaster.Cus_Order.TABLE_NAME + " WHERE "+
                CustomerMaster.Cus_Order.COLUMN_NAME_CUS_ID + " = ?";
        String[] selectionArgs = {cuid};

        Cursor cu=db.rawQuery(sql,selectionArgs);



        String price;
        String del;
        String id;


        while(cu.moveToNext()){

            id=cu.getString(0);

            del=cu.getString(3);
            price=cu.getString(1);


            cus_orders cOrder=new cus_orders();
            cOrder.setOrder_id(id);
            cOrder.setTot(price);
            cOrder.setCheck_order(del);

            list.add(cOrder);

        }
        cu.close();
        return list;
    }

    public ArrayList<Cart> retrive_user_cart_details(String cuid){

        ArrayList<Cart> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+ CustomerMaster.UserCart.TABLE_NAME + " WHERE "+
                CustomerMaster.UserCart.COLUMN_FOREIGN + "= ? AND "+ CustomerMaster.UserCart.COLUMN_DEFAULT_CHECK + "= ?";
        String []selectionArgs={cuid,num};
        Cursor cu=db.rawQuery(sql,selectionArgs);
        byte[] image;
        String name;
        String count;
        String price;
        String desc;
        String id;
        String total;
        String ppid;
        String fcuid;
        while(cu.moveToNext()){
            id=cu.getString(0);
            name=cu.getString(1);
            count=cu.getString(2);
            desc=cu.getString(3);
            price=cu.getString(4);
            total=cu.getString(5);
            image=cu.getBlob(6);
            ppid=cu.getString(7);
            fcuid=cu.getString(8);
            Bitmap bitmap;

            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            Cart cart=new Cart(name,desc,price,total,fcuid,bitmap,count,ppid);
            cart.setId(id);
            list.add(cart);

        }
        cu.close();

        return list;
    }
    public ArrayList Retrive_admin_product_details(){
        ArrayList<Products> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+ CustomerMaster.ProductItems.TABLE_NAME;

        Cursor cu=db.rawQuery(sql,null);
        byte[] image;
        String name;
        String count;
        String price;
        String desc;
        String id;
        String fid;

        while(cu.moveToNext()){

            id=cu.getString(0);
            name=cu.getString(1);
            count=cu.getString(2);
            desc=cu.getString(3);
            price=cu.getString(4);
            image=cu.getBlob(5);
            fid=cu.getString(7);
            Bitmap bitmap;

            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            Products products=new Products(id,name,bitmap,fid,count,desc,price);
            list.add(products);

        }
        cu.close();

        return list;

    }
    public ArrayList Retrive_admin_search_product_details(String pname){
        ArrayList<Products> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+ CustomerMaster.ProductItems.TABLE_NAME + " WHERE "+ CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME
                +" LIKE ? OR " +CustomerMaster.ProductItems.COLUMN_NAME_CATEGORY_NAME + " LIKE ?" ;
        String []selectionArgs={"%" + pname + "%",pname +"%"};

        Cursor cu=db.rawQuery(sql,selectionArgs);
        byte[] image;
        String name;
        String count;
        String price;
        String desc;
        String id;
        String fid;

        while(cu.moveToNext()){

            id=cu.getString(0);
            name=cu.getString(1);
            count=cu.getString(2);
            desc=cu.getString(3);
            price=cu.getString(4);
            image=cu.getBlob(5);
            fid=cu.getString(7);
            Bitmap bitmap;

            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            Products products=new Products(id,name,bitmap,fid,count,desc,price);
            list.add(products);

        }
        cu.close();

        return list;

    }
    public  ArrayList Retrive_seller_allDetails(){
        ArrayList<sellers> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+CustomerMaster.Seller.TABLE_NAME+ " WHERE "+
                CustomerMaster.Seller.COLUMN_NAME_CHECK_CUSTOMER + "= ?";
        String []selectionArgs1={"0"};
        Cursor cu=db.rawQuery(sql,selectionArgs1);
        int i =0;
        while (cu.moveToNext()){

                String id = cu.getString(0);
                String seller_name=cu.getString(1);
                String check_value = cu.getString(4);
                String cu_id = cu.getString(5);


                String sql1 = "SELECT * FROM " + CustomerMaster.Profile.TABLE_NAME + " WHERE " + CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID + " = "
                        + cu_id;
                SQLiteDatabase db1 = getReadableDatabase();
                Cursor cu1 = db1.rawQuery(sql1, null);
                byte[] image;
                Bitmap bitmap = null;
                while (cu1.moveToNext()) {
                    System.out.println("cus:"+cu_id);
                    image = cu1.getBlob(1);
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                }
                cu1.close();
               sellers seller1=new sellers();
                seller1.setId(id);
                seller1.setName(seller_name);
                seller1.setDvalue(check_value);
                seller1.setBitmap( bitmap);

                list.add(seller1);

        }
        cu.close();
        return list;
    }

    public  ArrayList Retrive_customer_allDetails(){
        ArrayList<User> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+CustomerMaster.Customers.TABLE_NAME;
        Cursor cu=db.rawQuery(sql,null);
        int i =0;
        while (cu.moveToNext()){
            if(i != 0) {
                String id = cu.getString(0);
                String name = cu.getString(1);
                String email = cu.getString(2);
                String password = cu.getString(3);

                String sql1 = "SELECT * FROM " + CustomerMaster.Profile.TABLE_NAME + " WHERE " + CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID + " = "
                        + id;
                SQLiteDatabase db1 = getReadableDatabase();
                Cursor cu1 = db1.rawQuery(sql1, null);
                byte[] image;
                Bitmap bitmap = null;
                while (cu1.moveToNext()) {
                    image = cu1.getBlob(1);
                    bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                }
                cu1.close();
                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setMail(email);
                user.setPassword(password);
                user.setBitmap(bitmap);
                list.add(user);
            }
            i++;
        }
        cu.close();
        return list;
    }
    public ArrayList Retrive_Product_Category_Details(){
        ArrayList<CategoryItems> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+ CustomerMaster.ProductCategory.TABLE_NAME;

        Cursor cu=db.rawQuery(sql,null);
        byte[] image;
        while(cu.moveToNext()){
            String id=cu.getString(0);
            image=cu.getBlob(1);
            String name=cu.getString(2);
            Bitmap bitmap;

            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);
            CategoryItems items=new CategoryItems(name,bitmap);
            items.setId(id);
            list.add(items);
        }
        cu.close();

        return list;

    }

    public boolean admin_delete_current_customer(String id){

        SQLiteDatabase db = getReadableDatabase();
        String selection = CustomerMaster.Customers.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {id};
        int rowsAffected= db.delete(CustomerMaster.Customers.TABLE_NAME,selection,selectionArgs);
        return rowsAffected > 0;

    }

    public boolean Delete_admin_notification_counts(String cus_id){
        SQLiteDatabase db=getReadableDatabase();
        String selection=CustomerMaster.User_cart_notification.COLUMN_CUS_ID + " = ?";
        String[] selectionArgs = {cus_id};
        int rowsAffected=db.delete(CustomerMaster.User_cart_notification.TABLE_NAME,selection,selectionArgs);
        return rowsAffected > 0;
    }
    public boolean Delete_admin_one_notification_counts(String cus_id,String no_id){
        SQLiteDatabase db=getReadableDatabase();
        String selection=CustomerMaster.User_cart_notification.COLUMN_CUS_ID + " = ? and "+ CustomerMaster.User_cart_notification.COLUMN_ID + " = ?";
        String[] selectionArgs = {cus_id, no_id};
        int rowsAffected=db.delete(CustomerMaster.User_cart_notification.TABLE_NAME,selection,selectionArgs);
        return rowsAffected > 0;
    }

    public boolean Admin_delete_current_product(String id){
        try{
            SQLiteDatabase db=getReadableDatabase();
            String selection=CustomerMaster.ProductItems.COLUMN_NAME_ID + " = ?";
            String[] selectionArgs = {id};
             int rowsAffected=db.delete(CustomerMaster.ProductItems.TABLE_NAME,selection,selectionArgs);
            return rowsAffected > 0;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean Admin_delete_category(String id){

        try{
            SQLiteDatabase db=getReadableDatabase();
            String selection=CustomerMaster.ProductCategory.COLUMN_NAME_ID + " = ?";
            String[] selectionArgs = {id};
            db.delete(CustomerMaster.ProductCategory.TABLE_NAME,selection,selectionArgs);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void delete_admin_notification(){
        SQLiteDatabase db=getReadableDatabase();
        db.delete(CustomerMaster.User_order_notification.TABLE_NAME,null,null);

    }
    public boolean user_delete_cart(String id){
        SQLiteDatabase db=getReadableDatabase();
        String selection=CustomerMaster.UserCart.COLUMN_ID + " = ?";
        String[] selectionArgs = {id};
        int rowDeleted=db.delete(CustomerMaster.UserCart.TABLE_NAME,selection,selectionArgs);
        return rowDeleted > 0;
    }

    public boolean admin_delete_seller(String id){
        SQLiteDatabase db=getReadableDatabase();
        String selection=CustomerMaster.Seller.COLUMN_NAME_ID+ " = ?";
        String[] selectionArgs = {id};
        int rowDeleted=db.delete(CustomerMaster.Seller.TABLE_NAME,selection,selectionArgs);
        return rowDeleted > 0;
    }
    public boolean update_seller_approval(String id){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Seller.COLUMN_NAME_CHECK_CUSTOMER,"1");

        String selection= CustomerMaster.Seller.COLUMN_NAME_ID +" = ?";
        String[] selectionArgs = {id};
        int count=db.update(CustomerMaster.Seller.TABLE_NAME,values,selection,selectionArgs);
        return (count > 0);
    }
    public boolean Admin_add_Category_Details(byte[] image,String name){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE,image);
        values.put(CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME,name);
        long rowId=db.insert(CustomerMaster.ProductCategory.TABLE_NAME,null,values);
        return rowId != -1;

    }
    public boolean Admin_add_product_Details(String name,String desc,String price,String count,byte[] image,String fid,String cname){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME,name);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_COUNT,count);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_DESCRIPTION,desc);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRICE,price);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRODUCTIMAGE,image);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_CATEGORY_NAME,cname);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY,fid);


        long rowId=db.insert(CustomerMaster.ProductItems.TABLE_NAME,null,values);

        return rowId != -1;
    }
    public boolean Admin_update_category_Info(String id,String name,byte[]image){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE,image);
        values.put(CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME,name);
        String selection= CustomerMaster.ProductCategory.COLUMN_NAME_ID +" = ?";
        String[] selectionArgs = {id};
        int count=db.update(CustomerMaster.ProductCategory.TABLE_NAME,values,selection,selectionArgs);

        ContentValues values1 =new ContentValues();
        values1.put(CustomerMaster.ProductItems.COLUMN_NAME_CATEGORY_NAME,name);
        String selection1=CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY + " = ?";
        String[] selectionArgs1 = {id};
        db.update(CustomerMaster.ProductItems.TABLE_NAME,values1,selection1,selectionArgs1);


        return count > 0;

    }
    public boolean update_carts_details(String num){

        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.UserCart.COLUMN_DEFAULT_CHECK,"1");
        String selection=CustomerMaster.UserCart.COLUMN_FOREIGN + " = ?";
        String[] selectionArgs = {num};
        int counts =db.update(CustomerMaster.UserCart.TABLE_NAME,values,selection,selectionArgs);
        return counts > 0;

    }

    public boolean update_cus_orders(String id){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Cus_Order.COLUMN_NAME_DELIVERY,"1");
        String selection=CustomerMaster.Cus_Order.COLUMN_NAME_ID + " =?";
        String[] selectionArgs = {id};
        int counts=db.update(CustomerMaster.Cus_Order.TABLE_NAME,values,selection,selectionArgs);
        return counts > 0;

    }
    public boolean Admin_update_product_info(String id,String name,String des,String price,byte[]image,String count){
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME, name);
            values.put(CustomerMaster.ProductItems.COLUMN_NAME_COUNT, count);
            values.put(CustomerMaster.ProductItems.COLUMN_NAME_DESCRIPTION, des);
            values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRICE, price);
            values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRODUCTIMAGE, image);
            String selection = CustomerMaster.ProductItems.COLUMN_NAME_ID + " = ?";
            String[] selectionArgs = {id};

            int counts = db.update(CustomerMaster.ProductItems.TABLE_NAME, values, selection, selectionArgs);
            return counts > 0;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Cursor  Customer_mail_check(String emailid){
        SQLiteDatabase db=getReadableDatabase();
        String[] projection = {CustomerMaster.Customers.COLUMN_NAME_ID, CustomerMaster.Customers.COLUMN_NAME_NAME, CustomerMaster.Customers.COLUMN_NAME_EMAIL,
                CustomerMaster.Customers.COLUMN_NAME_PASSWORD};

        return db.query(CustomerMaster.Customers.TABLE_NAME,projection, null,null,null,null,null);

    }

    public Cursor Admin_product_name_check(){
        SQLiteDatabase db=getReadableDatabase();
        String[] projection = {CustomerMaster.ProductCategory.COLUMN_NAME_ID, CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE, CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME};

        return db.query(CustomerMaster.ProductCategory.TABLE_NAME,projection,null,null,null,null,null);
    }


    public Cursor Admin_Item_name_check(){
        SQLiteDatabase db=getReadableDatabase();
        String[] projection = {CustomerMaster.ProductItems.COLUMN_NAME_ID, CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME,
                CustomerMaster.ProductItems.COLUMN_NAME_COUNT, CustomerMaster.ProductItems.COLUMN_NAME_DESCRIPTION, CustomerMaster.ProductItems.COLUMN_NAME_PRICE,
                CustomerMaster.ProductItems.COLUMN_NAME_PRODUCTIMAGE, CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY};

        return db.query(CustomerMaster.ProductItems.TABLE_NAME,projection,null,null,null,null,null);
    }

    public Bitmap getImage(String user_id){
        SQLiteDatabase db=getReadableDatabase();
        Bitmap bitmap=null;
        // System.out.println("Bits :"+bitmap);
        //String orderby= CustomerMaster.Profile.COLUMN_NAME_ID+" DESC";
        String sql="SELECT * FROM "+ CustomerMaster.Profile.TABLE_NAME+" WHERE "+ CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID +"=?";
        Cursor cu=db.rawQuery(sql,new String[]{user_id});
        // System.out.println("Cus :"+user_id);
        byte[] image;
        int t=-1;
        while(cu.moveToNext()){
            image=cu.getBlob(1);
            t++;
            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            // System.out.println("Bit :"+user_id);

        }
        cu.close();
        System.out.println("rrow :"+t);
        return bitmap;
    }
}
