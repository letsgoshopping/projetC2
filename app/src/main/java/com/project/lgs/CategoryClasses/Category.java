package com.project.lgs.CategoryClasses;

public class Category {

    private String name;
    private String display;

    public Category (String name, String display){
        this.name = name;
        this.display = display;
    }

    public String getCatName() {
        return name;
    }

    public void setCatName(String catName) {
        this.name = catName;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
