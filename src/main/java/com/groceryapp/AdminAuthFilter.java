package com.groceryapp;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.logging.Logger;

@WebFilter({ "/admin.html", "/update.jsp" })
public class AdminAuthFilter implements Filter {

    private static final Logger logger = Logger.getLogger(AdminAuthFilter.class.getName());

    public void init(FilterConfig fConfig) throws ServletException {
        logger.info("AdminAuthFilter initialized");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("adminUser") != null);

        if (isLoggedIn) {
            logger.info("Admin user is logged in, proceeding with the request");
            chain.doFilter(request, response); // User is logged in, proceed with the request
        } else {
            logger.warning("Admin user is not logged in, redirecting to AdminLogin.html");
            httpResponse.sendRedirect("AdminLogin.html"); // Not logged in, redirect to admin login page
        }
    }

    public void destroy() {
        logger.info("AdminAuthFilter destroyed");
    }
}
