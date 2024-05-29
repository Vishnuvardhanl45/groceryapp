package com.groceryapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get username and password from the form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/grocery_db";
        String dbUsername = "root";
        String dbPassword = "satya123";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM admin WHERE username=? AND password=?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Admin credentials are valid, set session attribute and redirect to admin.html
                        HttpSession session = request.getSession();
                        session.setAttribute("adminUser", username);
                        response.sendRedirect("admin.html");
                    } else {
                        // Admin credentials are invalid, display error message
                        PrintWriter out = response.getWriter();
                        out.println("<script>alert('Invalid username or password');</script>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection error
            response.sendRedirect("error.html");
        }
    }
}
