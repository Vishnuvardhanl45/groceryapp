package com.groceryapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchItemsServlet")
public class SearchItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/grocery_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "satya123";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String query = request.getParameter("query");
        List<GroceryItem> searchResults = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM grocery_items WHERE name LIKE ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "%" + query + "%");
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    GroceryItem item = new GroceryItem(
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("image_url"),
                            rs.getInt("quantity_value"),
                            rs.getString("quantity_unit"));
                    searchResults.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("searchResults", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("searchResults.jsp");
        dispatcher.forward(request, response);
    }
}
