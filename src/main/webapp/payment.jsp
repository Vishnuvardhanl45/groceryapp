<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redirecting to Razorpay</title>
    
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <%
        String amountToPay = request.getParameter("amount");
    %>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Redirecting to Razorpay</h5>
                        <p class="card-text">Please wait while we redirect you to the Razorpay gateway to complete the payment.</p>
                    </div>
                    <div class="card-footer text-muted">
                        <!-- Display the dynamic amount -->
                        Amount to Pay: <span id="amountToPay"><%= amountToPay %></span>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script>
        // Redirect to Razorpay gateway with the dynamic amount
        setTimeout(function() {
            var amount = document.getElementById("amountToPay").textContent;
            window.location.href = "razorpayGateway.html?amount=" + amount;
        }, 3000); // Redirect after 3 seconds (adjust as needed)
    </script>
</body>
</html>
