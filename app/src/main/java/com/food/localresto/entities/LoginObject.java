package com.food.localresto.entities;


public class LoginObject {

    private int id;
    private String username;
    private String password;
    private String email;
    private String role;
    private String address;
    private String phone;
    private String loggedIn;

    public LoginObject(int id, String username, String password, String role, String email, String address, String phone, String loggedIn) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.loggedIn = loggedIn;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getLoggedIn() {
        return loggedIn;
    }

    public LoginObject setId(int id) {
        this.id = id;
        return this;
    }

    public LoginObject setUsername(String username) {
        this.username = username;
        return this;
    }
    public LoginObject setPassword(String password) {
        this.password = password;
        return this;
    }
    public LoginObject setRole(String role) {
        this.role = role;
        return this;
    }
    public LoginObject setEmail(String email) {
        this.email = email;
        return this;
    }
    public LoginObject setAddress(String address) {
        this.address = address;
        return this;
    }
    public LoginObject setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public LoginObject setLoggedIn(String loggedIn) {
        this.loggedIn = loggedIn;
        return this;
    }
}
