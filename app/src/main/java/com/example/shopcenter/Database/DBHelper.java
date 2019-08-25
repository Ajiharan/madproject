package com.example.shopcenter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.shopcenter.model.CategoryItems;

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

        String SELER_PRODUCT_ENTRIES="CREATE TABLE "+ CustomerMaster.SellerProduct.TABLE_NAME+"("+
                CustomerMaster.SellerProduct.COLUMN_NAME_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.SellerProduct.COLUMN_NAME_IMAGE+" LONGBLOB,"+
                CustomerMaster.SellerProduct.COLIMN_NAME_CATEGORY_NAME+" TEXT)";

        String SELLER_CREATE_ENTRIES="CREATE TABLE "+CustomerMaster.Seller.TABLE_NAME+"("+
                CustomerMaster.Seller.COLUMN_NAME_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Seller.COLUMN_NAME_NAME+" TEXT,"+
                CustomerMaster.Seller.COLUMN_NAME_EMAIL+" TEXT,"+
                CustomerMaster.Seller.COLUMN_NAME_PASSWORD +" TEXT)";


        sqLiteDatabase.execSQL(CUSTOMER_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(CUSTOMER_PROFILE_CREATES_ENTRIES);
        sqLiteDatabase.execSQL(ADMIN_CATEGORY_DETAILS_ENTRIES);
        sqLiteDatabase.execSQL(SELER_PRODUCT_ENTRIES);
        sqLiteDatabase.execSQL(SELLER_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Customers.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Profile.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.ProductCategory.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.SellerProduct.TABLE_NAME);
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
        else{
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
