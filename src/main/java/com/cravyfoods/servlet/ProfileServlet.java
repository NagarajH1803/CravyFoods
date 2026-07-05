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
 * Controller servlet handling user profile view and update.
 */
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("index.html");
            return;
        }
        // Forward to profile page with current user details
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedUser") == null) {
            response.sendRedirect("index.html");
            return;
        }

        User loggedUser = (User) session.getAttribute("loggedUser");

        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String newPassword = request.getParameter("newPassword");
        String currentPassword = request.getParameter("currentPassword");

        // Validate current password
        User verifiedUser = userDAO.validateUser(loggedUser.getUsername(), currentPassword);
        if (verifiedUser == null) {
            response.sendRedirect("ProfileServlet?error=wrongpassword");
            return;
        }

        // Update the user object
        loggedUser.setEmail(email);
        loggedUser.setPhone(phone);
        loggedUser.setAddress(address);
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            loggedUser.setPassword(newPassword);
        }

        boolean success = userDAO.updateUser(loggedUser);

        if (success) {
            // Refresh session with updated user
            session.setAttribute("loggedUser", loggedUser);
            response.sendRedirect("ProfileServlet?success=true");
        } else {
            response.sendRedirect("ProfileServlet?error=updatefailed");
        }
    }
}
