package com.example.shopcenter.model;

import android.graphics.Bitmap;

public class User {

    private String id;
    private String name;
    private String mail;
    private String password;
    private Bitmap bitmap;

    public User() {

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBitmap(Bitmap bitmap){this.bitmap=bitmap;}

    public Bitmap getImage(){return bitmap;}
}
