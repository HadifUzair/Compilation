<nav class="navbar">
    <div class="logo">
        <a href="index.jsp" style="color: white;"><span>UiTM</span>Marketplace</a>
    </div>
    <ul class="nav-links">
        <li><a href="index.jsp">Home</a></li>
        <li><a href="shopping.jsp">Shop</a></li>
        <li><a href="cart.jsp">Cart</a></li>
        <%-- Link to the Servlet, not the JSP directly --%>
        <li><a href="HistoryServlet">History</a></li>
        <li><a href="profile.jsp">Profile</a></li>
        <% if(session.getAttribute("loggedUser") == null) { %>
            <li><a href="login.html" class="btn-login-nav">Login</a></li>
        <% } else { %>
            <li><a href="LogoutServlet" class="btn-login-nav">Logout</a></li>
        <% } %>
    </ul>
</nav>