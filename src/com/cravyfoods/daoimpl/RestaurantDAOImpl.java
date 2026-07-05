package com.cravyfoods.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cravyfoods.dao.RestaurantDAO;
import com.cravyfoods.pojo.Restaurant;
import com.cravyfoods.util.DBConnection;

/**
 * JDBC-based implementation of RestaurantDAO interface.
 */
public class RestaurantDAOImpl implements RestaurantDAO {

    @Override
    public List<Restaurant> getAllRestaurants() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Restaurant> list = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM restaurants ORDER BY rating DESC";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Restaurant r = new Restaurant();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImagePath(rs.getString("image_path"));
                r.setAddress(rs.getString("address"));
                list.add(r);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all restaurants: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public Restaurant getRestaurantById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Restaurant r = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM restaurants WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                r = new Restaurant();
                r.setId(rs.getInt("id"));
                r.setName(rs.getString("name"));
                r.setCuisine(rs.getString("cuisine"));
                r.setRating(rs.getDouble("rating"));
                r.setDeliveryTime(rs.getInt("delivery_time"));
                r.setImagePath(rs.getString("image_path"));
                r.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching restaurant by ID: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return r;
    }
}
