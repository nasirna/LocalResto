package com.food.localresto.entities;
import java.io.Serializable;

public class MenuCategoryObject implements Serializable {
    private int id;
    private int kode;
    private int menu_id;
    private String menu_jenis;
    private String menu_name;
    private String menu_image;

    public MenuCategoryObject(int id, int kode, int menu_id, String menu_jenis, String menu_name, String menu_image) {
        this.id = id;
        this.kode = kode;
        this.menu_id = menu_id;
        this.menu_jenis = menu_jenis;
        this.menu_name = menu_name;
        this.menu_image = menu_image;
    }

    public MenuCategoryObject(String menu_jenis, String menu_name, String menu_image) {
        this.menu_jenis = menu_jenis;
        this.menu_name = menu_name;
        this.menu_image = menu_image;
    }

    public int getId() {
        return id;
    }

    public int getKode() {
        return kode;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public String getMenu_jenis() {
        return menu_jenis;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public String getMenu_image() {
        return menu_image;
    }

    public MenuCategoryObject setId(int id) {
        this.id = id;
        return this;
    }

    public MenuCategoryObject setKode(int kode) {
        this.kode = kode;
        return this;
    }

    public MenuCategoryObject setMenu_id(int menu_id) {
        this.menu_id = menu_id;
        return this;
    }

    public MenuCategoryObject setMenu_name(String menu_name) {
        this.menu_name = menu_name;
        return this;
    }

    public MenuCategoryObject setMenu_jenis(String menu_jenis) {
        this.menu_jenis = menu_jenis;
        return this;
    }

    public MenuCategoryObject setMenu_image(String menu_image) {
        this.menu_image = menu_image;
        return this;
    }
}