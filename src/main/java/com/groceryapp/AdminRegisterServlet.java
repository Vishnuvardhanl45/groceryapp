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

@WebServlet("/AdminRegisterServlet")
public class AdminRegisterServlet extends HttpServlet {
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

            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery_db?useSSL=false", "root",
                    "satya123");

            String checkQuery = "SELECT * FROM admin WHERE email=? OR phone=?";
            pstmt = conn.prepareStatement(checkQuery);
            pstmt.setString(1, email);
            pstmt.setString(2, phone);
            rs = pstmt.executeQuery();

            if (rs.next()) {

                out.println("<script>alert('User already exists!');</script>");
            } else {

                String insertQuery = "INSERT INTO admin (name, username, email, phone, password) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(insertQuery);
                pstmt.setString(1, name);
                pstmt.setString(2, username);
                pstmt.setString(3, email);
                pstmt.setString(4, phone);
                pstmt.setString(5, password);
                pstmt.executeUpdate();

                out.println("<script>alert('Registration successful!');</script>");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<script>alert('An error occurred during registration. Please try again later.');</script>");
        } finally {

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
