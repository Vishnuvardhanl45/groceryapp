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

@WebServlet("/RemoveItemFromDatabaseServlet")
public class RemoveItemFromDatabaseServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/grocery_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "satya123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        String itemPriceStr = request.getParameter("itemPrice");

        // Check for null parameters
        if (itemName == null || itemPriceStr == null) {
            response.sendRedirect("removeForm.html?error=missing_parameters");
            return;
        }

        // Trim parameters
        itemName = itemName.trim();
        itemPriceStr = itemPriceStr.trim();

        double itemPrice;
        try {
            itemPrice = Double.parseDouble(itemPriceStr);
        } catch (NumberFormatException e) {
         
            response.sendRedirect("removeForm.html?error=invalid_price");
            return;
        }

        // Remove the item from the database
        try {
            removeItemFromDatabase(itemName, itemPrice);
            response.sendRedirect("update.jsp");
        } catch (SQLException e) {
            // Handle database error
            e.printStackTrace();
            response.sendRedirect("removeForm.html?error=database_error");
        }
    }

    private void removeItemFromDatabase(String itemName, double itemPrice) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM grocery_items WHERE name=? AND price=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, itemName);
                statement.setDouble(2, itemPrice);
                statement.executeUpdate();
            }
        }
    }
}
