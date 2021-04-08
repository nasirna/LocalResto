package com.food.localresto.entities;

import java.util.List;

public class HistoryObject {
    //private int order_id;
    private String order_id;
    private String order_date;
    private int order_quantity;
    private float order_price;
    private String status;
    private String payment_method;
    //List<menu_item> menu_items;
    private int tabel;
    private int pajak;
    private int disc;
    private int jml;
    private float totbayar;

    public HistoryObject(String order_id, String order_date, int order_quantity, float order_price, String status, String payment_method, int tabel
                         //, List<menu_item> menu_items
    ) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_quantity = order_quantity;
        this.order_price = order_price;
        this.status = status;
        this.payment_method = payment_method;
        this.tabel = tabel;
        //this.menu_items = menu_items;
    }

    public HistoryObject(String order_id, String order_date, int order_quantity, float order_price, String status, String payment_method, int tabel, int pajak, int disc, int jml, float totbayar) {
        this.order_id = order_id;
        this.order_date = order_date;
        this.order_quantity = order_quantity;
        this.order_price = order_price;
        this.status = status;
        this.payment_method = payment_method;
        this.tabel = tabel;
        this.pajak = pajak;
        this.disc = disc;
        this.jml = jml;
        this.totbayar = totbayar;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_date(){
        return order_date;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public float getOrder_price() {
        return order_price;
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

    /*public List<menu_item> getMenu_items() {
        return menu_items;
    }*/

    public int getPajak() {
        return pajak;
    }

    public int getDisc() {
        return disc;
    }

    public int getJml() {
        return jml;
    }

    public float getTotbayar() {
        return totbayar;
    }

////////
    public HistoryObject setOrder_id(String order_id) {
        this.order_id = order_id;
        return this;
    }
    public HistoryObject setOrder_date(String order_date) {
        this.order_date = order_date;
        return this;
    }
    public HistoryObject setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
        return this;
    }
    public HistoryObject setOrder_price(float order_price) {
        this.order_price = order_price;
        return this;
    }
    public HistoryObject setStatus(String status) {
        this.status = status;
        return this;
    }
    public HistoryObject setPayment_method(String payment_method) {
        this.payment_method = payment_method;
        return this;
    }
    public HistoryObject setTabel(int tabel) {
        this.tabel = tabel;
        return this;
    }
    public HistoryObject setPajak(int pajak) {
        this.pajak = pajak;
        return this;
    }
    public HistoryObject setDisc(int disc) {
        this.disc = disc;
        return this;
    }
    public HistoryObject setJml(int jml) {
        this.jml = jml;
        return this;
    }
    public HistoryObject setTotbayar(float totbayar) {
        this.totbayar = totbayar;
        return this;
    }
}
