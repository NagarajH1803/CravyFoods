package com.cravyfoods.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.cravyfoods.dao.UserDAO;
import com.cravyfoods.daoimpl.UserDAOImpl;
import com.cravyfoods.pojo.User;

/**
 * Controller servlet handling user authentication.
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Validate credentials against MySQL database
        User user = userDAO.validateUser(username, password);

        if (user != null) {
            // Login successful: Create session and save user
            HttpSession session = request.getSession(true);
            session.setAttribute("loggedUser", user);
            
            // Redirect to the RestaurantServlet to display the listing
            response.sendRedirect("RestaurantServlet");
        } else {
            // Login failed: Redirect back with error message
            response.sendRedirect("index.html?error=true");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}
