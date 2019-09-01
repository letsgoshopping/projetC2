package com.project.lgs.OrdersClasses;

import org.bson.Document;

import java.io.Serializable;
import java.util.HashMap;

public class Order implements Serializable {

    private String id;
    private String invoiceNumber;
    private String date;
    private String userId;
    private String total;
    private Document products;
    private String status;

    public Order (String invoiceNumber, String date, String userId, String total,String status){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.userId =  userId;
        this.total = total;
        this.status = status;
    }

    public Order (String id,String invoiceNumber, String date, String userId, Document products, String total, String status){
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.id = id;
        this.products = products;
        this.userId = userId;
        this.total = total;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
