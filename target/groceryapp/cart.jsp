<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .container {
            padding-top: 20px;
        }
        .go-back {
            position: absolute;
            top: 20px;
            right: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="index.jsp" class="btn btn-secondary go-back">Go Back</a>
        <h1 class="mb-4">Your Cart</h1>
        <div class="row">
            <div class="col-md-8">
                <c:if test="${empty cartItems}">
                    <p>Your cart is empty.</p>
                </c:if>
                <c:forEach var="item" items="${cartItems}">
                    <div class="card mb-3">
                        <div class="card-body">
                            <h5 class="card-title">${item.name}</h5>
                            <p class="card-text">Price: ${item.price} rs</p>
                           
                            <form action="/groceryapp/RemoveItemFromCartServlet" method="post">
                                <input type="hidden" name="cartItemId" value="${item.cartItemId}">
                                <button class="btn btn-danger" type="submit">Remove</button>
                            </form>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h5>Total</h5>
                    </div>
                    <div class="card-body">
                        <h6 class="card-subtitle mb-2 text-muted">Total Price:</h6>
                        <p class="card-text">$${totalPrice}</p>
                    </div>
                    <div class="card-footer">
                     
                        <form action="paymentRedirect.jsp" method="post">
                            <input type="hidden" name="totalPrice" value="${totalPrice}">
                            <button type="submit" class="btn btn-primary">Proceed to Checkout</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
