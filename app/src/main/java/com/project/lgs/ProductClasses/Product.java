package com.project.lgs.ProductClasses;

public class Product{

    private String title;
    private String description;
    private String rating;
    private String price;
    private String user;
    private String publishDate;
    private String category;
    private byte[] image;


    public Product(){}

    public Product(String title, String description, String rating, String price, byte[] image, String user,String pDate, String category){
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.image = image;
        this.user = user;
        this.publishDate = pDate;
        this.category = category;
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

        if (user != null)
            return user;
        else return "No User";
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
}
