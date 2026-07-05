package com.cravyfoods.pojo;

/**
 * POJO class representing a User in the CravyFoods application.
 * Contains user details for registration, login, and session management.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String address;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(int id, String username, String password, String email, String phone, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + ", phone=" + phone + ", address=" + address + "]";
    }
}
