package com.food.localresto.entities;

public class BackupOrderItemObject {
    private int id;
    private String order_id;
    private String kodeitem;
    private int quantity;
    private float price;
    private float subtotal;
    private String options;
    private String notes;


    public int getId() {
        return id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getKodeitem() {
        return kodeitem;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public String getOptions() {
        return options;
    }

    public String getNotes() {
        return notes;
    }

    public BackupOrderItemObject setId(int id) {
        this.id = id;
        return this;
    }
    public BackupOrderItemObject setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }
    public BackupOrderItemObject setKodeitem(String kodeitem) {
        this.kodeitem = kodeitem;
        return this;
    }

    public BackupOrderItemObject setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
    public BackupOrderItemObject setPrice(float price) {
        this.price = price;
        return this;
    }
    public BackupOrderItemObject setSubtotal(float subtotal) {
        this.subtotal = subtotal;
        return this;
    }
    public BackupOrderItemObject setOptions(String options) {
        this.options = options;
        return this;
    }
    public BackupOrderItemObject setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
