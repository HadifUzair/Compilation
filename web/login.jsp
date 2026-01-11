<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login | UniMarket</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

    <nav class="navbar">
        <div class="logo">
            <a href="index.jsp" style="color: white;"><span>UiTM</span>Marketplace</a>
        </div>
        <ul class="nav-links">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="cart">Cart</a></li>
            <li><a href="HistoryServlet">History</a></li>
            <li><a href="profile.jsp" style="color: #ffbf00;">Profile</a></li>
            <li><a href="register.jsp" class="btn-login-nav">Register</a></li>
        </ul>
    </nav>

    <section class="auth-wrapper">
        <div class="auth-card">
            <h2>Welcome Back</h2>
            
            <% if ("registered".equals(request.getParameter("status"))) { %>
                <div style="color: green; text-align: center; margin-bottom: 15px;">
                    Registration successful! Please log in.
                </div>
            <% } %>

            <% if (request.getAttribute("errorMessage") != null) { %>
                <div style="color: red; text-align: center; margin-bottom: 15px;">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <form action="login" method="post">
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" required>
                </div>
                <button type="submit" class="btn-submit">Log In</button>
            </form>
            
            <div class="form-footer">
                <p>Don't have an account? <a href="register.jsp">Register</a></p>
            </div>
        </div>
    </section>

    <footer>
        <p>&copy; 2024 UniMarket Group Project.</p>
    </footer>

</body>
</html>