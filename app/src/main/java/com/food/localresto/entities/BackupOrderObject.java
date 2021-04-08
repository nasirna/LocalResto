package com.food.localresto.entities;

public class BackupOrderObject {
    private int id;
    private String order_id;
    private String user_id;
    private int order_quantity;
    private float order_price;
    private String order_date;
    private String status;
    private String payment_method;
    private int tabel;
    private int pajak;
    private int disc;
    private float nettot;
    private float totbayar;

    public int getId() {
        return id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getUser_id(){
        return user_id;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public float getOrder_price() {
        return order_price;
    }

    public String getOrder_date(){
        return order_date;
    }

    public String getStatus() {
        return status;
    }

    public String getPayment_method() {
        return payment_method;
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

    public float getNettot() {
        return nettot;
    }

    public float getTotbayar() {
        return totbayar;
    }

    ////////

    public BackupOrderObject setId(int id) {
        this.id = id;
        return this;
    }
    public BackupOrderObject setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }
    public BackupOrderObject setUser_id(String user_id) {
        this.user_id = user_id;
        return this;
    }
    public BackupOrderObject setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
        return this;
    }
    public BackupOrderObject setOrder_price(float order_price) {
        this.order_price = order_price;
        return this;
    }
    public BackupOrderObject setOrder_date(String order_date) {
        this.order_date = order_date;
        return this;
    }
    public BackupOrderObject setStatus(String status) {
        this.status = status;
        return this;
    }
    public BackupOrderObject setPayment_method(String payment_method) {
        this.payment_method = payment_method;
        return this;
    }
    public BackupOrderObject setTabel(int tabel) {
        this.tabel = tabel;
        return this;
    }
    public BackupOrderObject setPajak(int pajak) {
        this.pajak = pajak;
        return this;
    }
    public BackupOrderObject setDisc(int disc) {
        this.disc = disc;
        return this;
    }
    public BackupOrderObject setNettot(float nettot) {
        this.nettot = nettot;
        return this;
    }
    public BackupOrderObject setTotbayar(float totbayar) {
        this.totbayar = totbayar;
        return this;
    }
}
