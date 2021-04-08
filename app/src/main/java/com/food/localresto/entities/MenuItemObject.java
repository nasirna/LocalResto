package com.food.localresto.entities;


public class MenuItemObject {

    private int menu_item_id;
    private String kodeitem;
    private int menu_id;
    private String menu_name;
    private String menu_jenis;
    private String item_name;
    private String description;
    //private byte[] item_picture;
    private String item_picture;
    private float item_price;
    private float item_price2;
    private float item_price3;
    private String item_options;
    private String item_note;
    private String item_activate;
    private String item_disc;

    /*
    public MenuItemObject(int menu_id, String item_name, String description, byte[] item_picture, float item_price, String item_options) {
        this.menu_id = menu_id;
        this.item_name = item_name;
        this.description = description;
        this.item_picture = item_picture;
        this.item_price = item_price;
        this.item_options = item_options;
    }

    public MenuItemObject(int menu_id, String item_name, String description, byte[] item_picture, float item_price, String item_options, String item_note) {
        this.menu_id = menu_id;
        this.item_name = item_name;
        this.description = description;
        this.item_picture = item_picture;
        this.item_price = item_price;
        this.item_options = item_options;
        this.item_note = item_note;
    }*/
    public MenuItemObject(int menu_item_id,  String kodeitem, int menu_id, String menu_jenis, String menu_name, String item_name, String description, String item_picture, float item_price, float item_price2, float item_price3, String item_activate, String item_disc) {
        this.menu_item_id = menu_item_id;
        this.kodeitem = kodeitem;
        this.menu_id = menu_id;
        this.menu_jenis = menu_jenis;
        this.menu_name = menu_name;
        this.item_name = item_name;
        this.description = description;
        this.item_picture = item_picture;
        this.item_price = item_price;
        this.item_price2 = item_price2;
        this.item_price3 = item_price3;
        this.item_activate = item_activate;
        this.item_disc = item_disc;
        //this.item_options = item_options;
    }

    public MenuItemObject(int menu_item_id,  String kodeitem, int menu_id, String menu_jenis, String menu_name, String item_name, String description, String item_picture, float item_price) {
        this.menu_item_id = menu_item_id;
        this.kodeitem = kodeitem;
        this.menu_id = menu_id;
        this.menu_jenis = menu_jenis;
        this.menu_name = menu_name;
        this.item_name = item_name;
        this.description = description;
        this.item_picture = item_picture;
        this.item_price = item_price;
        //this.item_options = item_options;
    }

    public MenuItemObject(int menu_id, String menu_jenis, String menu_name, String item_name, String description, String item_picture, float item_price) {
        this.menu_id = menu_id;
        this.menu_jenis = menu_jenis;
        this.menu_name = menu_name;
        this.item_name = item_name;
        this.description = description;
        this.item_picture = item_picture;
        this.item_price = item_price;
        //this.item_options = item_options;
    }
    public int getMenu_item_id() {
        return menu_item_id;
    }

    public String getKodeitem() {
        return kodeitem;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getMenu_jenis() {
        return menu_jenis;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return item_picture;
    }

    public float getItem_price() {
        return item_price;
    }

    public float getItem_price2() {
        return item_price2;
    }

    public float getItem_price3() {
        return item_price3;
    }

    public String getItem_options(){
        return item_options;
    }

    public String getItem_activate(){
        return item_activate;
    }

    public String getItem_disc(){
        return item_disc;
    }

    public void setImage(String item_picture) {
        this.item_picture = item_picture;
    }

    public MenuItemObject setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
        return this;
    }
    public MenuItemObject setKodeitem(String kodeitem) {
        this.kodeitem = kodeitem;
        return this;
    }
    public MenuItemObject setMenu_id(int menu_id) {
        this.menu_id = menu_id;
        return this;
    }
    public MenuItemObject setMenu_name(String menu_name) {
        this.menu_name = menu_name;
        return this;
    }
    public MenuItemObject setMenu_jenis(String menu_jenis) {
        this.menu_jenis = menu_jenis;
        return this;
    }
    public MenuItemObject setItem_name(String item_name) {
        this.item_name = item_name;
        return this;
    }
    public MenuItemObject setDescription(String description) {
        this.description = description;
        return this;
    }
    public MenuItemObject setItem_price(float item_price) {
        this.item_price = item_price;
        return this;
    }
    public MenuItemObject setItem_price2(float item_price2) {
        this.item_price2 = item_price2;
        return this;
    }
    public MenuItemObject setItem_price3(float item_price3) {
        this.item_price3 = item_price3;
        return this;
    }
    public MenuItemObject setItem_options(String item_options) {
        this.item_options = item_options;
        return this;
    }

}
