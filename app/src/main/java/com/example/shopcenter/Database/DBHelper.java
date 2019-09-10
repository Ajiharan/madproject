package com.example.shopcenter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.shopcenter.model.CategoryItems;
import com.example.shopcenter.model.Products;
import com.example.shopcenter.model.User;

import java.util.ArrayList;

import io.paperdb.Paper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="OnlineShop.db";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CUSTOMER_CREATE_ENTRIES="CREATE TABLE "+CustomerMaster.Customers.TABLE_NAME+"("+
                CustomerMaster.Customers.COLUMN_NAME_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Customers.COLUMN_NAME_NAME+" TEXT,"+CustomerMaster.Customers.COLUMN_NAME_EMAIL+
                " TEXT,"+CustomerMaster.Customers.COLUMN_NAME_PASSWORD +" TEXT)";

        String CUSTOMER_PROFILE_CREATES_ENTRIES="CREATE TABLE "+CustomerMaster.Profile.TABLE_NAME+"("+
                CustomerMaster.Profile.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Profile.COLUMN_NAME_IMAGE+" BLOB,"+ CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID+" INTEGER,CONSTRAINT fk_cus FOREIGN KEY ("+
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
                CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY+" INTEGER,CONSTRAINT fk_pro_cat FOREIGN KEY ("+
                CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY + ") REFERENCES "+ CustomerMaster.ProductCategory.TABLE_NAME+"("+
                CustomerMaster.ProductCategory.COLUMN_NAME_ID +") ON DELETE CASCADE ON UPDATE CASCADE)";

        sqLiteDatabase.execSQL(CUSTOMER_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(CUSTOMER_PROFILE_CREATES_ENTRIES);
        sqLiteDatabase.execSQL(ADMIN_CATEGORY_DETAILS_ENTRIES);
        sqLiteDatabase.execSQL(ADMIN_PRODUCT_DETAILS_ENTRIES);

        String PAYMENT_DETAILS="CREATE TABLE "+ CustomerMaster.PaymentDetails.TABLE_NAME +"("+
                CustomerMaster.PaymentDetails.COLUMN_NAME + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_mail + " TEXT PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.PaymentDetails.COLUMN_Zip + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_card + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_city + " TEXT,"+
                CustomerMaster.PaymentDetails.COLUMN_mail +")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Customers.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Profile.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.ProductCategory.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.ProductItems.TABLE_NAME);
            onCreate(sqLiteDatabase);
    }

    public boolean Customer_insert_data(String name,String emailid,String password){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Customers.COLUMN_NAME_NAME,name);
        values.put(CustomerMaster.Customers.COLUMN_NAME_EMAIL,emailid);
        values.put(CustomerMaster.Customers.COLUMN_NAME_PASSWORD,password);

        long rowId=db.insert(CustomerMaster.Customers.TABLE_NAME,null,values);


        if(rowId  == -1){
            return false;
        }

        return true;

    }

    public boolean Customer_Profile_insert(String cus_foreign_id,byte[] image){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.Profile.COLUMN_NAME_IMAGE,image);
        values.put(CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID,cus_foreign_id);
        long rowId=db.insert(CustomerMaster.Profile.TABLE_NAME,null,values);
        //System.out.println("Rows:"+rowId);
        if(rowId  == -1){
            return false;
        }

        return true;


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
            fid=cu.getString(6);
            Bitmap bitmap=null;

            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            Products products=new Products(id,name,bitmap,fid,count,desc,price);
            list.add(products);

        }

        return list;

    }
    public ArrayList Retrive_admin_search_product_details(String pname){
        ArrayList<Products> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();

        String sql="SELECT * FROM "+ CustomerMaster.ProductItems.TABLE_NAME + " WHERE "+ CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME
                +" LIKE ?";
        String []selectionArgs={"%"+pname + "%"};

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
            fid=cu.getString(6);
            Bitmap bitmap=null;

            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            Products products=new Products(id,name,bitmap,fid,count,desc,price);
            list.add(products);

        }

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
            Bitmap bitmap=null;

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
            String selectionArgs[] = {id};
           int rowsAffected= db.delete(CustomerMaster.Customers.TABLE_NAME,selection,selectionArgs);
           if(rowsAffected > 0){
               return true;
           }
            return false;

    }
    public boolean Admin_delete_current_product(String id){
        try{
            SQLiteDatabase db=getReadableDatabase();
            String selection=CustomerMaster.ProductItems.COLUMN_NAME_ID + " = ?";
            String selectionArgs[]={id};
             int rowsAffected=db.delete(CustomerMaster.ProductItems.TABLE_NAME,selection,selectionArgs);
             if(rowsAffected > 0) {
                 return true;
             }
             return false;
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
            String selectionArgs[]={id};
            db.delete(CustomerMaster.ProductCategory.TABLE_NAME,selection,selectionArgs);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean Admin_add_Category_Details(byte[] image,String name){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE,image);
        values.put(CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME,name);
        long rowId=db.insert(CustomerMaster.ProductCategory.TABLE_NAME,null,values);
        if(rowId  == -1){
            return false;
        }

        return true;

    }
    public boolean Admin_add_product_Details(String name,String desc,String price,String count,byte[] image,String fid){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME,name);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_COUNT,count);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_DESCRIPTION,desc);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRICE,price);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_PRODUCTIMAGE,image);
        values.put(CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY,fid);

        long rowId=db.insert(CustomerMaster.ProductItems.TABLE_NAME,null,values);

        if(rowId  == -1){
            return false;
        }

        return true;
    }
    public boolean Admin_update_category_Info(String id,String name,byte[]image){
        SQLiteDatabase db=getReadableDatabase();
        ContentValues values=new ContentValues();
        values.put(CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE,image);
        values.put(CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME,name);
        String selection= CustomerMaster.ProductCategory.COLUMN_NAME_ID +" = ?";
        String selectionArgs[]={id};
        int count=db.update(CustomerMaster.ProductCategory.TABLE_NAME,values,selection,selectionArgs);

        if(count > 0){
            return true;
        }
        return false;

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
            String selectionArgs[] = {id};

            int counts = db.update(CustomerMaster.ProductItems.TABLE_NAME, values, selection, selectionArgs);
            if (counts > 0) {
                return true;
            }
            return false;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }

    public Cursor  Customer_mail_check(String emailid){
        SQLiteDatabase db=getReadableDatabase();
        String projection[]={CustomerMaster.Customers.COLUMN_NAME_ID, CustomerMaster.Customers.COLUMN_NAME_NAME, CustomerMaster.Customers.COLUMN_NAME_EMAIL,
                CustomerMaster.Customers.COLUMN_NAME_PASSWORD};

        Cursor cursor=db.query(CustomerMaster.Customers.TABLE_NAME,projection, null,null,null,null,null);
        return  cursor;

    }

    public Cursor Admin_product_name_check(){
        SQLiteDatabase db=getReadableDatabase();
        String projection[]={CustomerMaster.ProductCategory.COLUMN_NAME_ID, CustomerMaster.ProductCategory.COLUMN_NAME_IMAGE, CustomerMaster.ProductCategory.COLIMN_NAME_CATEGORY_NAME};

        Cursor cu=db.query(CustomerMaster.ProductCategory.TABLE_NAME,projection,null,null,null,null,null);
        return cu;
    }

    public Cursor Admin_Item_name_check(){
        SQLiteDatabase db=getReadableDatabase();
        String projection[]={CustomerMaster.ProductItems.COLUMN_NAME_ID, CustomerMaster.ProductItems.COLUMN_NAME_PRODUCT_NAME,
                CustomerMaster.ProductItems.COLUMN_NAME_COUNT, CustomerMaster.ProductItems.COLUMN_NAME_DESCRIPTION, CustomerMaster.ProductItems.COLUMN_NAME_PRICE,
                CustomerMaster.ProductItems.COLUMN_NAME_PRODUCTIMAGE, CustomerMaster.ProductItems.COLUMN_NAME_FOREIGNKEY};

        Cursor cu=db.query(CustomerMaster.ProductItems.TABLE_NAME,projection,null,null,null,null,null);
        return cu;
    }

    public Bitmap getImage(String user_id){
        SQLiteDatabase db=getReadableDatabase();
        Bitmap bitmap=null;
        // System.out.println("Bits :"+bitmap);
        //String orderby= CustomerMaster.Profile.COLUMN_NAME_ID+" DESC";
        String sql="SELECT * FROM "+ CustomerMaster.Profile.TABLE_NAME+" WHERE "+ CustomerMaster.Profile.COLUMN_FOREIGNKEY_CUS_ID +"=?";
        Cursor cu=db.rawQuery(sql,new String[]{user_id});
        // System.out.println("Cus :"+user_id);
        byte[] image=null;
        int t=-1;
        while(cu.moveToNext()){
            image=cu.getBlob(1);
            t++;
            bitmap= BitmapFactory.decodeByteArray(image,0,image.length);

            // System.out.println("Bit :"+user_id);

        }
        System.out.println("rrow :"+t);
        return bitmap;
    }
}
