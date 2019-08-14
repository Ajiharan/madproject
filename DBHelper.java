package com.example.shopcenter.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="OnlineShop.db";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CUSTOMER_CREATE_ENTRIES="CREATE TABLE "+CustomerMaster.Customers.TABLE_NAME+"("+
                CustomerMaster.Customers.COLUMN_NAME_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                CustomerMaster.Customers.COLUMN_NAME_NAME+" TEXT,"+CustomerMaster.Customers.COLUMN_NAME_EMAIL+
                " TEXT,"+CustomerMaster.Customers.COLUMN_NAME_PASSWORD +" TEXT)";

        sqLiteDatabase.execSQL(CUSTOMER_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ CustomerMaster.Customers.TABLE_NAME);
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
        else{
            return true;
        }
    }

    public Cursor  Customer_mail_check(String emailid){
         SQLiteDatabase db=getReadableDatabase();
         String projection[]={CustomerMaster.Customers.COLUMN_NAME_ID, CustomerMaster.Customers.COLUMN_NAME_NAME, CustomerMaster.Customers.COLUMN_NAME_EMAIL,
          CustomerMaster.Customers.COLUMN_NAME_PASSWORD};

        Cursor cursor=db.query(CustomerMaster.Customers.TABLE_NAME,projection, null,null,null,null,null);
        return  cursor;

    }
}
