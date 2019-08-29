package com.project.lgs.CartClasses;

import org.bson.Document;

import java.util.HashMap;

public class Cart {

    private String id;
    private String invoiceNumber;
    private String date;
    private String userId;
    private String total;
    private Document products;
    private String productSequence;

    public Cart (String invoiceNumber, String date, String userId, String productSequence, String total){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.userId =  userId;
        this.productSequence = productSequence;
        this.total = total;
    }

    public Cart (String id,String invoiceNumber, String date, String userId, Document products, String productSequence, String total){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.id = id;
        this.products = products;
        this.userId = userId;
        this.productSequence = productSequence;
        this.total = total;
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
        this.id = id;
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

    public String getProductSequence() {
        return productSequence;
    }

    public void setProductSequence(String productSequence) {
        this.productSequence = productSequence;
    }

    public String getTotal() {
        return (total==null? "0":total);
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return invoiceNumber;
    }

}
