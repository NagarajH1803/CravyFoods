<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cravyfoods.pojo.OrderHistory" %>
<%@ page import="com.cravyfoods.pojo.User" %>
<%
    // Session Management Validation
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("index.html");
        return;
    }

    // Retrieve order list from request scope
    List<OrderHistory> orders = (List<OrderHistory>) request.getAttribute("orders");
    if (orders == null) {
        // Direct access protection
        response.sendRedirect("OrderHistoryServlet");
        return;
    }

    // Date formatting helper
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order History - CravyFoods</title>
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
        
        <h2 class="section-title">Your Order History</h2>
        
        <% if (orders.isEmpty()) { %>
            <!-- Empty Order History State -->
            <div class="empty-cart-state">
                <div class="icon">📜</div>
                <h2>No Orders Found</h2>
                <p style="color: var(--text-secondary); margin-top: 0.5rem;">You haven't placed any food orders yet.</p>
                <a href="RestaurantServlet" class="btn-shop">Browse Restaurants</a>
            </div>
        <% } else { %>
            <!-- History Card Listing -->
            <div class="history-list">
                <% for (OrderHistory order : orders) { %>
                    <div class="history-card">
                        
                        <!-- History Header -->
                        <div class="history-header">
                            <div class="order-id-date">
                                <h3>Order #<%= order.getId() %></h3>
                                <span class="date">
                                    Placed on: <%= order.getOrderDate() != null ? dateFormat.format(order.getOrderDate()) : "N/A" %>
                                </span>
                            </div>
                            
                            <div class="history-meta">
                                <div class="order-amount">₹<%= String.format("%.2f", order.getTotalAmount()) %></div>
                                <span class="status-badge status-delivered">
                                    <%= order.getStatus() %>
                                </span>
                            </div>
                        </div>
                        
                        <!-- History Body -->
                        <div class="history-body">
                            <h4>Items Ordered</h4>
                            <p class="history-items"><%= order.getItemsSummary() %></p>
                        </div>
                        
                    </div>
                <% } %>
            </div>
        <% } %>
        
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
