package com.food.localresto.entities;


public class CartObject {

    private int id;
    private String kodeitem;
    private String orderName;
    private int quantity;
    private float price;
    private String extra;
    private String note;

    public CartObject(int id, String kodeitem, String orderName, int quantity, float price, String extra, String note) {
        this.id = id;
        this.kodeitem = kodeitem;
        this.orderName = orderName;
        this.quantity = quantity;
        this.price = price;
        this.extra = extra;
        this.note = note;
    }

    public String getKodeitem() {
        return kodeitem;
    }

    /*public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPrice(float price) {
        this.price = price;
    }*/

    public int getId() {
        return id;
    }

    public String getOrderName() {
        return orderName;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public String getExtra() {
        return extra;
    }

    public String getNote() {
        return note;
    }

    ////
    public CartObject setKodeitem(String kodeitem) {
        this.kodeitem = kodeitem;
        return this;
    }
    public CartObject setId(int id) {
        this.id = id;
        return this;
    }
    public CartObject setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }
    public CartObject setPrice(float price) {
        this.price = price;
        return this;
    }
    public CartObject setOrderName(String orderName) {
        this.orderName = orderName;
        return this;
    }
    public CartObject setExtra(String extra) {
        this.extra = extra;
        return this;
    }
    public CartObject setNote(String note) {
        this.note = note;
        return this;
    }
}
