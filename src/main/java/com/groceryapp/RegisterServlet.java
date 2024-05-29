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

public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PrintWriter out = response.getWriter();

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery_db?useSSL=false", "root",
                    "satya123");

            // Check if the user already exists
            String checkQuery = "SELECT * FROM users WHERE email=? OR phone=?";
            pstmt = conn.prepareStatement(checkQuery);
            pstmt.setString(1, email);
            pstmt.setString(2, phone);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                out.println("<script>alert('User already exists!'); window.location.href = 'register.html';</script>");
            } else {
                // Insert the new user into the database
                String insertQuery = "INSERT INTO users (name, username, email, phone, password) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertQuery);
                pstmt.setString(1, name);
                pstmt.setString(2, username);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setString(5, password);
                pstmt.executeUpdate();

                // Alert success and redirect to login page
                out.println("<script>alert('Registration successful!'); window.location.href = 'login.html';</script>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println(
                    "<script>alert('An error occurred during registration. Please try again later.'); window.location.href = 'register.html';</script>");
        } finally {
            // Close resources
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
