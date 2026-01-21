<%@page import="com.marketplace.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Security: Check if user is logged in
    User user = (User) session.getAttribute("loggedUser");
    if (user == null) {
        response.sendRedirect("login.html");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile | UiTMMarketplace</title>
   <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
   <style>
       /* Ensure the image fits nicely in the circle */
       .profile-img img {
           width: 100%;
           height: 100%;
           object-fit: cover;
           border-radius: 50%;
       }
   </style>
</head>
<body>

    <jsp:include page="navbar.jsp" />

    <div class="container">
        <h1 class="page-title">My Profile</h1>

        <div class="profile-header">
            <div class="profile-img">
                <% if (user.getProfilePic() != null && !user.getProfilePic().isEmpty()) { %>
                    <img src="<%= user.getProfilePic() %>" alt="Avatar" onerror="this.src='https://via.placeholder.com/150?text=Error';">
                <% } else { %>
                    <span><%= user.getFullName().substring(0, 1).toUpperCase() %></span>
                <% } %>
            </div>
            <div class="profile-info">
                <h2><%= user.getFullName() %></h2>
                <p style="color: #666;">Student ID: <%= user.getStudentId() %></p>
                <p>Member since: 2024</p>
                
                <button onclick="editAvatar()" class="btn-cta" style="padding: 8px 20px; font-size: 0.9rem; border:none; cursor:pointer;">Edit Avatar</button>
                
                <form id="avatarForm" action="UpdateAvatarServlet" method="POST" style="display:none;">
                    <input type="hidden" name="avatarUrl" id="avatarUrlInput">
                </form>

                <script>
                    function editAvatar() {
                        let url = prompt("Please enter the URL of your new profile picture:", "<%= (user.getProfilePic() != null) ? user.getProfilePic() : "" %>");
                        if (url != null && url.trim() !== "") {
                            document.getElementById("avatarUrlInput").value = url;
                            document.getElementById("avatarForm").submit();
                        }
                    }
                </script>
            </div>
        </div>

        <div class="section-box">
            <h3>Account Details</h3>
            <%-- Success message after update --%>
            <% if(request.getParameter("status") != null) { %>
                <p style="color: green; margin-bottom: 10px;">
                    <% if(request.getParameter("status").equals("avatar_updated")) { %>
                        Avatar updated successfully!
                    <% } else { %>
                        Profile updated successfully!
                    <% } %>
                </p>
            <% } %>

            <form action="UpdateProfileServlet" method="POST" class="profile-grid">
                <div class="form-group">
                    <label>Full Name</label>
                    <input type="text" value="<%= user.getFullName() %>" disabled>
                </div>
                <div class="form-group">
                    <label>Student ID</label>
                    <input type="text" value="<%= user.getStudentId() %>" disabled>
                </div>
                <div class="form-group">
                    <label>Email Address</label>
                    <input type="email" name="email" value="<%= user.getEmail() %>">
                </div>
                <div class="form-group">
                    <label>Phone Number</label>
                    <input type="tel" name="phone" value="<%= user.getPhoneNumber() != null ? user.getPhoneNumber() : "" %>">
                </div>
                <div class="form-group">
                    <label>Address</label>
                    <input type="text" name="address" value="<%= user.getAddress() != null ? user.getAddress() : "" %>">
                </div>
               
                <div style="margin-top: 20px; text-align: right; grid-column: 1 / -1;">
                    <button type="submit" class="btn-submit" style="width: auto; padding: 10px 30px;">Save Changes</button>
                </div>
            </form>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 UniMarket Group Project.</p>
    </footer>

</body>
</html>