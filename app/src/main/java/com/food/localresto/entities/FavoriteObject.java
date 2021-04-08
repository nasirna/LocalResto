package com.food.localresto.entities;


public class FavoriteObject {

    private int id;
    private String name;
    private String imagePath;
    private float price;

    public FavoriteObject(int id, String name, String imagePath, float price) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public float getPrice() {
        return price;
    }

    public FavoriteObject setId(int id) {
        this.id = id;
        return this;
    }
    public FavoriteObject setName(String name) {
        this.name = name;
        return this;
    }
    public FavoriteObject setImagePath(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }
    public FavoriteObject setPrice(float price) {
        this.price = price;
        return this;
    }

}
