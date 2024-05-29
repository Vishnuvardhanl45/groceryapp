<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redirecting to Payment</title>
</head>
<body>
    <%
        // Get the total price from the form submission
        String totalPrice = request.getParameter("totalPrice");
    %>
    <script>
        // Redirect to payment.jsp with the total price as a parameter
        window.location.href = "payment.jsp?amount=<%= totalPrice %>";
    </script>
</body>
</html>
