package com.project.lgs.UsersClasses;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String id;
    private String conName;
    private String phoneCode;
    private String phoneNum;
    private String county;
    private String city;
    private String street;

    private String floor;

    public User (String id,String email, String conName, String phoneCode, String phoneNum, String county, String city, String street, String floor){
        this.email = email;
        this.id = id;
        this.conName = conName;
        this.phoneCode = phoneCode;
        this.phoneNum = phoneNum;
        this.county = county;
        this.city = city;
        this.street = street;
        this.floor = floor;
    }

    public String getEmail() {
        return email;
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

    @Override
    public String toString() {
        return email;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
