package com.cravyfoods.pojo;

/**
 * POJO class representing a Menu Item in the CravyFoods application.
 * Belongs to a specific restaurant.
 */
public class Menu {
    private int id;
    private int restaurantId;
    private String name;
    private double price;
    private String description;
    private boolean isVeg;
    private String imagePath;

    // Default constructor
    public Menu() {}

    // Parameterized constructor
    public Menu(int id, int restaurantId, String name, double price, String description, boolean isVeg, String imagePath) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isVeg = isVeg;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean isVeg) {
        this.isVeg = isVeg;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Menu [id=" + id + ", restaurantId=" + restaurantId + ", name=" + name + ", price=" + price + ", isVeg=" + isVeg + "]";
    }
}
