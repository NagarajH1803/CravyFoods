package com.cravyfoods.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class to manage MySQL database connections in the CravyFoods application.
 * Follows standard JDBC connection guidelines.
 */
public class DBConnection {

    // Database Connection Parameters
    // Modify URL, username, or password here to match your local MySQL configuration
    private static final String URL = "jdbc:mysql://localhost:3306/cravyfoods_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "nagu"; // Set to your MySQL root password

    // JDBC Driver Name
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            // Load the MySQL JDBC Driver class dynamically
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            System.err.println("CRITICAL ERROR: MySQL JDBC Driver not found in the classpath!");
            e.printStackTrace();
        }
    }

    /**
     * Establishes and returns a connection to the MySQL database.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * Safely closes the given database resources to prevent connection leaks.
     * @param rs ResultSet to close
     * @param stmt Statement or PreparedStatement to close
     * @param conn Connection to close
     */
    public static void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing ResultSet: " + e.getMessage());
        }

        try {
            if (stmt != null && !stmt.isClosed()) {
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing Statement: " + e.getMessage());
        }

        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing Connection: " + e.getMessage());
        }
    }
}
