package com.cravyfoods.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.cravyfoods.dao.UserDAO;
import com.cravyfoods.daoimpl.UserDAOImpl;
import com.cravyfoods.pojo.User;

/**
 * Controller servlet handling user registration.
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
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
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Validate user existence
        if (userDAO.checkUserExists(username)) {
            response.sendRedirect("register.html?error=exists");
            return;
        }

        // Create POJO object
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email);
        newUser.setPhone(phone);
        newUser.setAddress(address);

        // Register in MySQL
        boolean success = userDAO.registerUser(newUser);

        if (success) {
            response.sendRedirect("index.html?registered=true");
        } else {
            response.sendRedirect("register.html?error=failed");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("register.html");
    }
}
