package com.food.localresto.entities;


public class OrderObject {

    //private int id;
    private String id;
    private String orderDate;
    private float orderPrice;
    private String orderStatus;
    private int tabel;
    private int pajak;
    private int disc;
    private float nettot;
    private float totalbayar;
    private int jml;

    public OrderObject(String id, String orderDate, float orderPrice, String orderStatus, int tabel) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.tabel = tabel;
    }

    public OrderObject(String id, String orderDate, float orderPrice, String orderStatus, int tabel, int pajak, int disc, float nettot, float totalbayar, int jml) {
        this.id = id;
        this.orderDate = orderDate;
        this.orderPrice = orderPrice;
        this.orderStatus = orderStatus;
        this.tabel = tabel;
        this.pajak = pajak;
        this.disc = disc;
        this.nettot = nettot;
        this.totalbayar = totalbayar;
        this.jml = jml;
    }

    public String getId() {
        return id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public float getOrderPrice() {
        return orderPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public int getTabel() {
        return tabel;
    }

    public int getPajak() {
        return pajak;
    }

    public int getDisc() {
        return disc;
    }

    public float getNetTot() {
        return nettot;
    }

    public float getTotalBayar() {
        return totalbayar;
    }

    public int getJml() {
        return jml;
    }
}
