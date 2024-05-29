package com.groceryapp;

import java.io.IOException;
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

@WebServlet("/CalculateTotalServlet")
public class CalculateTotalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] selectedItems = request.getParameterValues("selectedItems[]");
        double totalAmount = 0.0;

        // JDBC URL, username, and password
        String jdbcUrl = "jdbc:mysql://localhost:3306/grocery_db";
        String username = "root";
        String password = "satya123";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Prepare the SQL statement to retrieve price for each selected item
            String sql = "SELECT item_price FROM grocery_items WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);

            // Iterate over each selected item and retrieve its price from the database
            for (String itemName : selectedItems) {
                statement.setString(1, itemName);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    double itemPrice = resultSet.getDouble("price");
                    totalAmount += itemPrice;
                }
            }

            // Close the database connection
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        // Redirect to payment page with the total amount
        response.sendRedirect("redirect_to_razorpay.jsp?totalAmount=" + totalAmount);
    }
}
