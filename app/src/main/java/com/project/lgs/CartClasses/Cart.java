package com.project.lgs.CartClasses;

import org.bson.Document;

import java.util.HashMap;

public class Cart {

    private String id;
    private String invoiceNumber;
    private String date;
    private String userId;
    private Document products;

    public Cart (String invoiceNumber, String date, String userId){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.userId =  userId;
    }

    public Cart (String id,String invoiceNumber, String date, String userId, Document products){
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

    public Document getProducts() {
        return products;
    }

    public void setProducts(Document products) {
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
