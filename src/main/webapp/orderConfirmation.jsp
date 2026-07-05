<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Collection" %>
<%@ page import="com.cravyfoods.pojo.CartItem" %>
<%@ page import="com.cravyfoods.pojo.User" %>
<%
    // Session Management Validation
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("index.html");
        return;
    }

    // Retrieve order checkout attributes from request scope
    Collection<CartItem> orderedItems = (Collection<CartItem>) request.getAttribute("orderedItems");
    Double totalAmount = (Double) request.getAttribute("totalAmount");
    String customerName = (String) request.getAttribute("customerName");
    String deliveryAddress = (String) request.getAttribute("deliveryAddress");
    String customerPhone = (String) request.getAttribute("customerPhone");
    String customerEmail = (String) request.getAttribute("customerEmail");

    if (orderedItems == null || totalAmount == null) {
        // Direct access protection
        response.sendRedirect("RestaurantServlet");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed - CravyFoods</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <!-- Header / Navbar -->
    <header>
        <div class="navbar">
            <div class="nav-brand">
                <a href="RestaurantServlet">
                    <h1>CravyFoods</h1>
                </a>
                <span>Food Delivery</span>
            </div>
            
            <div class="nav-links">
                <span class="nav-user">
                    <span class="dot"></span>
                    Hey, <%= loggedUser.getUsername() %>
                </span>
                <a href="RestaurantServlet" class="nav-item">🏠 Restaurants</a>
                <a href="CartServlet" class="nav-item cart-nav-btn">🛒 Cart</a>
                <a href="OrderHistoryServlet" class="nav-item">📦 Orders</a>
                <a href="ProfileServlet" class="nav-profile-link">👤 Profile</a>
                <a href="LogoutServlet" class="btn-logout">Logout</a>
            </div>
        </div>
    </header>

    <!-- Main Container -->
    <main class="main-container">
        
        <div class="confirmation-card">
            
            <!-- Pulse Check Icon -->
            <div class="success-icon">✓</div>
            
            <h2 style="font-size: 2rem; margin-bottom: 0.5rem; color: var(--success);">Order Placed Successfully!</h2>
            <p style="color: var(--text-secondary);">Your food is being prepared at the restaurant and will be delivered shortly.</p>
            
            <!-- Order Details -->
            <div class="confirmation-details">
                <h3>Delivery Details</h3>
                <div class="detail-row">
                    <span class="detail-label">Customer Name:</span>
                    <span class="detail-value"><%= customerName %></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Phone Number:</span>
                    <span class="detail-value"><%= customerPhone %></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Email Address:</span>
                    <span class="detail-value"><%= customerEmail %></span>
                </div>
                <div class="detail-row">
                    <span class="detail-label">Deliver To:</span>
                    <span class="detail-value"><%= deliveryAddress %></span>
                </div>
                
                <h3 style="margin-top: 1.5rem;">Order Summary</h3>
                <% for (CartItem item : orderedItems) { %>
                    <div class="detail-row">
                        <span class="detail-label">
                            <%= item.getName() %> <span style="color: var(--primary);">x <%= item.getQuantity() %></span>
                        </span>
                        <span class="detail-value">₹<%= String.format("%.2f", item.getTotalPrice()) %></span>
                    </div>
                <% } %>
                
                <div class="detail-row" style="border-top: 1px dashed var(--border-color); padding-top: 0.75rem; margin-top: 0.75rem; font-weight: 700; font-size: 1.1rem;">
                    <span style="color: var(--text-primary);">Total Paid:</span>
                    <span style="color: var(--primary);">₹<%= String.format("%.2f", totalAmount + 45.0 + (totalAmount * 0.05)) %></span>
                </div>
            </div>
            
            <!-- Action buttons -->
            <div style="display: flex; gap: 1rem; justify-content: center;">
                <a href="RestaurantServlet" class="btn-shop" style="margin-top: 0;">Order More Food</a>
                <a href="OrderHistoryServlet" class="btn-logout" style="display: flex; align-items: center; border-radius: 8px; padding: 0.75rem 1.5rem; font-weight: 600;">View Order History</a>
            </div>
        </div>

    </main>

    <!-- Footer -->
    <footer>
        <div class="footer-content">
            <div class="footer-logo">
                <h2>CravyFoods</h2>
            </div>
            <div class="footer-text">
                &copy; 2026 CravyFoods. Your Favourite Food, Delivered Fresh.
            </div>
        </div>
    </footer>

</body>
</html>
