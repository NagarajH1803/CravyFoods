package com.cravyfoods.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.cravyfoods.dao.MenuDAO;
import com.cravyfoods.dao.RestaurantDAO;
import com.cravyfoods.daoimpl.MenuDAOImpl;
import com.cravyfoods.daoimpl.RestaurantDAOImpl;
import com.cravyfoods.pojo.Menu;
import com.cravyfoods.pojo.Restaurant;

/**
 * Controller servlet handling menu retrieval for a selected restaurant.
 */
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MenuDAO menuDAO;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
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

        String restaurantIdStr = request.getParameter("restaurantId");
        if (restaurantIdStr == null || restaurantIdStr.trim().isEmpty()) {
            response.sendRedirect("RestaurantServlet");
            return;
        }

        try {
            int restaurantId = Integer.parseInt(restaurantIdStr);
            
            // Fetch restaurant details
            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            
            // Fetch menu list for the restaurant
            List<Menu> menuList = menuDAO.getMenuByRestaurant(restaurantId);

            if (restaurant == null) {
                response.sendRedirect("RestaurantServlet");
                return;
            }

            // Bind values to request attributes
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuList", menuList);
            
            // Forward to menu.jsp
            request.getRequestDispatcher("menu.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("RestaurantServlet");
        }
    }
}
