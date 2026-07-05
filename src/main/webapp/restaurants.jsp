<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.cravyfoods.pojo.Restaurant" %>
<%@ page import="com.cravyfoods.pojo.User" %>
<%@ page import="com.cravyfoods.pojo.CartItem" %>
<%
    // Session Management Validation
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("index.html");
        return;
    }

    // Retrieve restaurants list from request scope
    List<Restaurant> restaurants = (List<Restaurant>) request.getAttribute("restaurants");
    if (restaurants == null) {
        response.sendRedirect("RestaurantServlet");
        return;
    }

    // Get cart count
    @SuppressWarnings("unchecked")
    Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
    int cartCount = (cart != null) ? cart.size() : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurants - CravyFoods</title>
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
                <a href="CartServlet" class="nav-item cart-nav-btn">
                    🛒 Cart<% if(cartCount > 0) { %> <span class="cart-badge"><%= cartCount %></span><% } %>
                </a>
                <a href="OrderHistoryServlet" class="nav-item">📦 Orders</a>
                <a href="ProfileServlet" class="nav-profile-link">👤 Profile</a>
                <a href="LogoutServlet" class="btn-logout">Logout</a>
            </div>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="hero">
        <h2>Discover Amazing Food</h2>
        <p>Order fresh meals, crispy dosas, and spicy biryanis from the most legendary culinary spots straight to your home.</p>
    </section>

    <!-- Main Container -->
    <main class="main-container">
        <h2 class="section-title">Popular Restaurants Near You</h2>
        
        <div class="restaurant-grid">
            <% 
                for (Restaurant r : restaurants) { 
            %>
                <div class="restaurant-card">
                    <div class="restaurant-image-container">
                        <img src="<%= r.getImagePath() %>" alt="<%= r.getName() %>"
                             onload="this.classList.add('loaded')"
                             onerror="this.src='https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=400&q=80'; this.classList.add('loaded')">
                    </div>
                    
                    <div class="restaurant-info">
                        <h3 class="restaurant-name"><%= r.getName() %></h3>
                        <p class="restaurant-cuisine"><%= r.getCuisine() %></p>
                        <p class="restaurant-address">📍 <%= r.getAddress() %></p>
                        
                        <div class="restaurant-meta">
                            <span class="rating-badge rating-high">
                                ★ <%= r.getRating() %>
                            </span>
                            <span class="delivery-badge">
                                🕒 <%= r.getDeliveryTime() %> MINS
                            </span>
                        </div>
                        
                        <!-- View Menu Button -->
                        <a href="MenuServlet?restaurantId=<%= r.getId() %>" class="btn-shop" style="text-align: center; margin-top: 1rem; width: 100%; padding: 0.5rem 0; display:block;">
                            View Menu →
                        </a>
                    </div>
                </div>
            <% 
                } 
            %>
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
