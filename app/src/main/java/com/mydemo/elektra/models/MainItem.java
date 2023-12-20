package com.mydemo.elektra.models;

public class MainItem {

    private String category_id, name, description, image;

    public MainItem() {
    }

    public MainItem(String category_id, String name, String description, String image) {
        this.category_id = category_id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
