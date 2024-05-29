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

        .navbar-button {
            margin-right: 10px; 
        }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <img src="uploads/logo.png" alt="" width="30" height="24" class="d-inline-block align-text-top">
                GrocerEase
            </a>
            <div class="d-flex">
                <button type="button" class="btn btn-primary navbar-button" onclick="window.location.href='updateForm.html'">Update</button> <!-- Update button -->
                <button type="button" class="btn btn-danger navbar-button" onclick="window.location.href='removeForm.html'">Remove</button> <!-- Remove button -->
            </div>
            <form class="d-flex ms-auto" style="width: 90%;" action="/groceryapp/SearchItemsByAdmin" method="get">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search" name="query"> <!-- Added name attribute -->
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
            
        </div>
    </nav>

   
    <div class="container mt-5" id="groceryItemsContainer">
        <div class="row">
            <c:forEach var="item" items="${groceryItems}">
                <div class="col-md-4">
                    <div class="card mb-4" style="height: 350px; width: 300px;">
                        <img src="${item.imageURL}" class="card-img-top" alt="${item.name}" style="height: 60%;">
                        <div class="card-body">
                            <h5 class="card-title">${item.name}</h5>
                            <p class="card-text">Price: ${item.price} rs</p>
                            <p class="card-text">Quantity: ${item.quantityValue} ${item.quantityUnit}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>