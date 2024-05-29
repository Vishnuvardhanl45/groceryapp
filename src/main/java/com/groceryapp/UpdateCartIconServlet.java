package com.groceryapp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class UpdateCartIconServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get number of items in cart
        HttpSession session = request.getSession();
        int cartCount = (int) session.getAttribute("cartCount");

        // Send cart count as response
        response.getWriter().write(String.valueOf(cartCount));
    }
}
