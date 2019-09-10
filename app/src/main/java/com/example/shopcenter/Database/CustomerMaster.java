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
    public static class ProductItems implements BaseColumns{
        public static final String TABLE_NAME="products";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_PRODUCT_NAME="name";
        public static final String COLUMN_NAME_COUNT="count";
        public static final String  COLUMN_NAME_PRICE="price";
        public static final String COLUMN_NAME_DESCRIPTION="description";
        public static final String COLUMN_NAME_PRODUCTIMAGE="image";
        public static final String COLUMN_NAME_FOREIGNKEY="cid";
        public static final String COLUMN_NAME_CATEGORY_NAME="cname";
    }
    public static class PaymentDetails implements BaseColumns{
        public static final String TABLE_NAME="payment";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_mail="mail";
        public static final String COLUMN_Zip="Zip";
        public static final String COLUMN_card="card";
        public static final String COLUMN_city="city";

    }
}
