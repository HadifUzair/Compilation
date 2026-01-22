<%-- 
    Document   : seller_dashboard
    Created on : Jan 22, 2026, 10:51:18 AM
    Author     : amirah izzah
--%>

<%@page import="com.marketplace.models.SaleRecord"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Ensure user is logged in
    if (session.getAttribute("loggedUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Seller Dashboard | UiTM Marketplace</title>
    <link rel="stylesheet" type="text/css" href="css/styles.css">
    <style>
        .dashboard-container { max-width: 1200px; margin: 30px auto; padding: 20px; }
        .dashboard-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 30px; }
        
        /* Stats Cards */
        .stats-grid { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 40px; }
        .stat-card { background: white; padding: 25px; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); text-align: center; border-bottom: 4px solid #6a11cb; }
        .stat-value { font-size: 2.5em; font-weight: bold; color: #333; margin: 10px 0; }
        .stat-label { color: #666; font-size: 1.1em; }
        
        /* Table Styles */
        .table-container { background: white; padding: 20px; border-radius: 10px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }
        .data-table { width: 100%; border-collapse: collapse; margin-top: 15px; }
        .data-table th { background: #f8f9fa; padding: 15px; text-align: left; color: #444; border-bottom: 2px solid #ddd; }
        .data-table td { padding: 15px; border-bottom: 1px solid #eee; vertical-align: middle; }
        .data-table tr:hover { background-color: #f9f9f9; }
        
        .status-badge { padding: 5px 10px; border-radius: 15px; font-size: 0.85em; font-weight: bold; }
        .status-Completed { background-color: #d4edda; color: #155724; }
        .status-Pending { background-color: #fff3cd; color: #856404; }
        
        .product-thumb { width: 50px; height: 50px; object-fit: cover; border-radius: 5px; margin-right: 10px; vertical-align: middle; }
        .btn-add { background: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-weight: bold; }
    </style>
</head>
<body>
    <jsp:include page="navbar.jsp" />

    <div class="dashboard-container">
        <div class="dashboard-header">
            <h1>Seller Dashboard</h1>
            <a href="sell.jsp" class="btn-add">+ List New Item</a>
        </div>

        <div class="stats-grid">
            <div class="stat-card">
                <div class="stat-label">Total Revenue</div>
                <div class="stat-value">RM <%= String.format("%.2f", request.getAttribute("revenue")) %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Items Sold</div>
                <div class="stat-value"><%= request.getAttribute("itemsSold") %></div>
            </div>
            <div class="stat-card">
                <div class="stat-label">Active Listings</div>
                <div class="stat-value"><%= request.getAttribute("activeListings") %></div>
            </div>
        </div>

        <div class="table-container">
            <h2>Sales History</h2>
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Product</th>
                        <th>Buyer</th>
                        <th>Date</th>
                        <th>Price (RM)</th>
                        <th>Status</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                    List<SaleRecord> history = (List<SaleRecord>) request.getAttribute("salesHistory");
                    if (history != null && !history.isEmpty()) {
                        for (SaleRecord rec : history) {
                    %>
                    <tr>
                        <td>
                            <img src="<%= rec.getProductImage() %>" class="product-thumb" alt="img">
                            <%= rec.getProductName() %>
                        </td>
                        <td><%= rec.getBuyerName() %></td>
                        <td><%= rec.getSaleDate() %></td>
                        <td><%= String.format("%.2f", rec.getSoldPrice()) %></td>
                        <td><span class="status-badge status-<%= rec.getStatus() %>"><%= rec.getStatus() %></span></td>
                    </tr>
                    <% 
                        }
                    } else { 
                    %>
                    <tr>
                        <td colspan="5" style="text-align:center; padding: 30px; color: #888;">No sales records found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <jsp:include page="footer.jsp" />
</body>
</html>