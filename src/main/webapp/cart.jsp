<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<%@ page import="com.cravyfoods.pojo.CartItem" %>
<%@ page import="com.cravyfoods.pojo.User" %>
<%
    // Session Management Validation
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("index.html");
        return;
    }

    // Get cart from session
    @SuppressWarnings("unchecked")
    Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
    
    boolean isCartEmpty = (cart == null || cart.isEmpty());
    
    double subtotal = 0.0;
    if (!isCartEmpty) {
        for (CartItem item : cart.values()) {
            subtotal += item.getTotalPrice();
        }
    }
    
    double deliveryFee = isCartEmpty ? 0.0 : 40.0;
    double platformFee = isCartEmpty ? 0.0 : 5.0;
    double gst = isCartEmpty ? 0.0 : Math.round((subtotal * 0.05) * 100.0) / 100.0; // 5% GST
    double grandTotal = isCartEmpty ? 0.0 : (subtotal + deliveryFee + platformFee + gst);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - CravyFoods</title>
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
                    🛒 Cart<% if(!isCartEmpty) { %> <span class="cart-badge"><%= cart.size() %></span><% } %>
                </a>
                <a href="OrderHistoryServlet" class="nav-item">📦 Orders</a>
                <a href="ProfileServlet" class="nav-profile-link">👤 Profile</a>
                <a href="LogoutServlet" class="btn-logout">Logout</a>
            </div>
        </div>
    </header>

    <!-- Main Container -->
    <main class="main-container">
        <h2 class="section-title">Your Food Cart</h2>
        
        <% if (isCartEmpty) { %>
            <!-- Empty Cart State -->
            <div class="empty-cart-state">
                <div class="icon">🛒</div>
                <h2>Your Cart is Empty</h2>
                <p style="color: var(--text-secondary); margin-top: 0.5rem;">Looks like you haven't added any delicious foods to your cart yet.</p>
                <a href="RestaurantServlet" class="btn-shop">Explore Restaurants</a>
            </div>
        <% } else { %>
            <!-- Active Cart Layout -->
            <div class="cart-layout">
                
                <!-- Left: List of items -->
                <div class="cart-items-container">
                    <h3 style="margin-bottom: 1.5rem; border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">Items in Cart</h3>
                    
                    <% for (CartItem item : cart.values()) { %>
                        <div class="cart-item">
                            <!-- Veg/Non-Veg & Name -->
                            <div class="cart-item-details">
                                <div style="display: flex; align-items: center; gap: 0.5rem;">
                                    <% if (item.isVeg()) { %>
                                        <span class="type-badge type-veg" style="padding: 0; border: none; font-size: 0.8rem;"></span>
                                    <% } else { %>
                                        <span class="type-badge type-nonveg" style="padding: 0; border: none; font-size: 0.7rem;"></span>
                                    <% } %>
                                    <span class="cart-item-name"><%= item.getName() %></span>
                                </div>
                                <span class="cart-item-price">₹<%= String.format("%.2f", item.getPrice()) %> each</span>
                            </div>
                            
                            <!-- Quantity Controls -->
                            <div class="cart-item-qty">
                                <!-- Decrement Button -->
                                <a href="CartServlet?action=update&menuId=<%= item.getMenuId() %>&quantity=<%= item.getQuantity() - 1 %>" class="qty-btn">-</a>
                                <span class="qty-val"><%= item.getQuantity() %></span>
                                <!-- Increment Button -->
                                <a href="CartServlet?action=update&menuId=<%= item.getMenuId() %>&quantity=<%= item.getQuantity() + 1 %>" class="qty-btn">+</a>
                            </div>
                            
                            <!-- Total Price for this item -->
                            <div class="cart-item-total">
                                ₹<%= String.format("%.2f", item.getTotalPrice()) %>
                            </div>
                            
                            <!-- Remove Item -->
                            <a href="CartServlet?action=remove&menuId=<%= item.getMenuId() %>" class="btn-remove-item" title="Remove item">
                                🗑
                            </a>
                        </div>
                    <% } %>
                </div>
                
                <!-- Right: Billing Sidebar -->
                <div class="cart-summary">
                    <h3 style="border-bottom: 1px solid var(--border-color); padding-bottom: 0.75rem;">Bill Details</h3>
                    
                    <div class="summary-row">
                        <span class="label">Item Total:</span>
                        <span class="value">₹<%= String.format("%.2f", subtotal) %></span>
                    </div>
                    
                    <div class="summary-row">
                        <span class="label">Delivery Partner Fee:</span>
                        <span class="value">₹<%= String.format("%.2f", deliveryFee) %></span>
                    </div>

                    <div class="summary-row">
                        <span class="label">Platform Fee:</span>
                        <span class="value">₹<%= String.format("%.2f", platformFee) %></span>
                    </div>
                    
                    <div class="summary-row">
                        <span class="label">GST & Restaurant Taxes (5%):</span>
                        <span class="value">₹<%= String.format("%.2f", gst) %></span>
                    </div>
                    
                    <div class="summary-row total">
                        <span class="label">TO PAY:</span>
                        <span class="value">₹<%= String.format("%.2f", grandTotal) %></span>
                    </div>
                    
                    <!-- Checkout Form -->
                    <form action="CheckoutServlet" method="POST" style="margin-top: 1rem;">
                        <button type="submit" class="btn-checkout">
                            Confirm Order & Pay
                        </button>
                    </form>
                    
                    <!-- Delivery Address Reminder -->
                    <div style="font-size: 0.8rem; color: var(--text-secondary); background: var(--bg-input); padding: 1rem; border-radius: 8px; border: 1px solid var(--border-color); margin-top: 0.5rem;">
                        <p style="font-weight: 600; color: var(--text-primary); margin-bottom: 0.25rem;">📍 Delivering to:</p>
                        <p><%= loggedUser.getAddress() %></p>
                    </div>
                </div>
                
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
