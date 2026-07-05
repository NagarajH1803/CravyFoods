package com.cravyfoods.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cravyfoods.dao.MenuDAO;
import com.cravyfoods.pojo.Menu;
import com.cravyfoods.util.DBConnection;

/**
 * JDBC-based implementation of MenuDAO interface.
 */
public class MenuDAOImpl implements MenuDAO {

    @Override
    public List<Menu> getMenuByRestaurant(int restaurantId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Menu> list = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM menu WHERE restaurant_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, restaurantId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Menu m = new Menu();
                m.setId(rs.getInt("id"));
                m.setRestaurantId(rs.getInt("restaurant_id"));
                m.setName(rs.getString("name"));
                m.setPrice(rs.getDouble("price"));
                m.setDescription(rs.getString("description"));
                m.setVeg(rs.getBoolean("is_veg"));
                m.setImagePath(rs.getString("image_path"));
                list.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching menu for restaurant " + restaurantId + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return list;
    }

    @Override
    public Menu getMenuById(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Menu m = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM menu WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                m = new Menu();
                m.setId(rs.getInt("id"));
                m.setRestaurantId(rs.getInt("restaurant_id"));
                m.setName(rs.getString("name"));
                m.setPrice(rs.getDouble("price"));
                m.setDescription(rs.getString("description"));
                m.setVeg(rs.getBoolean("is_veg"));
                m.setImagePath(rs.getString("image_path"));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching menu item by ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return m;
    }
}
