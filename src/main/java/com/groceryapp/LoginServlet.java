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

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");

        String jdbcUrl = "jdbc:mysql://localhost:3306/grocery_db";
        String dbUsername = "root";
        String dbPassword = "satya123";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUsername, dbPassword)) {
            String query = "SELECT * FROM users WHERE (username = ? OR email = ? OR phone = ?) AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, username != null ? username : "");
                pstmt.setString(2, email != null ? email : "");
                pstmt.setString(3, phone != null ? phone : "");
                pstmt.setString(4, password);

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        HttpSession session = request.getSession();
                        if (username != null && !username.isEmpty()) {
                            session.setAttribute("username", rs.getString("username"));
                        } else if (email != null && !email.isEmpty()) {
                            session.setAttribute("email", rs.getString("email"));
                        } else if (phone != null && !phone.isEmpty()) {
                            session.setAttribute("phone", rs.getString("phone"));
                        }
                        response.sendRedirect("index.jsp");
                    } else {
                        PrintWriter out = response.getWriter();
                        out.println("<script>alert('Invalid username/email/phone or password');</script>");
                        out.println("<script>window.location.href='login.html';</script>");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html");
        }
    }
}
