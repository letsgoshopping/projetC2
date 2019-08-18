package com.project.lgs.UsersClasses;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String id;

    public User (String id,String email){
        this.email = email;
        this.id = id;
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
        id = id;
    }

    @Override
    public String toString() {
        return email;
    }
}
