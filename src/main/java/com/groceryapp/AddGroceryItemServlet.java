package com.groceryapp;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/addGroceryItem")
@MultipartConfig
public class AddGroceryItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data with error handling
        String itemName = request.getParameter("itemName");
        String itemPriceStr = request.getParameter("itemPrice");
        String quantityValueStr = request.getParameter("quantityValue");
        String quantityUnit = request.getParameter("quantityUnit");
        Part itemImagePart = request.getPart("itemImage");

        if (itemName == null || itemPriceStr == null || quantityValueStr == null || quantityUnit == null) {
            response.sendRedirect("admin.html?message=Missing%20form%20data");
            return;
        }

        double itemPrice;
        int quantityValue;
        try {
            itemPrice = Double.parseDouble(itemPriceStr);
            quantityValue = Integer.parseInt(quantityValueStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("admin.html?message=Invalid%20number%20format");
            return;
        }

        // Handle the file upload and save the image URL
        String imageURL = handleFileUpload(itemImagePart);

        // Save data to database
        boolean isInserted = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnection.getConnection();
            String query = "INSERT INTO grocery_items (name, price, quantity_value, quantity_unit, image_url) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, itemName);
            statement.setDouble(2, itemPrice);
            statement.setInt(3, quantityValue);
            statement.setString(4, quantityUnit);
            statement.setString(5, imageURL);
            statement.executeUpdate();
            isInserted = true;
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database error
        } finally {
            // Close resources in finally block
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // Redirect back to admin.html with success or failure message
        String redirectURL = isInserted ? "admin.html?message=Item%20successfully%20inserted"
                : "admin.html?message=Failed%20to%20insert%20item";
        response.sendRedirect(redirectURL);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method is not supported.");
    }

    private String handleFileUpload(Part itemImagePart) throws IOException {
        // Manually extract the file name from the Content-Disposition header
        String contentDisposition = itemImagePart.getHeader("content-disposition");
        String fileName = extractFileName(contentDisposition);

        // Set the upload directory
        String uploadDirPath = getServletContext().getRealPath("/uploads");
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Save the file
        String filePath = uploadDirPath + File.separator + fileName;
        itemImagePart.write(filePath);
        return "uploads/" + fileName;
    }

    private String extractFileName(String contentDisposition) {
        String[] parts = contentDisposition.split(";");
        for (String part : parts) {
            if (part.trim().startsWith("filename")) {
                return part.substring(part.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
