package com.food.localresto.entities;

public class Product {
    private	int	id;
    private	String name;
    private	int	quantity;
    private byte[] image;

    public Product(String name, int quantity, byte[] image) {
        this.name = name;
        this.quantity = quantity;
        this.image = image;
    }

    public Product(int id, String name, int quantity, byte[] image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
