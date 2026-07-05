package com.cravyfoods.pojo;

/**
 * POJO class representing an item added to the shopping cart.
 * Stored in the HTTP Session as a list or map of items.
 */
public class CartItem {
    private int menuId;
    private String name;
    private double price;
    private int quantity;
    private boolean isVeg;

    // Default constructor
    public CartItem() {}

    // Parameterized constructor
    public CartItem(int menuId, String name, double price, int quantity, boolean isVeg) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.isVeg = isVeg;
    }

    // Getters and Setters
    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isVeg() {
        return isVeg;
    }

    public void setVeg(boolean isVeg) {
        this.isVeg = isVeg;
    }

    // Helper method to get the total price for this item
    public double getTotalPrice() {
        return this.price * this.quantity;
    }

    @Override
    public String toString() {
        return "CartItem [menuId=" + menuId + ", name=" + name + ", price=" + price + ", quantity=" + quantity + ", isVeg=" + isVeg + "]";
    }
}
