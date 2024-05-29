<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Search Results</h1>
        <c:if test="${empty searchResults}">
            <p>No items found.</p>
        </c:if>
        <div class="row">
            <c:forEach var="item" items="${searchResults}">
                <div class="col-md-4">
                    <div class="card mb-4" style="height: 400px; width: 300px;">
                        <img src="${item.imageURL}" class="card-img-top" alt="${item.name}" style="height: 165px;">
                        <div class="card-body" style="margin-bottom: 30px;">
                            <h5 class="card-title">${item.name}</h5>
                            <p class="card-text">Price: ${item.price} rs</p>
                            <p class="card-text">Quantity: ${item.quantityValue} ${item.quantityUnit}</p>
                            <div class="d-grid gap-2" style="margin-top: 20px;">
                                <form action="/groceryapp/CartServlet" method="post">
                                    <input type="hidden" name="itemName" value="${item.name}">
                                    <button class="btn btn-primary" type="submit" style="width: 100%;">Add to Cart</button>
                                </form>
                                <form action="/groceryapp/payment" method="post">
                                    <input type="hidden" name="amount" value="${item.price}">
                                    <button class="btn btn-success" type="submit" style="width: 100%;">Buy</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
