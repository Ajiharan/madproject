package com.example.shopcenter.model;

import android.graphics.Bitmap;

public class sellers {
    private String name;
    private Bitmap bitmap;
    private String id;
    private String Dvalue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDvalue() {
        return Dvalue;
    }

    public void setDvalue(String dvalue) {
        Dvalue = dvalue;
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

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
