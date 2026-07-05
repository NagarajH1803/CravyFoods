<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.cravyfoods.pojo.Restaurant" %>
<%@ page import="com.cravyfoods.pojo.Menu" %>
<%@ page import="com.cravyfoods.pojo.User" %>
<%@ page import="com.cravyfoods.pojo.CartItem" %>
<%
    // Session Management Validation
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("index.html");
        return;
    }

    // Retrieve restaurant and menu list from request scope
    Restaurant restaurant = (Restaurant) request.getAttribute("restaurant");
    List<Menu> menuList = (List<Menu>) request.getAttribute("menuList");

    if (restaurant == null || menuList == null) {
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
    <title><%= restaurant.getName() %> - Menu - CravyFoods</title>
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

    <!-- Main Container -->
    <main class="main-container">
        
        <!-- Back Button -->
        <a href="RestaurantServlet" class="nav-item" style="display: inline-flex; align-items: center; gap: 0.5rem; margin-bottom: 1.5rem; color: var(--primary); font-weight: 600;">
            ← Back to Restaurants
        </a>

        <!-- Restaurant Details Header Card -->
        <div class="restaurant-header">
            <div class="restaurant-header-img">
                <img src="<%= restaurant.getImagePath() %>" alt="<%= restaurant.getName() %>" onload="this.classList.add('loaded')" onerror="this.src='https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=300&q=80'; this.classList.add('loaded')">
            </div>
            
            <div class="restaurant-header-details">
                <h2><%= restaurant.getName() %></h2>
                <p style="color: var(--text-secondary); margin-bottom: 0.5rem;"><%= restaurant.getCuisine() %></p>
                <p style="color: var(--text-secondary); font-size: 0.9rem;">📍 <%= restaurant.getAddress() %></p>
                
                <div class="meta-row">
                    <span class="rating-badge">
                        ★ <%= restaurant.getRating() %>
                    </span>
                    <span>|</span>
                    <span>🕒 <%= restaurant.getDeliveryTime() %> mins delivery time</span>
                </div>
            </div>
        </div>

        <h2 class="section-title">Menu Items</h2>
        
        <!-- Menu Items List -->
        <div class="menu-list">
            <% 
                for (Menu m : menuList) { 
            %>
                <div class="menu-card">
                    <!-- Menu Image -->
                    <div class="menu-card-img">
                        <img src="<%= m.getImagePath() %>" alt="<%= m.getName() %>" onload="this.classList.add('loaded')" onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=200&q=80'; this.classList.add('loaded')">
                    </div>

                    <!-- Menu Info -->
                    <div class="menu-info">
                        <div class="menu-name-row">
                            <h3 class="menu-name"><%= m.getName() %></h3>
                            
                            <!-- Veg/Non-Veg Badge -->
                            <% if (m.isVeg()) { %>
                                <span class="type-badge type-veg">Veg</span>
                            <% } else { %>
                                <span class="type-badge type-nonveg">Non-Veg</span>
                            <% } %>
                        </div>
                        
                        <div class="menu-price">₹<%= String.format("%.2f", m.getPrice()) %></div>
                        <p class="menu-description"><%= m.getDescription() %></p>
                    </div>

                    <!-- Add to Cart Action -->
                    <div class="menu-action">
                        <form action="CartServlet" method="POST">
                            <input type="hidden" name="action" value="add">
                            <input type="hidden" name="menuId" value="<%= m.getId() %>">
                            <input type="hidden" name="restaurantId" value="<%= restaurant.getId() %>">
                            <button type="submit" class="btn-add-cart">+ Add to Cart</button>
                        </form>
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
