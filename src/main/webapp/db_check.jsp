<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Database Connection Diagnosis</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; background-color: #f4f4f9; color: #333; }
        .card { background: white; padding: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-bottom: 20px; }
        .success { color: green; font-weight: bold; }
        .error { color: red; font-weight: bold; }
        pre { background: #eee; padding: 15px; border-radius: 5px; overflow-x: auto; }
    </style>
</head>
<body>
    <h2>CravyFoods Database Diagnostic Page</h2>
    
    <div class="card">
        <h3>1. Driver Class Verification</h3>
        <%
            String driverClass = "com.mysql.cj.jdbc.Driver";
            try {
                Class.forName(driverClass);
                out.println("<p class='success'>✓ JDBC Driver class '" + driverClass + "' successfully found in classpath.</p>");
            } catch (ClassNotFoundException e) {
                out.println("<p class='error'>✗ JDBC Driver class '" + driverClass + "' not found!</p>");
                out.println("<p><b>Error Details:</b></p><pre>");
                e.printStackTrace(new java.io.PrintWriter(out));
                out.println("</pre>");
            }
        %>
    </div>

    <div class="card">
        <h3>2. Connection Verification</h3>
        <%
            String url = "jdbc:mysql://localhost:3306/cravyfoods_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
            String username = "root";
            String password = "nagu";
            
            try (Connection conn = DriverManager.getConnection(url, username, password)) {
                out.println("<p class='success'>✓ Successfully connected to MySQL database 'cravyfoods_db'.</p>");
                
                // Show users count
                try (Statement stmt = conn.createStatement();
                     ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users")) {
                    if (rs.next()) {
                        out.println("<p><b>Number of registered users in database:</b> " + rs.getInt(1) + "</p>");
                    }
                }
            } catch (SQLException e) {
                out.println("<p class='error'>✗ Failed to connect to MySQL database!</p>");
                out.println("<p><b>Error Details:</b></p><pre>");
                e.printStackTrace(new java.io.PrintWriter(out));
                out.println("</pre>");
            }
        %>
    </div>
</body>
</html>
