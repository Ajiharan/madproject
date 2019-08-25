package com.example.shopcenter.model;

import android.media.Image;

public class Product {

    private String productName;
    private String productCategory;
    private String productApprovalStatus;
    private String ProductApprovalDate;
    private Image productImage;

    public Product(String productName, String productCategory, String productApprovalStatus, String productApprovalDate) {
        this.productName = productName;
        this.productCategory = productCategory;
        this.productApprovalStatus = productApprovalStatus;
        ProductApprovalDate = productApprovalDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductApprovalStatus() {
        return productApprovalStatus;
    }

    public void setProductApprovalStatus(String productApprovalStatus) {
        this.productApprovalStatus = productApprovalStatus;
    }

    public String getProductApprovalDate() {
        return ProductApprovalDate;
    }

    public void setProductApprovalDate(String productApprovalDate) {
        ProductApprovalDate = productApprovalDate;
    }

    public Image getProductImage() {
        return productImage;
    }

    public void setProductImage(Image productImage) {
        this.productImage = productImage;
    }
}
