package com.cravyfoods.dao;

import com.cravyfoods.pojo.User;

/**
 * Data Access Object (DAO) interface for User operations.
 */
public interface UserDAO {
    
    /**
     * Validates a user's login credentials against the database.
     * @param username the username
     * @param password the password
     * @return User object if valid, null otherwise
     */
    User validateUser(String username, String password);
    
    /**
     * Registers a new user in the database.
     * @param user User object containing new details
     * @return true if registration was successful, false otherwise
     */
    boolean registerUser(User user);
    
    /**
     * Checks if a username already exists in the database.
     * @param username the username to check
     * @return true if exists, false otherwise
     */
    boolean checkUserExists(String username);

    /**
     * Updates an existing user's profile details in the database.
     * @param user User object with updated details
     * @return true if update was successful, false otherwise
     */
    boolean updateUser(User user);
}
