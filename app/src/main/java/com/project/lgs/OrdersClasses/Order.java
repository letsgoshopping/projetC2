package com.project.lgs.OrdersClasses;

import java.util.HashMap;

public class Order {

    private String id;
    private String invoiceNumber;
    private String date;
    private String userId;
    private HashMap<String,HashMap<String, HashMap<String,String>>> products;

    public Order (String id,String invoiceNumber, String date, String userId){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.id = id;
        this.userId =  userId;
    }

    public Order (String id,String invoiceNumber, String date, String userId, HashMap<String,HashMap<String, HashMap<String,String>>> products){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.id = id;
        this.products = products;
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public HashMap<String, HashMap<String, HashMap<String, String>>> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, HashMap<String, HashMap<String, String>>> products) {
        this.products = products;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return invoiceNumber;
    }

}
