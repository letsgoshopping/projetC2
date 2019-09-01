package com.project.lgs.SupplierClasses;

import java.io.Serializable;

public class Supplier implements Serializable {

    private String id;
    private String name;
    private String description;
    private String phoneNumber;
    private String joinDate;
    private byte[] image;
    private String email;


    public Supplier(){}

    public Supplier(String id, String name, String description, String phoneNumber,byte[] img, String joinDate, String email){
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = img;
        this.phoneNumber = phoneNumber;
        this.joinDate = joinDate;
        this.email = email;

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


    public byte[] getImage() {
        return (image==null? null:image);
    }

    public void setImage (byte[] image) {
        this.image = image;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String toString (){
        return this.email;
    }
}
