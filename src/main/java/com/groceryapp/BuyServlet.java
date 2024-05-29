package com.groceryapp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/BuyServlet")
public class BuyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // Do not create a new session if not exists
        if (session != null && (session.getAttribute("email") != null || session.getAttribute("phone") != null
                || session.getAttribute("username") != null)) {
            // User is logged in, redirect to payment page
            String itemName = request.getParameter("itemName");
            String itemPrice = request.getParameter("itemPrice");
            response.sendRedirect("payment.jsp?itemName=" + itemName + "&amount=" + itemPrice);
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.html");
        }
    }
}
