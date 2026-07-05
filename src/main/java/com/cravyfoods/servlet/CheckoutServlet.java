package com.cravyfoods.servlet;

import java.io.IOException;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.cravyfoods.dao.OrderHistoryDAO;
import com.cravyfoods.daoimpl.OrderHistoryDAOImpl;
import com.cravyfoods.pojo.CartItem;
import com.cravyfoods.pojo.OrderHistory;
import com.cravyfoods.pojo.User;

/**
 * Controller servlet to process the final checkout of order.
 * Computes total, logs history in MySQL, clears cart, and forwards to confirmation page.
 */
@WebServlet("/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderHistoryDAO orderHistoryDAO;

    @Override
    public void init() throws ServletException {
        orderHistoryDAO = new OrderHistoryDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Session Management Check
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("index.html");
            return;
        }

        User user = (User) session.getAttribute("loggedUser");
        
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart.jsp?error=empty");
            return;
        }

        // Compute total amount and build items summary
        double totalAmount = 0.0;
        StringBuilder summaryBuilder = new StringBuilder();
        int index = 0;

        for (CartItem item : cart.values()) {
            totalAmount += item.getTotalPrice();
            
            if (index > 0) {
                summaryBuilder.append(", ");
            }
            summaryBuilder.append(item.getName())
                          .append(" x ")
                          .append(item.getQuantity());
            index++;
        }

        String itemsSummary = summaryBuilder.toString();

        // Create Order History instance
        OrderHistory order = new OrderHistory();
        order.setUserId(user.getId());
        order.setTotalAmount(totalAmount);
        order.setStatus("Delivered");
        order.setItemsSummary(itemsSummary);

        // Save order to MySQL database using DAO
        boolean isSaved = orderHistoryDAO.saveOrder(order);

        if (isSaved) {
            // Bind order confirmation attributes to request
            request.setAttribute("orderedItems", cart.values());
            request.setAttribute("totalAmount", totalAmount);
            request.setAttribute("customerName", user.getUsername());
            request.setAttribute("deliveryAddress", user.getAddress());
            request.setAttribute("customerPhone", user.getPhone());
            request.setAttribute("customerEmail", user.getEmail());

            // Clear session cart since order has been confirmed
            session.removeAttribute("cart");

            // Forward to orderConfirmation.jsp
            request.getRequestDispatcher("orderConfirmation.jsp").forward(request, response);
        } else {
            response.sendRedirect("cart.jsp?error=checkout_failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("cart.jsp");
    }
}
