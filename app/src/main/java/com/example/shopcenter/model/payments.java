package com.example.shopcenter.model;

public class payments {

    private String id;
    private String total;
    private String name;
    private String  email_id;
    private String zipcode;
    private String cardNo;
    private String city;
    private String cus_id;
    private String order_id;


//    public payments( String total, String name, String email_id, String zipcode, String cardNo, String city, String cus_id, String order_id) {
//
//        this.total = total;
//        this.name = name;
//        this.email_id = email_id;
//        this.zipcode = zipcode;
//        this.cardNo = cardNo;
//        this.city = city;
//        this.cus_id = cus_id;
//        this.order_id = order_id;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCus_id() {
        return cus_id;
    }

    public void setCus_id(String cus_id) {
        this.cus_id = cus_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
