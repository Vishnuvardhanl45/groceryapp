package com.groceryapp;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class CheckLoginStatusServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        boolean loggedIn = checkLoginStatus(request);
        response.getWriter().write(String.valueOf(loggedIn));
    }

    private boolean checkLoginStatus(HttpServletRequest request) {

        return true;
    }
}
