package com.groceryapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RemoveItemFromCartServlet")
public class RemoveItemFromCartServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/grocery_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "satya123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cartItemIdStr = request.getParameter("cartItemId");

        if (cartItemIdStr == null) {
            response.sendRedirect("cart.jsp?error=missing_parameters");
            return;
        }

        int cartItemId;
        try {
            cartItemId = Integer.parseInt(cartItemIdStr.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("cart.jsp?error=invalid_id");
            return;
        }

        // Remove the item from the cart table in the database
        try {
            removeItemFromCart(cartItemId);
            response.sendRedirect("RetrieveCartFromDB");
        } catch (SQLException | IOException e) {

            e.printStackTrace();
            response.sendRedirect("cart.jsp?error=database_error");
        }
    }

    private void removeItemFromCart(int cartItemId) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM cart WHERE id=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, cartItemId);
                statement.executeUpdate();
            }
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Unable to load database driver.");
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
