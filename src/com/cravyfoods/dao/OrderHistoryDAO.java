package com.cravyfoods.dao;

import java.util.List;
import com.cravyfoods.pojo.OrderHistory;

/**
 * Data Access Object (DAO) interface for Order History operations.
 */
public interface OrderHistoryDAO {
    
    /**
     * Saves a new order record into the database.
     * @param order the OrderHistory object
     * @return true if saved successfully, false otherwise
     */
    boolean saveOrder(OrderHistory order);
    
    /**
     * Retrieves all past orders placed by a specific user.
     * @param userId the user's ID
     * @return List of OrderHistory objects
     */
    List<OrderHistory> getOrdersByUser(int userId);
}
