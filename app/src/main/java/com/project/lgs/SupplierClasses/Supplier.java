package com.project.lgs.SupplierClasses;

public class Supplier{

    private String name;
    private String description;
    private String phoneNumber;
    private String joinDate;
    private int imageRessource;
    private String email;
    private String password;


    public Supplier(){}

    public Supplier(String name, String description, String phoneNumber,int img, String joinDate, String email, String pass){
        this.name = name;
        this.description = description;
        this.imageRessource = img;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
        this.email = email;
        this.password = pass;

    }

    public String getName() {

        if (name != null)
            return name;
        else return "No Name";
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhoneNumber() {

        if (phoneNumber != null)
            return phoneNumber;
        else return "No Number";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public int getImageRessource() {
        return imageRessource;
    }

    public void setImageRessource(int imageRessource) {
        this.imageRessource = imageRessource;
    }

    public String getJoinDate() {

        if (joinDate != null)
            return joinDate;
        else return "No Date";
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getEmail() {

        if (email != null)
            return email;
        else return "No Email";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
