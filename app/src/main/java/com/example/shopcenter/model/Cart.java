package com.example.shopcenter.model;

import android.graphics.Bitmap;

public class Cart {
    private String id;
    private String name;
    private String des;
    private String price;
    private String count;
    private String total;
    private String fid;
    private Bitmap bitmap;
    private String product_id;


    public Cart(String name, String des, String price, String total, String fid, Bitmap bitmap, String count,String product_id) {

        this.name = name;
        this.des = des;
        this.price = price;
        this.total = total;
        this.fid = fid;
        this.bitmap = bitmap;
        this.count=count;
        this.product_id=product_id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
