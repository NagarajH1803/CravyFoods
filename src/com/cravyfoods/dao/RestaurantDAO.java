package com.cravyfoods.dao;

import java.util.List;
import com.cravyfoods.pojo.Restaurant;

/**
 * Data Access Object (DAO) interface for Restaurant operations.
 */
public interface RestaurantDAO {
    
    /**
     * Retrieves all restaurants from the database.
     * @return List of Restaurant objects
     */
    List<Restaurant> getAllRestaurants();
    
    /**
     * Retrieves a specific restaurant by its ID.
     * @param id the restaurant ID
     * @return Restaurant object if found, null otherwise
     */
    Restaurant getRestaurantById(int id);
}
