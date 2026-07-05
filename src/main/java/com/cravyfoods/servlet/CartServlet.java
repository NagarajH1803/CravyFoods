package com.cravyfoods.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.cravyfoods.dao.MenuDAO;
import com.cravyfoods.daoimpl.MenuDAOImpl;
import com.cravyfoods.pojo.CartItem;
import com.cravyfoods.pojo.Menu;

/**
 * Controller servlet handling cart operations (Add, Update, Remove, View).
 * Cart items are stored inside the user's HTTP Session.
 */
@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MenuDAO menuDAO;

    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
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

        String action = request.getParameter("action");
        if (action == null) {
            action = "view";
        }

        // Retrieve or initialize session cart
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        try {
            if ("add".equalsIgnoreCase(action)) {
                int menuId = Integer.parseInt(request.getParameter("menuId"));
                int quantity = 1;
                if (request.getParameter("quantity") != null) {
                    quantity = Integer.parseInt(request.getParameter("quantity"));
                }

                if (cart.containsKey(menuId)) {
                    // Item already in cart, increment quantity
                    CartItem existingItem = cart.get(menuId);
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                } else {
                    // New item, fetch from database and add
                    Menu menu = menuDAO.getMenuById(menuId);
                    if (menu != null) {
                        CartItem item = new CartItem(
                            menu.getId(),
                            menu.getName(),
                            menu.getPrice(),
                            quantity,
                            menu.isVeg()
                        );
                        cart.put(menuId, item);
                    }
                }
                
                // Redirect back to the restaurant's menu
                String restaurantId = request.getParameter("restaurantId");
                if (restaurantId != null) {
                    response.sendRedirect("MenuServlet?restaurantId=" + restaurantId);
                } else {
                    response.sendRedirect("RestaurantServlet");
                }
                return;

            } else if ("update".equalsIgnoreCase(action)) {
                int menuId = Integer.parseInt(request.getParameter("menuId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));

                if (cart.containsKey(menuId)) {
                    if (quantity <= 0) {
                        cart.remove(menuId);
                    } else {
                        cart.get(menuId).setQuantity(quantity);
                    }
                }
                response.sendRedirect("cart.jsp");
                return;

            } else if ("remove".equalsIgnoreCase(action)) {
                int menuId = Integer.parseInt(request.getParameter("menuId"));
                cart.remove(menuId);
                response.sendRedirect("cart.jsp");
                return;

            } else if ("view".equalsIgnoreCase(action)) {
                response.sendRedirect("cart.jsp");
                return;
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing ID or quantity in CartServlet: " + e.getMessage());
            response.sendRedirect("RestaurantServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
