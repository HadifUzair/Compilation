<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar">
    <div class="logo">
        <a href="index.jsp" style="color: white; text-decoration: none;"><span>UiTM</span>Marketplace</a>
    </div>
    <ul class="nav-links">
        <li><a href="index.jsp">Home</a></li>
        <li><a href="ProductListingServlet">Shop</a></li>
        <li><a href="CartServlet">Cart</a></li>
        
        <% if(session.getAttribute("loggedUser") != null) { %>
            <li><a href="sell.jsp" style="color: #ffbf00; font-weight:bold;">+ Sell</a></li>
            
            <li><a href="SellerDashboardServlet">Dashboard</a></li>
        <% } %>
        
        <li><a href="HistoryServlet">Orders</a></li>
        
        <li><a href="profile.jsp">Profile</a></li>

        <% if(session.getAttribute("loggedUser") == null) { %>
            <li><a href="login.jsp" class="btn-login-nav">Login</a></li>
        <% } else { %>
            <li><a href="<%= request.getContextPath() %>/LogoutServlet" class="btn-login-nav">Logout</a></li>
        <% } %>
    </ul>
</nav>