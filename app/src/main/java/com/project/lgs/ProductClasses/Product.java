package com.project.lgs.ProductClasses;

import java.io.Serializable;

public class Product implements Serializable {

    private String id;
    private String title;
    private String description;
    private String rating;
    private String price;
    private byte[] image;
    private String user;
    private String publishDate;
    private String category;
    private String code;
    private String orderedQtity;
    private String supEmail;


    public Product(){}

    public Product(String id, String title, String description, String rating, String price, byte[] image, String user,String pDate, String category, String code){
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.user = user;
        this.publishDate = pDate;
        this.category = category;
        this.code = code;
    }

    public String getTitle() {

        if (title != null)
            return title;
        else return "No Title";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription()
    {
        if (description != null)
            return description;
        else return "No description is provided";
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {

        if (rating != null)
            return rating;
        else return "No Rating";
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {

        if (price != null)
            return price;
        else return "0";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUser() {

       return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPublishDate() {

        if (publishDate != null)
            return publishDate;
        else return "No Date";
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getCategory() {

        if (category != null)
            return category;
        else return "No Category";
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return (code ==  null? "0":code);
    }

    public void setCode(String id) {
        this.code = code;
    }

    public String getOrderedQtity() {
        return orderedQtity;
    }

    public void setOrderedQtity(String orderedQtity) {
        this.orderedQtity = orderedQtity;
    }

    public String getSupEmail() {
        return supEmail;
    }

    public void setSupEmail(String supEmail) {
        this.supEmail = supEmail;
    }


}
