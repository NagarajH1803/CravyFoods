package com.cravyfoods.dao;

import java.util.List;
import com.cravyfoods.pojo.Menu;

/**
 * Data Access Object (DAO) interface for Menu operations.
 */
public interface MenuDAO {
    
    /**
     * Retrieves all menu items belonging to a specific restaurant.
     * @param restaurantId the restaurant's ID
     * @return List of Menu objects
     */
    List<Menu> getMenuByRestaurant(int restaurantId);
    
    /**
     * Retrieves a specific menu item by its ID.
     * @param id the menu item ID
     * @return Menu object if found, null otherwise
     */
    Menu getMenuById(int id);
}
