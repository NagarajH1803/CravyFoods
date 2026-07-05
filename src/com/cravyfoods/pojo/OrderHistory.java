package com.cravyfoods.pojo;

import java.sql.Timestamp;

/**
 * POJO class representing an Order from the historical records.
 * Belongs to a specific user.
 */
public class OrderHistory {
    private int id;
    private int userId;
    private Timestamp orderDate;
    private double totalAmount;
    private String status;
    private String itemsSummary;

    // Default constructor
    public OrderHistory() {}

    // Parameterized constructor
    public OrderHistory(int id, int userId, Timestamp orderDate, double totalAmount, String status, String itemsSummary) {
        this.id = id;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.itemsSummary = itemsSummary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemsSummary() {
        return itemsSummary;
    }

    public void setItemsSummary(String itemsSummary) {
        this.itemsSummary = itemsSummary;
    }

    @Override
    public String toString() {
        return "OrderHistory [id=" + id + ", userId=" + userId + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount + ", status=" + status + "]";
    }
}
