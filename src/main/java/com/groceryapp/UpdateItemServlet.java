package com.groceryapp;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@MultipartConfig
public class UpdateItemServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/grocery_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "satya123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String itemName = request.getParameter("itemName");
        String itemPriceStr = request.getParameter("itemPrice");
        String newItemName = request.getParameter("newItemName");
        String newItemPriceStr = request.getParameter("newItemPrice");
        Part itemImagePart = request.getPart("itemImage");

        if (itemName == null || itemPriceStr == null || newItemName == null || newItemPriceStr == null) {
            response.sendRedirect("update.jsp?error=missing_parameters");
            return;
        }

        itemName = itemName.trim();
        newItemName = newItemName.trim();

        double itemPrice;
        double newItemPrice;
        try {
            itemPrice = Double.parseDouble(itemPriceStr.trim());
            newItemPrice = Double.parseDouble(newItemPriceStr.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect("update.jsp?error=invalid_price_format");
            return;
        }

        // Check if the item exists in the database
        try {
            if (!itemExists(itemName, itemPrice)) {
                response.sendRedirect("update.jsp?error=item_not_found");
                return;
            }
        } catch (SQLException | IOException e) {

            e.printStackTrace();
            response.sendRedirect("update.jsp?error=database_error");
            return;
        }

        try (Connection conn = getConnection()) {
            String sql;
            if (itemImagePart != null && itemImagePart.getSize() > 0) {
                // Image part is not empty, update item name, price, and image
                sql = "UPDATE grocery_items SET name=?, price=?, image_url=? WHERE name=? AND price=?";
            } else {
                // Image part is empty, update item name and price only
                sql = "UPDATE grocery_items SET name=?, price=? WHERE name=? AND price=?";
            }
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                int parameterIndex = 1; // Start index for setting parameters
                statement.setString(parameterIndex++, newItemName);
                statement.setDouble(parameterIndex++, newItemPrice);

                // Handle image upload
                if (itemImagePart != null && itemImagePart.getSize() > 0) {
                    String fileName = getSubmittedFileName(itemImagePart);
                    if (fileName != null && !fileName.isEmpty()) {
                        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdir();
                        }
                        itemImagePart.write(uploadPath + File.separator + fileName);
                        String imageURL = "uploads/" + fileName;
                        statement.setString(parameterIndex++, imageURL); // Increment index for setting parameters
                    }
                }

                statement.setString(parameterIndex++, itemName); // Increment index for setting parameters
                statement.setDouble(parameterIndex++, itemPrice); // Increment index for setting parameters

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    // Update successful
                    response.sendRedirect("update.jsp");
                } else {
                    // Update failed
                    response.sendRedirect("update.jsp?error=update_failed");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("update.jsp?error=database_error");
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

    private boolean itemExists(String itemName, double itemPrice) throws SQLException {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM grocery_items WHERE name=? AND price=?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, itemName);
                statement.setDouble(2, itemPrice);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); // Returns true if item exists
                }
            }
        }
    }

    // Method to get the submitted file name from the Part object
    private String getSubmittedFileName(Part part) {
        String fileName = null;
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                break;
            }
        }
        return fileName;
    }
}
