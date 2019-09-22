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
        public static final String COLUMN_NAME_ID="p_id";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_NAME_MAIL="mail";
        public static final String COLUMN_NAME_ZIP="Zip";
        public static final String COLUMN_NAME_CARD="card";
        public static final String COLUMN_NAME_CITY="city";
        public static final String COLUMN_NAME_CUS_ID="cus_id";
        public static final String COLUMN_NAME_AMOUNT="amount";
        public static final String COLUMN_NAME_ORDER="or_id";
    }

    public static class Cus_Order implements  BaseColumns{
        public static  final String TABLE_NAME="orders";
        public static final String  COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_CUS_ID="cuid";
        public static final String COLUMN_NAME_AMOUNT="amount";
        public static final String COLUMN_NAME_DELIVERY="delevery";
    }

    public static class UserCart implements BaseColumns{
        public static final String TABLE_NAME="cart";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_NAME="name";
        public static final String COLUMN_DES="des";
        public static final String COLUMN_PRICE="price";
        public static final String COLUMN_COUNT="count";
        public static final String COLUMN_TOTAL="total";
        public static final String COLUMN_IMAGE="image";
        public static final String COLUMN_FOREIGN="cid";
        public static final String COLUMN_FOREIGN1="pid";
        public static final String COLUMN_DEFAULT_CHECK="order_check";
    }

    public static class User_cart_notification implements  BaseColumns{
        public static final String TABLE_NAME="cart_count";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_COUNT="count";
        public static final String COLUMN_CUS_ID="cus_id";
    }

    public static class User_order_notification implements  BaseColumns{
        public static final String TABLE_NAME="orders_count";
        public static final String COLUMN_ID="id";
        public static final String COLUMN_COUNT="count";
        public static final String COLUMN_CUS_ID="cus_id";
    }


    public static class SellerProductItems implements BaseColumns{
        public static final String TABLE_NAME="seller_products";
        public static final String COLUMN_NAME_ID="id";
        public static final String COLUMN_NAME_PRODUCT_NAME="name";
        public static final String COLUMN_NAME_COUNT="count";
        public static final String  COLUMN_NAME_PRICE="price";
        public static final String COLUMN_NAME_DESCRIPTION="description";
        public static final String COLUMN_NAME_PRODUCTIMAGE="image";
    }

    public static class Seller implements BaseColumns{
        public static final String COLUMN_NAME_ID="id";
        public static final String TABLE_NAME="seller";
        public static final String COLUMN_NAME_USER_NAME="user_name";
        public static final String COLUMN_NAME_EMAIL="mail";
        public static final String COLUMN_NAME_PASSWORD="password";
        public static final String COLUMN_NAME_CUSTOMER="cus_id";
        public static final String COLUMN_NAME_CHECK_CUSTOMER="chk";
    }
}
