package com.project.lgs.CategoryClasses;

public class Category{

    private String name;
    private String display;
    private String CatId;

    public Category (String id, String name, String display){
        this.name = name;
        this.display = display;
        this.CatId = id;
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

    public String getCatId() {
        return CatId;
    }

    public void setCatId(String catId) {
        CatId = catId;
    }

    @Override
    public String toString() {
        return name;
    }
}
