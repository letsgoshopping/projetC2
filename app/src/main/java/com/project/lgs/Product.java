package com.project.lgs;

public class Product {

    private String title;
    private String description;
    private Double rating;
    private Double price;
    private String user;
    private String publishDate;
    private String category;
    private int imageRessource;


    public Product(String title, String description, Double rating, Double price, int img, String user,String pDate){
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.imageRessource = img;
        this.user = user;
        this.publishDate = pDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getImageRessource() {
        return imageRessource;
    }

    public void setImageRessource(int imageRessource) {
        this.imageRessource = imageRessource;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        publishDate = publishDate;
    }
}
