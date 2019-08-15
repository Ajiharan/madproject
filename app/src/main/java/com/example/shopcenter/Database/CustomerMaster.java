package com.example.shopcenter.Database;

import android.provider.BaseColumns;

public final class CustomerMaster {
    private CustomerMaster(){}

    public static class Customers implements BaseColumns {
        public static final String TABLE_NAME="customer";
        public static final String  COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_NAME="name";
        public static final String COLUMN_NAME_PASSWORD="password";
        public static final String COLUMN_NAME_EMAIL="email_id";
    }

    public static class Profile implements BaseColumns{
        public static final String TABLE_NAME="profile";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_IMAGE="photo";
        public static final String  COLUMN_FOREIGNKEY_CUS_ID="cus_id";
    }

    public static class ProductCategory implements  BaseColumns{
        public static final String TABLE_NAME="Product_category";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_IMAGE="image";
        public static final String  COLIMN_NAME_CATEGORY_NAME="category";
    }
}
