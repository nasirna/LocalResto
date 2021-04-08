package com.food.localresto.entities;


public class RestaurantObject {

    private int id;
    private String kode;
    private String name;
    private String description;
    private String address;
    private String city;
    private String profinsi;
    private String email;
    private String phone;
    private String opening_time;

    public RestaurantObject(int id, String kode, String name, String description, String address, String city, String profinsi, String email, String phone, String opening_time) {
        this.id = id;
        this.kode = kode;
        this.name = name;
        this.description = description;
        this.address = address;
        this.city = city;
        this.profinsi = profinsi;
        this.email = email;
        this.phone = phone;
        this.opening_time = opening_time;
    }

    public int getId() {
        return id;
    }

    public String getKode() {
        return kode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getProfinsi() {
        return profinsi;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public RestaurantObject setId(int id) {
        this.id = id;
        return this;
    }
    public RestaurantObject setKode(String kode) {
        this.kode = kode;
        return this;
    }
    public RestaurantObject setName(String name) {
        this.name = name;
        return this;
    }
    public RestaurantObject setDescription(String description) {
        this.description = description;
        return this;
    }
    public RestaurantObject setAddress(String address) {
        this.address = address;
        return this;
    }
    public RestaurantObject setCity(String city) {
        this.city = city;
        return this;
    }
    public RestaurantObject setProfinsi(String profinsi) {
        this.profinsi = profinsi;
        return this;
    }
    public RestaurantObject setEmail(String email) {
        this.email = email;
        return this;
    }
    public RestaurantObject setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public RestaurantObject setOpening_time(String opening_time) {
        this.opening_time = opening_time;
        return this;
    }

}
