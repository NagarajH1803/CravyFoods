package com.cravyfoods.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.cravyfoods.dao.OrderHistoryDAO;
import com.cravyfoods.daoimpl.OrderHistoryDAOImpl;
import com.cravyfoods.pojo.OrderHistory;
import com.cravyfoods.pojo.User;

/**
 * Controller servlet to fetch and display the logged-in user's order history.
 */
@WebServlet("/OrderHistoryServlet")
public class OrderHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderHistoryDAO orderHistoryDAO;

    @Override
    public void init() throws ServletException {
        orderHistoryDAO = new OrderHistoryDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Session Management Check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("index.html");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        
        // Fetch order history records from MySQL database
        List<OrderHistory> orders = orderHistoryDAO.getOrdersByUser(user.getId());

        // Bind data to request and forward to orderHistory.jsp
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("orderHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
