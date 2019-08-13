package com.example.shopcenter.Database;

import android.content.Context;
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
}
