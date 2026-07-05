<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.cravyfoods.pojo.User" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.cravyfoods.pojo.CartItem" %>
<%
    User loggedUser = (User) session.getAttribute("loggedUser");
    if (loggedUser == null) {
        response.sendRedirect("index.html");
        return;
    }
    @SuppressWarnings("unchecked")
    Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
    int cartCount = (cart != null) ? cart.size() : 0;
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - CravyFoods</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        .profile-wrapper {
            max-width: 800px;
            margin: 2rem auto;
            padding: 0 2rem;
        }
        .profile-hero {
            background: linear-gradient(135deg, var(--bg-card) 0%, #1e1e20 100%);
            border-radius: 20px;
            padding: 2.5rem;
            display: flex;
            align-items: center;
            gap: 2rem;
            border: 1px solid var(--border-color);
            margin-bottom: 2rem;
            position: relative;
            overflow: hidden;
        }
        .profile-hero::before {
            content: '';
            position: absolute;
            top: 0; left: 0; right: 0;
            height: 4px;
            background: linear-gradient(90deg, var(--primary), #ff8b00);
        }
        .profile-avatar {
            width: 90px;
            height: 90px;
            background: linear-gradient(135deg, var(--primary), #ff8b00);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 2.5rem;
            font-weight: 800;
            color: white;
            flex-shrink: 0;
            box-shadow: 0 0 30px rgba(255, 82, 0, 0.4);
        }
        .profile-hero-info h2 {
            font-size: 1.8rem;
            margin-bottom: 0.25rem;
        }
        .profile-hero-info p {
            color: var(--text-secondary);
            font-size: 0.95rem;
        }
        .profile-badge {
            display: inline-flex;
            align-items: center;
            gap: 0.4rem;
            background: rgba(255,82,0,0.1);
            color: var(--primary);
            border: 1px solid rgba(255,82,0,0.2);
            padding: 0.25rem 0.75rem;
            border-radius: 50px;
            font-size: 0.8rem;
            font-weight: 600;
            margin-top: 0.5rem;
        }
        .profile-form-card {
            background: var(--bg-card);
            border-radius: 16px;
            border: 1px solid var(--border-color);
            padding: 2rem;
            margin-bottom: 1.5rem;
        }
        .profile-form-card h3 {
            font-size: 1.1rem;
            margin-bottom: 1.5rem;
            color: var(--primary);
            display: flex;
            align-items: center;
            gap: 0.5rem;
            border-bottom: 1px solid var(--border-color);
            padding-bottom: 0.75rem;
        }
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 1rem;
        }
        .form-group input:disabled {
            opacity: 0.5;
            cursor: not-allowed;
        }
        .btn-update {
            width: 100%;
            padding: 0.9rem;
            background: linear-gradient(90deg, var(--primary), #ff8b00);
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 1rem;
            font-weight: 700;
            cursor: pointer;
            transition: all 0.2s ease;
            margin-top: 0.5rem;
            letter-spacing: 0.03em;
        }
        .btn-update:hover {
            opacity: 0.9;
            box-shadow: 0 4px 20px rgba(255,82,0,0.4);
            transform: translateY(-1px);
        }
        .profile-alert {
            padding: 0.9rem 1.2rem;
            border-radius: 10px;
            margin-bottom: 1.5rem;
            font-size: 0.9rem;
            font-weight: 500;
        }
        .profile-alert-success {
            background: rgba(16,185,129,0.1);
            color: var(--success);
            border: 1px solid rgba(16,185,129,0.2);
        }
        .profile-alert-error {
            background: rgba(239,68,68,0.1);
            color: var(--danger);
            border: 1px solid rgba(239,68,68,0.2);
        }
        @media (max-width: 600px) {
            .form-row { grid-template-columns: 1fr; }
            .profile-hero { flex-direction: column; text-align: center; }
        }
    </style>
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
                <a href="RestaurantServlet" class="nav-item">🏠 Home</a>
                <a href="CartServlet" class="nav-item cart-nav-btn">
                    🛒 Cart <% if(cartCount > 0) { %><span class="cart-badge"><%= cartCount %></span><% } %>
                </a>
                <a href="OrderHistoryServlet" class="nav-item">📦 Orders</a>
                <a href="LogoutServlet" class="btn-logout">Logout</a>
            </div>
        </div>
    </header>

    <div class="profile-wrapper">

        <!-- Alert Messages -->
        <%
            String paramSuccess = request.getParameter("success");
            String paramError = request.getParameter("error");
        %>
        <% if ("true".equals(paramSuccess)) { %>
            <div class="profile-alert profile-alert-success">✅ Profile updated successfully!</div>
        <% } else if ("wrongpassword".equals(paramError)) { %>
            <div class="profile-alert profile-alert-error">❌ Current password is incorrect. Please try again.</div>
        <% } else if ("updatefailed".equals(paramError)) { %>
            <div class="profile-alert profile-alert-error">❌ Profile update failed. Please try again.</div>
        <% } %>

        <!-- Profile Hero -->
        <div class="profile-hero">
            <div class="profile-avatar">
                <%= loggedUser.getUsername().substring(0, 1).toUpperCase() %>
            </div>
            <div class="profile-hero-info">
                <h2><%= loggedUser.getUsername() %></h2>
                <p><%= loggedUser.getEmail() %></p>
                <p><%= loggedUser.getPhone() %></p>
                <span class="profile-badge">🍕 CravyFoods Member</span>
            </div>
        </div>

        <!-- Edit Profile Form -->
        <form action="ProfileServlet" method="POST">
            <div class="profile-form-card">
                <h3>👤 Account Information</h3>
                <div class="form-row">
                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" id="username" value="<%= loggedUser.getUsername() %>" disabled>
                    </div>
                    <div class="form-group">
                        <label for="email">Email Address</label>
                        <input type="email" id="email" name="email" value="<%= loggedUser.getEmail() %>" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" value="<%= loggedUser.getPhone() %>" required>
                </div>
                <div class="form-group">
                    <label for="address">Delivery Address</label>
                    <textarea id="address" name="address" required style="height: 80px;"><%= loggedUser.getAddress() %></textarea>
                </div>
            </div>

            <div class="profile-form-card">
                <h3>🔒 Change Password</h3>
                <div class="form-row">
                    <div class="form-group">
                        <label for="currentPassword">Current Password *</label>
                        <input type="password" id="currentPassword" name="currentPassword" placeholder="Required to save changes" required>
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New Password (optional)</label>
                        <input type="password" id="newPassword" name="newPassword" placeholder="Leave blank to keep current">
                    </div>
                </div>
            </div>

            <button type="submit" class="btn-update">💾 Save Changes</button>
        </form>

        <div style="text-align:center; margin: 1.5rem 0;">
            <a href="RestaurantServlet" style="color: var(--text-secondary); font-size: 0.9rem;">← Back to Restaurants</a>
        </div>
    </div>

    <!-- Footer -->
    <footer>
        <div class="footer-content">
            <div class="footer-logo"><h2>CravyFoods</h2></div>
            <div class="footer-text">&copy; 2026 CravyFoods. Your Favourite Food, Delivered Fresh.</div>
        </div>
    </footer>
</body>
</html>
