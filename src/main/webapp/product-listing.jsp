<%@ page import="com.groceryapp.GroceryItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Grocery Delivery Site</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    // Simulated list of grocery items (you can replace this with your actual list)
    List<GroceryItem> groceryItems = new ArrayList<>();
    groceryItems.add(new GroceryItem("Apple", 1.99, "apple.jpg", 10, "pieces"));
    groceryItems.add(new GroceryItem("Banana", 0.99, "banana.jpg", 5, "pieces"));
%>
<div class="container mt-5" id="groceryItemsContainer">
    <div class="row">
        <%
            for (GroceryItem item : groceryItems) {
        %>
        <div class="col-md-4">
            <div class="card mb-4" style="height: 400px; width: 300px;">
                <img src="<%= item.getImageURL() %>" class="card-img-top" alt="<%= item.getName() %>" style="height: 165px;">
                <div class="card-body" style="margin-bottom: 30px;">
                    <h5 class="card-title"><%= item.getName() %></h5>
                    <p class="card-text">Price: $<%= new DecimalFormat("0.00").format(item.getPrice()) %></p>
                    <p class="card-text">Quantity: <%= item.getQuantityValue() %> <%= item.getQuantityUnit() %></p>
                    <form action="addToCart" method="post">
                        <input type="hidden" name="itemName" value="<%= item.getName() %>">
                        <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">
                        <input type="hidden" name="itemImageURL" value="<%= item.getImageURL() %>">
                        <input type="hidden" name="itemQuantityValue" value="<%= item.getQuantityValue() %>">
                        <input type="hidden" name="itemQuantityUnit" value="<%= item.getQuantityUnit() %>">
                        <button class="btn btn-primary" type="submit">Add to Cart</button>
                    </form>
                </div>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
