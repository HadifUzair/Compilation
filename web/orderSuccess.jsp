<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer orderId = (Integer) session.getAttribute("lastOrderId");
    if (orderId == null) {
        response.sendRedirect("cart");
        return;
    }
    
    String paymentMethod = (String) session.getAttribute("paymentMethod");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmed | UiTMMarketplace</title>
    <link rel="stylesheet" href="css/styles.css">
    <style>
        .success-container {
            text-align: center;
            padding: 50px 20px;
            max-width: 600px;
            margin: 0 auto;
        }
        .success-icon {
            font-size: 80px;
            color: #4CAF50;
            margin-bottom: 20px;
        }
        .order-id {
            background: #f5f5f5;
            padding: 10px;
            border-radius: 5px;
            display: inline-block;
            margin: 15px 0;
        }
    </style>
</head>
<body>
    <jsp:include page="navbar.jsp" />
    
    <div class="container">
        <div class="success-container">
            <div class="success-icon">✓</div>
            <h1>Order Confirmed!</h1>
            <p>Thank you for your purchase. Your order has been placed successfully.</p>
            
            <div class="order-id">
                <strong>Order ID:</strong> #<%= orderId %>
            </div>
            
            <p>
                <% if ("COD".equals(paymentMethod)) { %>
                    Please arrange campus meetup with seller for payment.
                <% } else { %>
                    Please complete your payment via FPX within 24 hours.
                <% } %>
            </p>
            
            <p>You can track your order in <a href="HistoryServlet">Purchase History</a>.</p>
            
            <div style="margin-top: 30px;">
                <a href="ProductListingServlet" class="btn-submit">Continue Shopping</a>
                <a href="HistoryServlet" class="btn-cancel" style="background: #ccc; color: #333; padding: 10px 20px; border-radius: 5px; text-decoration: none;">View Orders</a>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>