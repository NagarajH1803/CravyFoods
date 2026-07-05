package com.cravyfoods.servlet;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.cravyfoods.dao.RestaurantDAO;
import com.cravyfoods.daoimpl.RestaurantDAOImpl;
import com.cravyfoods.pojo.Restaurant;

/**
 * Controller servlet handling restaurant retrieval and display.
 */
@WebServlet("/RestaurantServlet")
public class RestaurantServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
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

        // Fetch restaurants from MySQL database
        List<Restaurant> restaurantList = restaurantDAO.getAllRestaurants();

        // Bind data to request and forward to restaurants.jsp
        request.setAttribute("restaurants", restaurantList);
        request.getRequestDispatcher("restaurants.jsp").forward(request, response);
    }
}
