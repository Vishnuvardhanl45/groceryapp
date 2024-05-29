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
import javax.servlet.http.HttpSession;

@WebServlet("/RetrieveCartFromDB")
public class RetrieveCartFromDB extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String phone = (String) session.getAttribute("phone");
        String email = (String) session.getAttribute("email");

        List<CartItem> cartItems = new ArrayList<>();
        double totalPrice = 0.0;

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery_db", "root",
                "satya123")) {
            PreparedStatement pstmt = conn
                    .prepareStatement("SELECT * FROM cart WHERE username = ? OR phone = ? OR email = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, phone);
            pstmt.setString(3, email);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("id"));
                cartItem.setName(rs.getString("item_name"));
                cartItem.setPrice(rs.getDouble("item_price"));
                cartItems.add(cartItem);
                totalPrice += rs.getDouble("item_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalPrice", totalPrice);
        RequestDispatcher rd = request.getRequestDispatcher("cart.jsp");
        rd.forward(request, response);
    }
}
