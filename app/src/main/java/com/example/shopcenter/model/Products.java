package com.example.shopcenter.model;

import android.graphics.Bitmap;

public class Products {
    private String product_id;
    private String product_name;
    private String product_desc;
    private String product_price;
    private Bitmap bitmap;
    private String  count;
    private String product_foreign_key;

   // private String product_cat;

    public Products(String product_id, String product_name, Bitmap bitmap, String product_foreign_key,String count,String product_desc,String product_price) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.bitmap = bitmap;
        this.product_foreign_key = product_foreign_key;
        this.count=count;
        this.product_desc=product_desc;
        this.product_price=product_price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getProduct_foreign_key() {
        return product_foreign_key;
    }

    public void setProduct_foreign_key(String product_foreign_key) {
        this.product_foreign_key = product_foreign_key;
    }

    public void setCount(String count){
        this.count=count;
    }

    public String getCount(){
        return count;
    }

    public void setProduct_desc(String product_desc){
        this.product_desc=product_desc;
    }

    public String getProduct_desc(){
        return product_desc;
    }


    public void setProduct_price(String product_price){
        this.product_price=product_price;
    }

    public String getProduct_price(){
        return product_price;
    }



}
