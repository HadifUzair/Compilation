<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register | UniMarket</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>

    <nav class="navbar">
        <div class="logo">
            <a href="index.jsp" style="color: white;"><span>UiTM</span>Marketplace</a>
        </div>
        <ul class="nav-links">
            <li><a href="login.jsp" class="btn-login-nav">Login</a></li>
        </ul>
    </nav>

    <section class="auth-wrapper">
        <div class="auth-card">
            <h2>Create Account</h2>
            
            <% if (request.getAttribute("errorMessage") != null) { %>
                <div style="color: red; text-align: center; margin-bottom: 10px;">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>

            <form action="register" method="post">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" name="fullname" required>
                </div>
                <div class="form-group">
                    <label>Student ID</label>
                    <input type="text" name="studentID"  required>
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" required>
                </div>
                <div class="form-group">
                    <label>Password</label>
                    <input type="password" name="password" placeholder="Create Password" required>
                </div>
                <button type="submit" class="btn-submit">Register Now</button>
            </form>
            <div class="form-footer">
                <p>Already have an account? <a href="login.jsp">Log In</a></p>
            </div>
        </div>
    </section>

    <footer>
        <p>&copy; 2024 UniMarket Group Project.</p>
    </footer>

</body>
</html>
