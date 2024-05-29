package com.groceryapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<GroceryItem> groceryItems = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT name, price, quantity_value, quantity_unit, image_url FROM grocery_items";
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double price = resultSet.getDouble("price");
                    int quantityValue = resultSet.getInt("quantity_value");
                    String quantityUnit = resultSet.getString("quantity_unit");
                    String imageURL = resultSet.getString("image_url");
                    groceryItems.add(new GroceryItem(name, price, imageURL, quantityValue, quantityUnit));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }

        // Set groceryItems and quantityUnits as request attributes
        request.setAttribute("groceryItems", groceryItems);
        request.setAttribute("quantityUnits", QuantityUnits.getUnits());

        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}
