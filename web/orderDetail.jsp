<%-- 
    Document   : orderDetail
    Created on : Jan 19, 2026, 1:58:39 PM
    Author     : user
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order Details</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <jsp:include page="navbar.jsp" />
    <div class="container">
        <h1 class="page-title">Order Details #${orderId}</h1>
        <div class="section-box">
            <table class="styled-table">
                <thead>
                    <tr><th>Product</th><th>Price</th><th>Quantity</th><th>Total</th></tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${orderItems}">
                    <tr>
                        <td style="display:flex; align-items:center;">
                            <img src="${item.imageUrl}" style="width:50px; height:50px; margin-right:10px; border-radius:4px;">
                            ${item.productName}
                        </td>
                        <td>RM ${item.price}</td>
                        <td>${item.quantity}</td>
                        <td>RM ${item.price * item.quantity}</td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
            <div style="margin-top: 20px;">
                <a href="HistoryServlet" class="btn-cancel" style="background:#6c757d; color:white; padding:10px 20px; border-radius:5px; text-decoration:none;">Back to History</a>
            </div>
        </div>
    </div>
</body>
</html>