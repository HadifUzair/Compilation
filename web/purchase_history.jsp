<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Purchase History | UiTMMarketplace</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

    <jsp:include page="navbar.jsp" />

    <div class="container">
        <h1 class="page-title">Purchase History</h1>

        <div class="section-box">
            <div class="table-responsive">
                <table class="styled-table">
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Date</th>
                            <th>Items</th>
                            <th>Total Price</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%-- This 'history' list is provided by the HistoryServlet --%>
                        <c:forEach var="order" items="${history}">
                            <tr>
                                <td>#ORD-${order.orderId}</td>
                                <td>${order.orderDate}</td>
                                <td>${order.itemSummary != null ? order.itemSummary : 'Multiple Items'}</td>
                                <td>RM ${order.totalAmount}</td>
                                <td>
                                    <span class="badge ${order.status == 'Completed' ? 'badge-success' : 'badge-warning'}">
                                        ${order.status}
                                    </span>
                                </td>
                                <td><a href="ViewOrder?id=${order.orderId}" style="color: var(--primary-purple); font-weight: bold;">View Details</a></td>
                            </tr>
                        </c:forEach>
                        
                        <c:if test="${empty history}">
                            <tr>
                                <td colspan="6" style="text-align: center;">No orders found.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 UniMarket Group Project.</p>
    </footer>

</body>
</html>