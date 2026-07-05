package com.cravyfoods.pojo;

/**
 * POJO class representing a Restaurant in the CravyFoods application.
 * Contains metadata about famous food outlets.
 */
public class Restaurant {
    private int id;
    private String name;
    private String cuisine;
    private double rating;
    private int deliveryTime;
    private String imagePath;
    private String address;

    // Default constructor
    public Restaurant() {}

    // Parameterized constructor
    public Restaurant(int id, String name, String cuisine, double rating, int deliveryTime, String imagePath, String address) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.rating = rating;
        this.deliveryTime = deliveryTime;
        this.imagePath = imagePath;
        this.address = address;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Restaurant [id=" + id + ", name=" + name + ", cuisine=" + cuisine + ", rating=" + rating + ", deliveryTime=" + deliveryTime + "]";
    }
}
