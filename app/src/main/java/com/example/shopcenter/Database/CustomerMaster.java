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
}
