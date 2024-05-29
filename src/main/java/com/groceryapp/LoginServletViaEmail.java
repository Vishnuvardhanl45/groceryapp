package com.groceryapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServletViaEmail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get username, email, phone, and password from the form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        // Database connection parameters
        String jdbcUrl = "jdbc:mysql://localhost:3306/grocery_db";
        String dbUsername = "root";
        String dbPassword = "satya123";

        // Initialize database connection and prepared statement
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM users WHERE (username=? OR email=? OR phone=?) AND password=?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, phone);
                pstmt.setString(4, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // User credentials are valid, set session attribute and redirect to index.jsp
                        HttpSession session = request.getSession();
                        session.setAttribute("email", email);
                        response.sendRedirect("index.jsp");
                    } else {
                        // User credentials are invalid, display error message
                        PrintWriter out = response.getWriter();
                        out.println("<script>alert('Invalid username/email/phone or password');</script>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

            response.sendRedirect("error.html");
        }
    }
}