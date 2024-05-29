package com.groceryapp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && (session.getAttribute("email") != null || session.getAttribute("phone") != null
                || session.getAttribute("username") != null)) {
            // User is logged in, redirect to payment page
            String amount = request.getParameter("amount");
            request.setAttribute("amount", amount);
            request.getRequestDispatcher("payment.jsp").forward(request, response);
        } else {
            // User is not logged in, redirect to login page
            response.sendRedirect("login.html");
        }
    }
}
