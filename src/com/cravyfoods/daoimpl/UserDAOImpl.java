package com.cravyfoods.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.cravyfoods.dao.UserDAO;
import com.cravyfoods.pojo.User;
import com.cravyfoods.util.DBConnection;

/**
 * JDBC-based implementation of UserDAO interface.
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public User validateUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            System.err.println("Error validating user login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return user;
    }

    @Override
    public boolean registerUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO users (username, password, email, phone, address) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPhone());
            pstmt.setString(5, user.getAddress());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(null, pstmt, conn);
        }
        return isSuccess;
    }

    @Override
    public boolean checkUserExists(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT id FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return exists;
    }

    @Override
    public boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "UPDATE users SET email = ?, phone = ?, address = ?, password = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getEmail());
            pstmt.setString(2, user.getPhone());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getId());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating user profile: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(null, pstmt, conn);
        }
        return isSuccess;
    }
}
