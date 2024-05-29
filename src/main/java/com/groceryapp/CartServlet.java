package com.groceryapp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if the user is logged in
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");
        String phone = (String) session.getAttribute("phone");

        if (username == null && email == null && phone == null) {
            response.sendRedirect("login.html");
            return;
        }

        // Retrieve item name and price from request parameters
        String itemName = request.getParameter("itemName");
        double itemPrice = getItemPriceFromDatabase(itemName);

        // If item price is not found, redirect back to index.jsp
        if (itemPrice == -1) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Insert the item into the cart table in the database
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO cart (item_name, item_price, username, email, phone) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, itemName);
                preparedStatement.setDouble(2, itemPrice);
                preparedStatement.setString(3, username);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, phone);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp"); // Redirect back to the main page
            return;
        }

        // Redirect back to the main page after adding the item to the cart
        response.sendRedirect("index.jsp");
    }

    private double getItemPriceFromDatabase(String itemName) {
        double itemPrice = -1; // Default value if item price is not found

        // Query to retrieve item price based on item name from grocery_items table
        String query = "SELECT price FROM grocery_items WHERE name = ?";

        try (Connection connection = DatabaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, itemName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    itemPrice = resultSet.getDouble("price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemPrice;
    }
}
