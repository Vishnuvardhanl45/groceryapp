<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Grocery Delivery Site</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
    <style>
        body {
            background-image: url('uploads/backgroundimage.png');
            background-size: cover;
            background-attachment: fixed;
            background-position: center;
            background-repeat: no-repeat;
            background-size: 100% 90%;
            background-color: rgba(255, 255, 255, 0.5);
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid" style="margin-left: 30px;">
            <a class="navbar-brand" href="#">
                <img src="uploads/logo.png" alt="" width="30" height="24" class="d-inline-block align-text-top">
                GrocerEase
            </a>
            <form class="d-flex ms-auto" style="width: 80%;" action="/groceryapp/SearchItemsServlet" method="get">
                <input class="form-control me-2" type="search" name="query" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit" style="margin-left: 40px; margin-right: 40px;width: 20%;">Search</button>
            </form>
        </div>
    </nav>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">

            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="about.html">About</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="contact.html">Contact Us</a>
                    </li>
                </ul>
            

                <div class="d-flex align-items-center ms-auto">
                    <form action="/groceryapp/RetrieveCartFromDB" method="get">
                        <button type="submit" class="btn btn-light">
                            <ion-icon name="cart" style="font-size: 32px; margin-right: 10px;"></ion-icon>
                            <p class="mb-0" id="cartCounter">Cart (${sessionScope.cartCount})</p>
                        </button>
                    </form>
                      
                    <c:choose>
                        <c:when test="${not empty sessionScope.email || not empty sessionScope.phone || not empty sessionScope.username}">
                            <form action="SessionManagementServlet" method="get">
                                <input type="hidden" name="action" value="logout">
                                <button type="submit" class="btn btn-secondary ms-3">Logout</button>
                            </form>
                            <c:choose>
                                <c:when test="${not empty sessionScope.email}">
                                    <p class="mb-0 ms-3">Hello, ${sessionScope.email}!</p>
                                </c:when>
                                <c:when test="${not empty sessionScope.username}">
                                    <p class="mb-0 ms-3">Hello, ${sessionScope.username}!</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="mb-0 ms-3">Hello, ${sessionScope.phone}!</p>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn btn-secondary ms-3" onclick="window.location.href='login.html'">Login</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-5" id="groceryItemsContainer">
        <div class="row">
            <c:forEach var="item" items="${groceryItems}">
                <div class="col-md-4">
                    <div class="card mb-4" style="height: 400px; width: 300px;">
                        <img src="${item.imageURL}" class="card-img-top" alt="${item.name}" style="height: 165px;">
                        <div class="card-body" style="margin-bottom: 30px;">
                            <h5 class="card-title">${item.name}</h5>
                            <p class="card-text">Price: ${item.price} rs</p>
                            <p class="card-text">Quantity: ${item.quantityValue} ${item.quantityUnit}</p>
                            <div class="d-grid gap-2" style="margin-top: 20px;">
                                <form action="/groceryapp/CartServlet" method="post" >
                                    <input type="hidden" name="itemName" value="${item.name}">
                                    <button class="btn btn-primary" type="submit" style="width: 100%;">Add to Cart</button>
                                </form>

                                <form action="/groceryapp/BuyServlet" method="post">
                                    <input type="hidden" name="itemName" value="${item.name}">
                                    <input type="hidden" name="itemPrice" value="${item.price}">
                                    <c:choose>
                                        <c:when test="${not empty sessionScope.email || not empty sessionScope.phone || not empty sessionScope.username}">
                                            <button class="btn btn-success" type="submit" style="width: 100%;">Buy</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-success" type="button" style="width: 100%;" onclick="redirectToLogin()">Buy</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>

                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success">${sessionScope.message}</div>
        <c:remove var="message" scope="session"/>
    </c:if>

   

<script>
    function redirectToPayment() {
        window.location.href = "payment.jsp";
    }

    function redirectToLogin() {
        window.location.href = "login.html";
    }
</script>



    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
