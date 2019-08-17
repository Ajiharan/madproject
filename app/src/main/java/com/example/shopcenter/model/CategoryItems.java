package com.example.shopcenter.model;

import android.graphics.Bitmap;

public class CategoryItems {
    private String name;
    private Bitmap bitmap;
    private String id;

    public CategoryItems(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setId(String id){
        this.id=id;
    }
    public  String getId(){
        return this.id;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
