package com.cravyfoods.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.cravyfoods.dao.OrderHistoryDAO;
import com.cravyfoods.pojo.OrderHistory;
import com.cravyfoods.util.DBConnection;

/**
 * JDBC-based implementation of OrderHistoryDAO interface.
 */
public class OrderHistoryDAOImpl implements OrderHistoryDAO {

    @Override
    public boolean saveOrder(OrderHistory order) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean isSuccess = false;

        try {
            conn = DBConnection.getConnection();
            String sql = "INSERT INTO orders_history (user_id, total_amount, status, items_summary) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setString(3, order.getStatus() != null ? order.getStatus() : "Delivered");
            pstmt.setString(4, order.getItemsSummary());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }
        } catch (SQLException e) {
            System.err.println("Error saving order history: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(null, pstmt, conn);
        }
        return isSuccess;
    }

    @Override
    public List<OrderHistory> getOrdersByUser(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrderHistory> list = new ArrayList<>();

        try {
            conn = DBConnection.getConnection();
            String sql = "SELECT * FROM orders_history WHERE user_id = ? ORDER BY order_date DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderHistory order = new OrderHistory();
                order.setId(rs.getInt("id"));
                order.setUserId(rs.getInt("user_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setItemsSummary(rs.getString("items_summary"));
                list.add(order);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching orders for user " + userId + ": " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBConnection.closeResources(rs, pstmt, conn);
        }
        return list;
    }
}
